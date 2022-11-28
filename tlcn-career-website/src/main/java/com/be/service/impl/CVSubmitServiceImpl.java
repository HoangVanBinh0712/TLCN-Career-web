package com.be.service.impl;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.be.controller.exception.CommonRuntimeException;
import com.be.dto.CVSubmitDTO;
import com.be.model.CVSubmit;
import com.be.model.Post;
import com.be.model.Profile;
import com.be.model.pk.CVSubmitPK;
import com.be.model.pk.ProfilePK;
import com.be.payload.BaseResponse;
import com.be.payload.ListWithPagingResponse;
import com.be.repository.CVSubmitRepository;
import com.be.repository.PostRepository;
import com.be.repository.ProfileRepository;
import com.be.repository.UserRepository;
import com.be.service.CVSubmitService;
import com.be.utility.datatype.EStatus;

import me.xdrop.fuzzywuzzy.FuzzySearch;

@Service
public class CVSubmitServiceImpl implements CVSubmitService {

	@Autowired
	CVSubmitRepository cvSubmitRepository;

	@Autowired
	ProfileRepository profileRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	PostRepository postRepository;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public ListWithPagingResponse<CVSubmitDTO> getListCV(Long employerId, Long postId, Integer page, Integer limit) {

		Optional<Post> optPost = postRepository.findById(postId);
		if (optPost.isEmpty())
			throw new CommonRuntimeException("Post not found with id: " + postId);
		Post post = optPost.get();
		if (post.getEmployer().getId() != employerId)
			throw new CommonRuntimeException("You do not have right to get this resource !");
		if (page == null || page <= 0)
			page = 1;
		if (limit == null || limit <= 0)
			limit = 6;

		List<CVSubmit> lstCV = cvSubmitRepository.findByPost(postId, (page - 1) * limit, limit);

		if (lstCV.isEmpty())
			throw new CommonRuntimeException("No cv is submitted to this post !.");

		Integer count = cvSubmitRepository.countByPost(postId).intValue();
		Integer totalPage;
		if (count % limit != 0)
			totalPage = count / limit + 1;
		else
			totalPage = count / limit;
		return new ListWithPagingResponse<>(page, totalPage, limit,
				lstCV.stream().map(cv -> modelMapper.map(cv, CVSubmitDTO.class)).toList());
	}

	@Override
	public BaseResponse sumbitCV(Long userId, Long postId, Long mediaId) {
		// Check if user submitted

		Optional<CVSubmit> optCVSubmit = cvSubmitRepository.findByUserAndPost(userId, postId);
		if (optCVSubmit.isPresent())
			throw new CommonRuntimeException(
					"You submitted your cv to this post. If you want to change your cv please delete and then submit again. !");

		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new CommonRuntimeException("Post not found with Id: " + postId));
		EStatus status = post.getStatus();
		Date today = new Date();
		if (status != EStatus.ACTIVE || post.getStartDate().after(today) || post.getExpirationDate().before(today))
			return new BaseResponse(false, "Post is not available to submit cv !");
		
		Profile profile = profileRepository.getReferenceById(new ProfilePK(userId, mediaId));

		CVSubmitPK pk = new CVSubmitPK(postId, userId, mediaId);

		CVSubmit cvSubmit = new CVSubmit();

		cvSubmit.setId(pk);
		cvSubmit.setDate(new Date());
		cvSubmit.setPost(post);
		cvSubmit.setProfile(profile);
		String url = profile.getMediaResource().getUrl();
		if (!url.endsWith(".pdf"))
			throw new CommonRuntimeException("CV is invalid ! Accept pdf only.");
		try {
			byte[] fileContent = IOUtils.toByteArray(new URL(url));

			PDDocument document = PDDocument.load(fileContent);

			// Instantiate PDFTextStripper class
			PDFTextStripper pdfStripper = new PDFTextStripper();
			// Retrieving text from PDF document
			String text = pdfStripper.getText(document);

			// Closing the document
			document.close();

			text = cleanResume(text);
			String des = cleanResume(post.getDescription());
			// Calculate the match %
			cvSubmit.setMatchPercent((long) FuzzySearch.tokenSetRatio(des, text));

			cvSubmitRepository.save(cvSubmit);
		} catch (Exception e) {
			return new BaseResponse(true, "Can not read the cv please upload other cv !");
		}

		return new BaseResponse(true, "Submit CV successfully !");

	}

	@Override
	public BaseResponse deleteSumbittedCV(Long userId, Long postId, Long mediaId) {
		Optional<CVSubmit> optCVSubmit = cvSubmitRepository.findById(new CVSubmitPK(postId, userId, mediaId));
		if (optCVSubmit.isEmpty())
			throw new CommonRuntimeException("CV not found. Please check again !");

		cvSubmitRepository.delete(optCVSubmit.get());

		return new BaseResponse(true, "Cancel submit CV successfully !");
	}

	public String cleanResume(String resume) {
		resume = resume.replaceAll("http\\S+\\s*", "");

		resume = resume.replaceAll("\\s+", " ");
		resume = resume.replaceAll("\"[^\\\\x00-\\\\x7f]\"", " ");
		resume = resume.replaceAll("@\\S+", " ");
		resume = resume.replaceAll("#\\S+", " ");
		resume = resume.replaceAll("RT|cc", " ");

		return resume;
	}

}
