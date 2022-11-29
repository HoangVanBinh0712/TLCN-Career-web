package com.be.service.impl;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.be.controller.exception.CommonRuntimeException;
import com.be.dto.PostDTO;
import com.be.model.City;
import com.be.model.Employer;
import com.be.model.Field;
import com.be.model.MediaResource;
import com.be.model.Post;
import com.be.model.PostOrder;
import com.be.payload.BaseResponse;
import com.be.payload.DataResponse;
import com.be.payload.post.PostCreateRequest;
import com.be.payload.post.PostCreateRequestTool;
import com.be.payload.post.PostRenewRequest;
import com.be.payload.post.PostUpdateRequest;
import com.be.repository.CVSubmitRepository;
import com.be.repository.CityRepository;
import com.be.repository.EmployerRepository;
import com.be.repository.FieldRepository;
import com.be.repository.PostOrderRepository;
import com.be.repository.PostRepository;
import com.be.repository.ServiceRepository;
import com.be.service.MediaResourceService;
import com.be.service.PostService;
import com.be.utility.datatype.ECurrency;
import com.be.utility.datatype.EOrderType;
import com.be.utility.datatype.EROrderStatus;
import com.be.utility.datatype.ESalary;
import com.be.utility.datatype.EStatus;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	PostRepository postRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	CityRepository cityRepository;

	@Autowired
	FieldRepository fieldRepository;

	@Autowired
	EmployerRepository employerRepository;

	@Autowired
	MediaResourceService mediaResourceService;

	@Autowired
	CVSubmitRepository cvSubmitRepository;

	@Autowired
	ServiceRepository serviceRepository;

	@Autowired
	PostOrderRepository postOrderRepository;

	@Override
	@Transactional(timeout = 1200)
	public DataResponse<PostDTO> create(Long empId, PostCreateRequest request, MultipartFile avatar) {
		if (request.getSalaryType() != ESalary.WAGE_AGREEMENT && request.getSalary() == null)
			throw new CommonRuntimeException("Salary must not be null !");

		Date today = new Date();

		if (request.getStartDate().before(today))
			request.setStartDate(today);
		Post post = modelMapper.map(request, Post.class);

		Optional<City> city = cityRepository.findByCode(request.getCityCode());
		if (city.isEmpty())
			throw new CommonRuntimeException("City not found with code: " + request.getCityCode());

		Optional<Field> field = fieldRepository.findByCode(request.getFieldCode());

		if (field.isEmpty())
			throw new CommonRuntimeException("Field not found with code: " + request.getFieldCode());

		Optional<com.be.model.Service> service = serviceRepository.findById(request.getService());

		if (service.isEmpty())
			throw new CommonRuntimeException("Service not found with id: " + request.getService());

		Employer emp = employerRepository.getReferenceById(empId);

		if (!emp.getEmailConfirm())
			return new DataResponse<>(false, "Please confirm your email address before adding post !", null);

		Integer duration = request.getDuration();
		// Format and caculate date
		SimpleDateFormat cmf = new SimpleDateFormat("yyyy-MM-dd");
		LocalDate date = LocalDate.parse(cmf.format(request.getStartDate()));
		LocalDate expirationDate = date.plusMonths(duration);

		post.setCreateDate(today);
		post.setCity(city.get());
		post.setField(field.get());
		post.setService(service.get());
		post.setEmployer(emp);
		post.setExpirationDate(Date.from(expirationDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
		post.setStatus(EStatus.WAIT_FOR_PAYMENT);

		if (avatar != null)
			try {
				MediaResource mr = mediaResourceService.save(avatar.getBytes());
				post.setAvatar(mr);

			} catch (Exception e) {
				throw new CommonRuntimeException("Error occur when uploading avatar please try again !");
			}
		// Create order
		Double price = service.get().getPrice();

		PostOrder order = new PostOrder();
		order.setCreatedDate(today);
		order.setDuration(request.getDuration());
		order.setPost(post);
		order.setPrice(price);
		order.setService(service.get());
		order.setStatus(EROrderStatus.WAIT_FOR_PAYMENT);
		order.setTotal(price * duration);
		order.setType(EOrderType.NEW);
		order.setCurrency(service.get().getCurrency());
		order.setPaymentDeadline(new Date(today.getTime() + 7 * 24 * 60 * 60 * 1000));
		order.setEmployer(emp);

		postOrderRepository.save(order);

		return new DataResponse<>(true, "Add new post successfully. Pay to get your post active !.",
				modelMapper.map(post, PostDTO.class));
	}

	@Override
	@Transactional(timeout = 1200)
	public DataResponse<PostDTO> createTool(Long empId, PostCreateRequestTool request, MultipartFile avatar) {
		if (request.getSalaryType() != ESalary.WAGE_AGREEMENT && request.getSalary() == null)
			throw new CommonRuntimeException("Salary must not be null !");
		Date today = new Date();

		if (request.getStartDate().before(today))
			request.setStartDate(today);
		Post post = modelMapper.map(request, Post.class);

		Optional<City> city = cityRepository.findById(request.getCityId());
		if (city.isEmpty())
			throw new CommonRuntimeException("City not found with code: " + request.getCityId());

		Field field = fieldRepository.findByName(request.getFieldName());

		if (field == null)
			throw new CommonRuntimeException("Field not found with code: " + request.getFieldName());

		Optional<com.be.model.Service> service = serviceRepository.findById(request.getService());

		if (service.isEmpty())
			throw new CommonRuntimeException("Service not found with id: " + request.getService());

		Employer emp = employerRepository.getReferenceById(empId);

		if (!emp.getEmailConfirm())
			return new DataResponse<>(false, "Please confirm your email address before adding post !", null);

		Integer duration = request.getDuration();
		// Format and caculate date
		SimpleDateFormat cmf = new SimpleDateFormat("yyyy-MM-dd");
		LocalDate date = LocalDate.parse(cmf.format(request.getStartDate()));
		LocalDate expirationDate = date.plusMonths(duration);

		post.setCreateDate(today);
		post.setCity(city.get());
		post.setField(field);
		post.setService(service.get());
		post.setEmployer(emp);
		post.setExpirationDate(Date.from(expirationDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
		post.setStatus(EStatus.WAIT_FOR_PAYMENT);

		if (avatar != null)
			try {
				MediaResource mr = mediaResourceService.save(avatar.getBytes());
				post.setAvatar(mr);

			} catch (Exception e) {
				throw new CommonRuntimeException("Error occur when uploading avatar please try again !");
			}
		// Create order
		Double price = service.get().getPrice();

		PostOrder order = new PostOrder();
		order.setCreatedDate(today);
		order.setDuration(request.getDuration());
		order.setPost(post);
		order.setPrice(price);
		order.setService(service.get());
		order.setStatus(EROrderStatus.WAIT_FOR_PAYMENT);
		order.setTotal(price * duration);
		order.setType(EOrderType.NEW);
		order.setCurrency(ECurrency.VND);
		order.setPaymentDeadline(new Date(today.getTime() + 7 * 24 * 60 * 60 * 1000));
		order.setEmployer(emp);

		postOrderRepository.save(order);

		return new DataResponse<>(true, "Add new post successfully. Pay to get your post active !.",
				modelMapper.map(post, PostDTO.class));
	}

	@Override
	@Transactional(timeout = 1200)
	public DataResponse<PostDTO> update(Long empId, PostUpdateRequest request, MultipartFile avatar) {

		if (request.getSalaryType() != null)
			if (request.getSalaryType() != ESalary.WAGE_AGREEMENT
					&& request.getSalary() == null)
				throw new CommonRuntimeException("Salary must not be null !");

		Optional<Post> optPost = postRepository.findById(request.getId());

		if (optPost.isEmpty())
			throw new CommonRuntimeException("Post not found with id: " + request.getId());

		Post post = optPost.get();
		if (post.getStatus() == EStatus.DELETED_BY_ADMIN) {
			return new DataResponse<>(false, "Post is already deleted by admin, cannot be update !", null);
		}

		if (!post.getEmployer().getId().equals(empId))
			throw new CommonRuntimeException("Unable to update other employer's post.");

		if (!StringUtils.isBlank(request.getTitle()))
			post.setTitle(request.getTitle());

		if (!StringUtils.isBlank(request.getDescription()))
			post.setDescription(request.getDescription());

		if (request.getSalary() != null) {
			post.setSalary(request.getSalary());
		} else if (request.getSalaryType() == ESalary.WAGE_AGREEMENT) {
			post.setSalary(null);
		}

		if (request.getSalaryType() != null)
			post.setSalaryType(request.getSalaryType());

		if (!StringUtils.isBlank(request.getCityCode())) {
			Optional<City> city = cityRepository.findByCode(request.getCityCode());
			if (city.isEmpty())
				throw new CommonRuntimeException("City not found with code: " + request.getCityCode());
			post.setCity(city.get());
		}
		if (!StringUtils.isBlank(request.getFieldCode())) {
			Optional<Field> field = fieldRepository.findByCode(request.getFieldCode());
			if (field.isEmpty())
				throw new CommonRuntimeException("Field not found with code: " + request.getFieldCode());
			post.setField(field.get());
		}
		if (!StringUtils.isBlank(request.getLocation()))
			post.setLocation(request.getLocation());

		if (request.getRecruit() != null)
			post.setRecruit(request.getRecruit());
		// Update status only in 3 state
		EStatus status = request.getStatus();
		if (status != null && status != post.getStatus()) {
			if (status == EStatus.ACTIVE || status == EStatus.DISABLE || status == EStatus.DELETED)
				post.setStatus(status);
			else
				throw new CommonRuntimeException(String.format("Unable to change from %s to %s.",
						post.getStatus().toString(), status.toString()));

		}
		// update when not paid yet
		Date today = new Date();
		PostOrder order;
		if (post.getStatus() == EStatus.WAIT_FOR_PAYMENT) {
			order = postOrderRepository.findByPostAndStatus(post, EROrderStatus.WAIT_FOR_PAYMENT);

			if (request.getDuration() != null && request.getDuration() != order.getDuration())
				order.setDuration(request.getDuration());

			if (request.getStartDate() != null && request.getStartDate().after(today))
				post.setStartDate(request.getStartDate());
			else
				post.setStartDate(today);

			// Format and caculate date
			SimpleDateFormat cmf = new SimpleDateFormat("yyyy-MM-dd");
			LocalDate date = LocalDate.parse(cmf.format(post.getStartDate()));
			LocalDate expirationDate = date.plusMonths(order.getDuration());

			post.setExpirationDate(Date.from(expirationDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));

			if (request.getService() != null && request.getService() != post.getService().getId()) {
				Optional<com.be.model.Service> opt = serviceRepository.findById(request.getService());
				if (opt.isEmpty())
					throw new CommonRuntimeException("Service not found with Id: " + request.getService());
				com.be.model.Service service = opt.get();

				post.setService(service);
				order.setService(service);
				order.setPrice(service.getPrice());
			}

			order.setTotal(order.getPrice() * order.getDuration());
		} else {
			// Update duration only if the start day is after to day and allow to move down
			order = postOrderRepository.findByPostAndStatus(post, EROrderStatus.PAID);

			LocalDate localDate1 = request.getStartDate().toInstant()
					.atZone(ZoneId.systemDefault())
					.toLocalDate();
			LocalDate localDate2 = post.getStartDate().toInstant()
					.atZone(ZoneId.systemDefault())
					.toLocalDate();
			// System.out.println(!localDate1.isEqual(localDate2));
			if (request.getStartDate() != null && !localDate1.isEqual(localDate2))
				if (post.getStartDate().after(today) && request.getStartDate().after(today)
						&& post.getStartDate().after(request.getStartDate())) {
					post.setStartDate(request.getStartDate());
					SimpleDateFormat cmf = new SimpleDateFormat("yyyy-MM-dd");
					LocalDate date = LocalDate.parse(cmf.format(post.getStartDate()));
					LocalDate expirationDate = date.plusMonths(order.getDuration());
					post.setExpirationDate(
							Date.from(expirationDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
				} else {
					throw new CommonRuntimeException(
							"You can not change the start day like this. \n If the post is already worked , you can not change the start date.");
				}
		}

		if (avatar != null)
			try {
				if (post.getAvatar() != null && !mediaResourceService.delete(post.getAvatar().getId()))
					throw new CommonRuntimeException("Error occur when deleting avatar please try again!");
				MediaResource mr = mediaResourceService.save(avatar.getBytes());
				post.setAvatar(mr);

			} catch (Exception e) {
				throw new CommonRuntimeException("Error occur when uploading avatar please try again !");
			}
		order.setPost(post);

		postOrderRepository.save(order);
		return new DataResponse<>(true, "Update Post Successfully !", modelMapper.map(post, PostDTO.class));
	}

	@Override
	@Transactional(timeout = 1200)
	public DataResponse<PostDTO> renew(Long empId, PostRenewRequest request) {

		Date today = new Date();

		if (request.getStartDate().before(today))
			request.setStartDate(today);

		Optional<Post> optPost = postRepository.findById(request.getId());

		if (optPost.isEmpty())
			throw new CommonRuntimeException("Post not found with id: " + request.getId());

		Post post = optPost.get();
		if (!post.getEmployer().getId().equals(empId))
			throw new CommonRuntimeException("Unable to update other employer's post.");

		if (post.getStatus() == EStatus.DELETED_BY_ADMIN) {
			return new DataResponse<>(false, "Post is already deleted by admin, cannot be renew !", null);
		}

		if (post.getExpirationDate().after(today))
			return new DataResponse<>(false, "Post has not expired yet !!", null);

		Optional<com.be.model.Service> service = serviceRepository.findById(request.getService());

		if (service.isEmpty())
			throw new CommonRuntimeException("Service not found with id: " + request.getService());

		if (request.getStartDate() == null)
			post.setStartDate(today);
		else
			post.setStartDate(request.getStartDate());

		Integer duration = request.getDuration();

		SimpleDateFormat cmf = new SimpleDateFormat("yyyy-MM-dd");
		LocalDate date = LocalDate.parse(cmf.format(post.getStartDate()));
		LocalDate expirationDate = date.plusMonths(duration);
		post.setExpirationDate(Date.from(expirationDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
		post.setStatus(EStatus.WAIT_FOR_PAYMENT);
		post.setService(service.get());

		Double price = service.get().getPrice();

		PostOrder order = new PostOrder();
		order.setCreatedDate(today);
		order.setDuration(request.getDuration());
		order.setPost(post);
		order.setPrice(price);
		order.setService(service.get());
		order.setStatus(EROrderStatus.WAIT_FOR_PAYMENT);
		order.setTotal(price * duration);
		order.setType(EOrderType.RENEW);
		order.setCurrency(ECurrency.VND);
		order.setPaymentDeadline(new Date(today.getTime() + 7 * 24 * 60 * 60 * 1000));

		postOrderRepository.save(order);
		return new DataResponse<>(true, "Renew post successfully. Pay to get your post active !.",
				modelMapper.map(post, PostDTO.class));
	}

	@Override
	@Transactional
	public BaseResponse deletePost(Long empId, Long postId) {
		Optional<Post> optPost = postRepository.findById(postId);

		if (optPost.isEmpty())
			throw new CommonRuntimeException("Post not found with id: " + postId);

		Post post = optPost.get();
		if (!post.getEmployer().getId().equals(empId))
			throw new CommonRuntimeException("Unable to delete other employer's post.");

		// Allow to delete when not paid yet
		if (post.getStatus() == EStatus.WAIT_FOR_PAYMENT) {
			// Post just be created, no cv submit, no comment, no like
			PostOrder order = postOrderRepository.findByPostAndStatus(post, EROrderStatus.WAIT_FOR_PAYMENT);
			postOrderRepository.delete(order);
			postRepository.delete(post);

			if (post.getAvatar() != null)
				mediaResourceService.delete(post.getAvatar().getId());

		} else if (post.getStatus() != EStatus.DELETED_BY_ADMIN) {
			post.setStatus(EStatus.DELETED);
			postRepository.save(post);
		} else {
			return new BaseResponse(false, "Post is already deleted by admin !");

		}

		return new BaseResponse(true, "Delete successfully post with id: " + postId);
	}

	@Override
	public DataResponse<PostDTO> getDetail(Long postId) {
		Optional<Post> optPost = postRepository.findById(postId);
		if (optPost.isEmpty()
				|| optPost.get().getStatus() != EStatus.ACTIVE && optPost.get().getStatus() != EStatus.DISABLE)
			throw new CommonRuntimeException("Post not found with id: " + postId);

		return new DataResponse<>(true, "", modelMapper.map(optPost.get(), PostDTO.class));
	}

	@Override
	public DataResponse<PostDTO> getEmployerPostDetail(Long empId, Long postId) {
		Optional<Post> optPost = postRepository.findById(postId);
		if (optPost.isEmpty())
			throw new CommonRuntimeException("Post not found with id: " + postId);
		Post post = optPost.get();
		if (post.getEmployer().getId() != empId)
			throw new CommonRuntimeException("Post not found with id: " + postId);

		return new DataResponse<>(true, "", modelMapper.map(post, PostDTO.class));
	}

}
