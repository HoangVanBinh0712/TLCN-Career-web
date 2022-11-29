package com.be.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.be.controller.exception.CommonRuntimeException;
import com.be.dto.CVSubmitDTO;
import com.be.dto.PostDTO;
import com.be.dto.ProfileDTO;
import com.be.dto.UserDTO;
import com.be.dto.loaduser.LoadUserDTO;
import com.be.model.CVSubmit;
import com.be.model.City;
import com.be.model.MediaResource;
import com.be.model.Profile;
import com.be.model.User;
import com.be.model.pk.ProfilePK;
import com.be.payload.BaseResponse;
import com.be.payload.DataResponse;
import com.be.payload.ListWithPagingResponse;
import com.be.payload.auth.PasswordUpdateRequest;
import com.be.payload.auth.ResetPasswordRequest;
import com.be.payload.auth.UserProfileCreateRequest;
import com.be.payload.auth.UserProfileUpdateRequest;
import com.be.payload.profile.CVSubmittedPost;
import com.be.repository.AdminRepository;
import com.be.repository.CVSubmitRepository;
import com.be.repository.CityRepository;
import com.be.repository.EmployerRepository;
import com.be.repository.ProfileRepository;
import com.be.repository.UserRepository;
import com.be.service.MediaResourceService;
import com.be.service.SendEmailService;
import com.be.service.UserService;
import com.be.utility.EmailType;
import com.be.utility.RolePrefix;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepo;

	@Autowired
	EmployerRepository empRepo;

	@Autowired
	AdminRepository adminRepo;

	@Autowired
	CityRepository cityRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	ProfileRepository profileRepository;

	@Autowired
	MediaResourceService mediaResourceService;

	@Autowired
	CVSubmitRepository cvSubmitRepository;

	@Autowired
	SendEmailService sendEmailService;

	@Override
	public DataResponse<UserDTO> create(UserProfileCreateRequest request) {

		// Check in admin table
		if (adminRepo.findByEmail(request.getEmail()).isPresent()) {
			throw new CommonRuntimeException("Email already exists");
		}
		// Check in employer table
		if (empRepo.findByEmail(request.getEmail()).isPresent()) {
			throw new CommonRuntimeException("Email already exists");
		}
		if (empRepo.existsByPhone(request.getPhone())) {
			throw new CommonRuntimeException("Phone number already exists");
		}

		// Check in user table
		if (userRepo.findByEmail(request.getEmail()).isPresent()) {
			throw new CommonRuntimeException("Email already exists");
		}
		if (userRepo.existsByPhone(request.getPhone())) {
			throw new CommonRuntimeException("Phone number already exists");
		}

		// All good
		User user = modelMapper.map(request, User.class);
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setActive(true);
		// Next step use will confirm email so just set it to false
		user.setEmailConfirm(false);

		user = userRepo.save(user);

		// Then send Code for user
		sendEmailService.sendVerifyCode(RolePrefix.USER, request.getEmail(), EmailType.CONFIRM_EMAIL);
		return new DataResponse<>(true, "Register successfully.", modelMapper.map(user, UserDTO.class));
	}

	@Override
	public BaseResponse sendResetPasswordCode(String email) {
		Optional<User> opt = userRepo.findByEmail(email);
		if (opt.isEmpty())
			return new BaseResponse(false, "User not found !");
		User user = opt.get();
		if (!user.getEmailConfirm())
			return new BaseResponse(false, "Unable to send verify code because the email is not confirmed yet.");
		sendEmailService.sendVerifyCode(RolePrefix.USER, user.getEmail(), EmailType.RESET_PASSWORD);
		return new BaseResponse(true, "Verify code sended successfully.");
	}

	@Override
	public BaseResponse changePasswordWithResetCode(ResetPasswordRequest request) {
		Optional<User> opt = userRepo.findByEmail(request.getEmail());
		if (opt.isEmpty())
			return new BaseResponse(false, "User not found !");
		User user = opt.get();
		String userCode = user.getCode();
		if (!userCode.equals(request.getCode()))
			return new BaseResponse(false, "Code is not valid !");

		user.setPassword(passwordEncoder.encode(request.getNewPassword()));
		user.setCode("");
		user = userRepo.save(user);
		return new BaseResponse(true, "Change password successfully !");

	}

	@Override
	public BaseResponse emailConfirm(String email, String code) {
		Optional<User> opt = userRepo.findByEmail(email);
		if (opt.isEmpty())
			return new BaseResponse(false, "User not found !");
		User user = opt.get();
		String userCode = user.getCode();
		if (!userCode.equals(code))
			return new BaseResponse(false, "Code is not valid !");
		user.setEmailConfirm(true);
		user.setCode("");
		userRepo.save(user);
		return new BaseResponse(true, "Email confirm successfully !");

	}

	@Override
	public UserDTO getUserProfile(String email) {
		Optional<User> optUser = userRepo.findByEmail(email);
		if (optUser.isEmpty())
			throw new CommonRuntimeException("User not found with Email: " + email);

		return modelMapper.map(optUser.get(), UserDTO.class);
	}

	@Override
	@Transactional
	public DataResponse<UserDTO> update(Long userId, UserProfileUpdateRequest request, MultipartFile avatar) {
		Optional<User> optUser = userRepo.findById(request.getId());
		if (optUser.isEmpty())
			throw new CommonRuntimeException("User not found with Id: " + request.getId());
		User user = optUser.get();
		if (user.getId() != userId)
			throw new CommonRuntimeException("You do not have right to change other peple information !");
		if (request != null) {
			if (!StringUtils.isBlank(request.getEmail()) && !request.getEmail().equals(user.getEmail()))
				if (userRepo.existsByEmail(request.getEmail())) {
					throw new CommonRuntimeException("Email already exists");
				} else {
					user.setEmail(request.getEmail());
					user.setEmailConfirm(false);
				}
			if (!StringUtils.isBlank(request.getPhone()) && !request.getPhone().equals(user.getPhone()))
				if (userRepo.existsByPhone(request.getPhone())) {
					throw new CommonRuntimeException("Phone number already exists");
				} else {
					user.setPhone(request.getPhone());
				}

			if (StringUtils.isNotBlank(request.getName()) && !request.getName().equals(user.getName())) {
				user.setName(request.getName());
			}
			if (request.getGender() != null && !request.getGender().equals(user.getGender())) {
				user.setGender(request.getGender());
			}
			if (request.getBirth() != null && !request.getBirth().equals(user.getBirth())) {
				user.setBirth(request.getBirth());
			}
			if (request.getAddress() != null && !request.getAddress().equals(user.getAddress())) {
				user.setAddress(request.getAddress());
			}
		}

		if (request.getCityId() != null) {
			City city = cityRepository.getReferenceById(request.getCityId());
			user.setCity(city);
		}

		if (avatar != null) {
			// Delete old image
			if (user.getAvatar() != null) {
				if (!mediaResourceService.delete(user.getAvatar().getId()))
					throw new CommonRuntimeException("Error occur when deleting old image !");
			}
			// Up load new Image
			try {
				MediaResource img = mediaResourceService.save(avatar.getBytes());
				user.setAvatar(img);
			} catch (IOException e) {
				throw new CommonRuntimeException("Error occur when uploading new image !");
			}
		}

		user = userRepo.save(user);

		return new DataResponse<>(true, "Update information successfully.", modelMapper.map(user, UserDTO.class));
	}

	@Override
	public BaseResponse changePassword(String email, PasswordUpdateRequest request) {
		Optional<User> optUser = userRepo.findByEmail(email);
		if (optUser.isEmpty())
			throw new CommonRuntimeException("User not found with Email: " + email);
		User user = optUser.get();

		if (passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
			user.setPassword(passwordEncoder.encode(request.getNewPassword()));
			userRepo.save(user);

			return new BaseResponse(true, "Change password successfully !");
		}
		return new BaseResponse(false, "Old password is not valid !");

	}

	@Override
	public BaseResponse uploadCV(String email, MultipartFile cv, Boolean isDefault, String name) {
		Optional<User> optUser = userRepo.findByEmail(email);
		if (optUser.isEmpty())
			throw new CommonRuntimeException("User not found with Email: " + email);
		User user = optUser.get();

		MediaResource mr = null;
		try {
			mr = mediaResourceService.save(cv.getBytes());
			ProfilePK pk = new ProfilePK(user.getId(), mr.getId());

			Profile profile = new Profile();
			profile.setId(pk);
			profile.setMediaResource(mr);
			profile.setUser(user);
			profile.setIsDefault(isDefault);
			profile.setName(name);

			if (isDefault)
				profileRepository.updateDefaultToFalse(user.getId());
			profileRepository.save(profile);

		} catch (Exception e) {
			if (mr != null)
				mediaResourceService.delete(mr.getId());
			return new BaseResponse(false, e.getMessage());
		}

		return new BaseResponse(true, "Success !");
	}

	@Override
	public List<ProfileDTO> getListCV(Long userId) {
		return profileRepository.findByUserId(userId).stream().map(p -> modelMapper.map(p, ProfileDTO.class)).toList();
	}

	@Override
	public ProfileDTO getCVDetail(Long userId, Long mediaId) {

		Optional<Profile> optProfile = profileRepository.findById(new ProfilePK(userId, mediaId));
		if (optProfile.isEmpty())
			throw new CommonRuntimeException("Profile not found with id:" + mediaId);

		return modelMapper.map(optProfile.get(), ProfileDTO.class);
	}

	@Override
	public ListWithPagingResponse<CVSubmittedPost> getCVSubmittedPost(Long userId, Integer page, Integer limit) {

		if (page == null || page <= 0)
			page = 1;
		if (limit == null || limit <= 0)
			limit = 6;

		List<CVSubmit> lstCV = cvSubmitRepository.findByUser(userId, (page - 1) * limit, limit);

		if (lstCV.isEmpty())
			throw new CommonRuntimeException("No cv is submitted to this post !.");

		Integer count = cvSubmitRepository.countByUser(userId).intValue();
		Integer totalPage;
		if (count % limit != 0)
			totalPage = count / limit + 1;
		else
			totalPage = count / limit;
		List<CVSubmittedPost> result = new ArrayList<>();
		for (CVSubmit cv : lstCV) {
			result.add(new CVSubmittedPost(modelMapper.map(cv.getPost(), PostDTO.class),
					modelMapper.map(cv, CVSubmitDTO.class)));
		}

		return new ListWithPagingResponse<>(page, totalPage, limit, result);
	}

	@Override
	@Transactional
	public BaseResponse deleteCV(Long userId, Long mediaId) {
		ProfilePK profilePK = new ProfilePK(userId, mediaId);
		Optional<Profile> profile = profileRepository.findById(profilePK);
		if (profile.isEmpty())
			throw new CommonRuntimeException("Profile not found with id:" + mediaId);
		// Delete in cv submit
		cvSubmitRepository.deleteByProfile(userId, mediaId);

		profileRepository.delete(profile.get());
		mediaResourceService.delete(mediaId);

		return new BaseResponse(true, "Delete cv successfully !");
	}

	@Override
	public LoadUserDTO getLoadUser(String email) {
		Optional<User> optUser = userRepo.findByEmail(email);
		if (optUser.isEmpty())
			throw new CommonRuntimeException("User not found with Email: " + email);

		return modelMapper.map(optUser.get(), LoadUserDTO.class);
	}

}
