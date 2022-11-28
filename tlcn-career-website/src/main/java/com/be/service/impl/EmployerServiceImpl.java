package com.be.service.impl;

import java.io.IOException;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.be.controller.exception.CommonRuntimeException;
import com.be.dto.EmployerDTO;
import com.be.model.City;
import com.be.model.Employer;
import com.be.model.Field;
import com.be.model.MediaResource;
import com.be.payload.BaseResponse;
import com.be.payload.DataResponse;
import com.be.payload.auth.EmployerProfileCreateRequest;
import com.be.payload.auth.EmployerProfileUpdateRequest;
import com.be.payload.auth.PasswordUpdateRequest;
import com.be.payload.auth.ResetPasswordRequest;
import com.be.repository.AdminRepository;
import com.be.repository.CityRepository;
import com.be.repository.EmployerRepository;
import com.be.repository.FieldRepository;
import com.be.repository.UserRepository;
import com.be.service.EmployerService;
import com.be.service.MediaResourceService;
import com.be.service.SendEmailService;
import com.be.utility.EmailType;
import com.be.utility.RolePrefix;

@Service
public class EmployerServiceImpl implements EmployerService {

	@Autowired
	AdminRepository adminRepo;

	@Autowired
	UserRepository userRepo;

	@Autowired
	EmployerRepository employerRepository;

	@Autowired
	FieldRepository fieldRepository;

	@Autowired
	CityRepository cityRepository;
	@Autowired
	ModelMapper modelMapper;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	MediaResourceService mediaResourceService;

	@Autowired
	SendEmailService sendEmailService;

	@Override
	public DataResponse<EmployerDTO> create(EmployerProfileCreateRequest request) {
		// Check in admin table
		if (adminRepo.findByEmail(request.getEmail()).isPresent()) {
			throw new CommonRuntimeException("Email already exists");
		}

		// Check in user table
		if (userRepo.findByEmail(request.getEmail()).isPresent()) {
			throw new CommonRuntimeException("Email already exists");
		}
		if (userRepo.existsByPhone(request.getPhone())) {
			throw new CommonRuntimeException("Phone number already exists");
		}

		// Check in employer table
		if (employerRepository.findByEmail(request.getEmail()).isPresent()) {
			throw new CommonRuntimeException("Email already exists");
		}
		if (employerRepository.existsByPhone(request.getPhone())) {
			throw new CommonRuntimeException("Phone number already exists");
		}

		Field field = fieldRepository.getReferenceById(request.getField());

		City city = cityRepository.getReferenceById(request.getCity());

		Employer employer = modelMapper.map(request, Employer.class);
		employer.setPassword(passwordEncoder.encode(request.getPassword()));
		employer.setActive(true);
		employer.setEmailConfirm(false);
		employer.setField(field);
		employer.setCity(city);

		EmployerDTO result = modelMapper.map(employerRepository.save(employer), EmployerDTO.class);
		// Send code
		sendEmailService.sendVerifyCode(RolePrefix.EMPLOYER, request.getEmail(), EmailType.CONFIRM_EMAIL);
		return new DataResponse<>(true, "Register successfully.", result);
	}

	@Override
	public BaseResponse emailConfirm(String email, String code) {
		Optional<Employer> opt = employerRepository.findByEmail(email);
		if (opt.isEmpty())
			return new BaseResponse(false, "Employer not found !");
		Employer employer = opt.get();
		String empCode = employer.getCode();
		if (!empCode.equals(code))
			return new BaseResponse(false, "Code is not valid !");
		employer.setEmailConfirm(true);
		employer.setCode("");
		employerRepository.save(employer);
		return new BaseResponse(true, "Email confirm success fully !");

	}

	@Override
	public BaseResponse sendResetPasswordCode(String email) {
		Optional<Employer> opt = employerRepository.findByEmail(email);
		if (opt.isEmpty())
			return new BaseResponse(false, "User not found !");
		Employer employer = opt.get();
		if (!employer.getEmailConfirm())
			return new BaseResponse(false, "Unable to send verify code because the email is not confirmed yet.");
		sendEmailService.sendVerifyCode(RolePrefix.EMPLOYER, employer.getEmail(), EmailType.RESET_PASSWORD);
		return new BaseResponse(true, "Verify code sended successfully.");
	}

	@Override
	public BaseResponse changePasswordWithResetCode(ResetPasswordRequest request) {
		Optional<Employer> opt = employerRepository.findByEmail(request.getEmail());
		if (opt.isEmpty())
			return new BaseResponse(false, "User not found !");
		Employer employer = opt.get();
		String userCode = employer.getCode();
		if (!userCode.equals(request.getCode()))
			return new BaseResponse(false, "Code is not valid !");

		employer.setPassword(passwordEncoder.encode(request.getNewPassword()));
		employer.setCode("");
		employer = employerRepository.save(employer);
		return new BaseResponse(true, "Change password successfully !");

	}

	@Override
	public BaseResponse changePassword(String email, PasswordUpdateRequest request) {
		Optional<Employer> optEmployer = employerRepository.findByEmail(email);
		if (optEmployer.isEmpty())
			throw new CommonRuntimeException("User not found with Email: " + email);
		Employer employer = optEmployer.get();

		if (passwordEncoder.matches(request.getOldPassword(), employer.getPassword())) {
			employer.setPassword(passwordEncoder.encode(request.getNewPassword()));
			employerRepository.save(employer);
			return new BaseResponse(false, "Change password successfully !");
		}
		return new BaseResponse(false, "Old password is not valid !");

	}

	@Override
	public EmployerDTO getEmployerProfile(String email) {
		Optional<Employer> optEmployer = employerRepository.findByEmail(email);
		if (optEmployer.isEmpty())
			throw new CommonRuntimeException("User not found with Email: " + email);

		return modelMapper.map(optEmployer.get(), EmployerDTO.class);
	}

	@Override
	@Transactional
	public DataResponse<EmployerDTO> update(Long employerId, EmployerProfileUpdateRequest request,
			MultipartFile avatar) {
		Optional<Employer> optEmployer = employerRepository.findById(request.getId());
		if (optEmployer.isEmpty())
			throw new CommonRuntimeException("User not found with Id: " + request.getId());
		Employer employer = optEmployer.get();
		if (employer.getId() != employerId)
			throw new CommonRuntimeException("You do not have right to change other peple information !");
		if (request != null) {
			if (StringUtils.isNotBlank(request.getEmail()) && !request.getEmail().equals(employer.getEmail()))
				if (employerRepository.existsByEmail(request.getEmail())) {
					throw new CommonRuntimeException("Email already exists");
				} else {
					employer.setEmail(request.getEmail());
					employer.setEmailConfirm(false);
				}
			if (!StringUtils.isBlank(request.getPhone()) && !request.getPhone().equals(employer.getPhone()))
				if (employerRepository.existsByPhone(request.getPhone())) {
					throw new CommonRuntimeException("Phone number already exists");
				} else {
					employer.setPhone(request.getPhone());
				}
			// Name
			if (StringUtils.isNotBlank(request.getName()) && !request.getName().equals(employer.getName())) {
				employer.setName(request.getName());
			}
			// Employee
			if (request.getEmployee() != null && !request.getEmployee().equals(employer.getEmployee())) {
				employer.setEmployee(request.getEmployee());
			}
			// Address
			if (StringUtils.isNotBlank(request.getAddress()) && !request.getAddress().equals(employer.getAddress())) {
				employer.setAddress(request.getAddress());
			}
			// Description
			if (StringUtils.isNotBlank(request.getDescription())
					&& !request.getDescription().equals(employer.getDescription())) {
				employer.setDescription(request.getDescription());
			}
		}

		if (request.getCityId() != null) {
			City city = cityRepository.getReferenceById(request.getCityId());
			employer.setCity(city);
		}

		if (request.getFieldId() != null) {
			Field field = fieldRepository.getReferenceById(request.getFieldId());
			employer.setField(field);
		}

		if (avatar != null) {
			if (employer.getAvatar() != null)
				if (!mediaResourceService.delete(employer.getAvatar().getId())) {
					throw new CommonRuntimeException("Error occur when deleting old image !");
				}
			// Up load new Image
			try {
				MediaResource img = mediaResourceService.save(avatar.getBytes());
				employer.setAvatar(img);
			} catch (IOException e) {
				throw new CommonRuntimeException("Error occur when uploading new image !");
			}
		}

		employer = employerRepository.save(employer);

		return new DataResponse<>(true, "Update information successfully.", modelMapper.map(employer, EmployerDTO.class));
	}

	@Override
	public EmployerDTO getEmployerById(Long id) {
		Optional<Employer> optEmployer = employerRepository.findById(id);
		if (optEmployer.isEmpty())
			throw new CommonRuntimeException("Employer not found with Id: " + id);

		return modelMapper.map(optEmployer.get(), EmployerDTO.class);
	}

}
