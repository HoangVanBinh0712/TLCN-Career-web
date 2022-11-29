package com.be.service.impl;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.Tuple;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.be.controller.exception.CommonRuntimeException;
import com.be.dto.PostDTO;
import com.be.model.Admin;
import com.be.model.Post;
import com.be.payload.BaseResponse;
import com.be.payload.DataResponse;
import com.be.payload.statistic.CountPaidPostByYear;
import com.be.payload.statistic.CountTotalByService;
import com.be.payload.statistic.StatisticResponse;
import com.be.payload.statistic.SumTotalByService;
import com.be.payload.statistic.SumTotalByYearMonthCurrencyStatus;
import com.be.repository.AdminRepository;
import com.be.repository.PostRepository;
import com.be.service.AdminPostService;
import com.be.utility.datatype.EROrderStatus;
import com.be.utility.datatype.EStatus;

@Service
public class AdminPostServiceImpl implements AdminPostService {
	@Autowired
	PostRepository postRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	AdminRepository adminRepository;

	@Override
	public BaseResponse acceptPost(String email, Long postId) {
		Optional<Post> optPost = postRepository.findById(postId);
		if (optPost.isEmpty())
			throw new CommonRuntimeException("Post not found with id: " + postId);
		Post post = optPost.get();
		Admin admin = adminRepository.findByEmail(email).get();
		post.setAcceptedBy(admin);
		post.setAcceptedDate(new Date());
		post.setStatus(EStatus.ACTIVE);

		postRepository.save(post);
		return new BaseResponse(true, "Accept susscessfully post with id: " + postId);
	}

	@Override
	public BaseResponse unAcceptPost(Long postId) {
		Optional<Post> optPost = postRepository.findById(postId);
		if (optPost.isEmpty())
			throw new CommonRuntimeException("Post not found with id: " + postId);
		Post post = optPost.get();
		post.setStatus(EStatus.DELETED_BY_ADMIN);

		postRepository.save(post);
		return new BaseResponse(true, "Unaccept susscessfully post with id: " + postId);
	}

	@Override
	public DataResponse<PostDTO> getPostDetail(Long postId) {
		Optional<Post> optPost = postRepository.findById(postId);
		if (optPost.isEmpty())
			throw new CommonRuntimeException("Post not found with id: " + postId);
		return new DataResponse<>(true, "", modelMapper.map(optPost.get(), PostDTO.class));
	}

	@Override
	public DataResponse<StatisticResponse> getStatistic() {
		List<CountPaidPostByYear> countPaidPostByYears = postRepository
				.getCountPaidOrderGroupByMonth(EROrderStatus.PAID);
		List<Tuple> stbymcs = postRepository.getSumTotalByYearMonthCurrencyStatus();
		List<Tuple> stbs = postRepository.getSumTotalByService();
		List<Tuple> ctbs = postRepository.getCountTotalByService();

		List<SumTotalByYearMonthCurrencyStatus> sumTotalByYearMonthCurrencyStatus = stbymcs.stream()
				.map(x -> new SumTotalByYearMonthCurrencyStatus(x.get("year", Integer.class),
						x.get("month", Integer.class),
						x.get("status", String.class),
						x.get("revenue", Double.class),
						x.get("currency", String.class)))
				.toList();

		List<SumTotalByService> sumTotalByServices = stbs.stream()
				.map(x -> new SumTotalByService(x.get("name", String.class),
						x.get("revenue", Double.class),
						x.get("currency", String.class)))
				.toList();
		List<CountTotalByService> countTotalByServices = ctbs.stream()
				.map(x -> new CountTotalByService(x.get("name", String.class),
						new BigInteger(x.get("count").toString()).longValueExact(),
						x.get("currency", String.class)))
				.toList();
		StatisticResponse res = new StatisticResponse();
		res.setCountPaidPostByYears(countPaidPostByYears);
		res.setCountTotalByServices(countTotalByServices);
		res.setSumTotalByServices(sumTotalByServices);
		res.setSumTotalByYearMonthCurrencyStatus(sumTotalByYearMonthCurrencyStatus);

		return new DataResponse<>(true, "Sucess", res);
	}
}
