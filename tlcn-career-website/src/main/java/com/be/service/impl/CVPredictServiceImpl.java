package com.be.service.impl;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.be.controller.exception.CommonRuntimeException;
import com.be.dto.PostDTO;
import com.be.model.Field;
import com.be.model.Profile;
import com.be.model.pk.ProfilePK;
import com.be.payload.ListWithPagingResponse;
import com.be.payload.predict.CVPredictResponse;
import com.be.payload.predict.JobOptionResponse;
import com.be.repository.FieldRepository;
import com.be.repository.PostRepository;
import com.be.repository.ProfileRepository;
import com.be.service.CVPredictService;
import com.be.utility.Page;
import com.be.utility.datatype.EStatus;

@Service
public class CVPredictServiceImpl implements CVPredictService {
	@Value("${career.app.ai.url}")
	String aiUurl;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	ProfileRepository profileRepository;

	@Autowired
	FieldRepository fieldRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PostRepository postRepository;

	@Override
	public CVPredictResponse<PostDTO> predict(Long mediaId, Long userId) {
		Profile profile = profileRepository.getReferenceById(new ProfilePK(userId, mediaId));

		String url = profile.getMediaResource().getUrl();
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

			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

			HttpEntity<String> entity = new HttpEntity<>(headers);

			String response = restTemplate.exchange(aiUurl, HttpMethod.POST, entity, String.class, text).getBody();
			response = response.replace("\"", "");

			// Handle String
			Map<String, String> dict = new HashMap<>();

			String highestPercent = null;
			for (String s : response.split(" - ")) {
				dict.put(s.split(" ")[1], s.split(" ")[0]);
				if (highestPercent == null)
					highestPercent = s.split(" ")[1];
			}

			List<Field> lstField = fieldRepository.findByListCode(dict.keySet());

			List<JobOptionResponse> jobOptionResponses = new ArrayList<>();

			for (int i = 0; i < lstField.size(); i++)
				jobOptionResponses.add(new JobOptionResponse(lstField.get(i).getCode(), lstField.get(i).getName(),
						dict.get(lstField.get(i).getCode())));

			Long fieldId = null;
			String fieldName = "";
			for (Field fi : lstField)
				if (fi.getCode().equals(highestPercent)) {
					fieldId = fi.getId();
					fieldName = fi.getName();
					break;
				}

			int count = postRepository
					.adminCountBeforeSearch(null, null, null, null, null, fieldId, null, EStatus.ACTIVE, null,
							null, null)
					.intValue();

			Page page = new Page(null, null, count);

			List<PostDTO> posts = postRepository
					.adminSearch(null, null, null, null, null, fieldId, null, EStatus.ACTIVE, null, null, null,
							page)
					.stream()
					.map(p -> modelMapper.map(p, PostDTO.class)).toList();

			return new CVPredictResponse<>(page.getPageNumber() + 1, page.getTotalPage(), posts, jobOptionResponses,
					fieldName);

		} catch (Exception e) {
			throw new CommonRuntimeException("Can not predict the cv please upload other cv !");
		}

	}

	@Override
	public ListWithPagingResponse<PostDTO> getPostByField(String code, Integer pageNumber, Integer limit) {
		Optional<Field> optField = fieldRepository.findByCode(code);
		if (optField.isEmpty())
			throw new CommonRuntimeException("Field not found with code : " + code);
		Field field = optField.get();

		int count = postRepository
				.adminCountBeforeSearch(null, null, null, null, null, field.getId(), null, null, null, null, null)
				.intValue();

		Page page = new Page(pageNumber, limit, count);

		List<PostDTO> posts = postRepository
				.adminSearch(null, null, null, null, null, field.getId(), null, null, null, null, null, page).stream()
				.map(p -> modelMapper.map(p, PostDTO.class)).toList();

		return new ListWithPagingResponse<>(page.getPageNumber() + 1, page.getTotalPage(), page.getPageSize(), posts);
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
