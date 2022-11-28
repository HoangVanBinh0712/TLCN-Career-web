package com.be.service.impl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.be.controller.exception.CommonRuntimeException;
import com.be.model.Employer;
import com.be.model.User;
import com.be.payload.BaseResponse;
import com.be.repository.EmployerRepository;
import com.be.repository.UserRepository;
import com.be.service.SendEmailService;
import com.be.utility.EmailType;
import com.be.utility.RandomString;
import com.be.utility.RolePrefix;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Service
public class SendEmailServiceImpl implements SendEmailService {

	@Autowired
	UserRepository userRepo;

	@Autowired
	EmployerRepository empRepo;

	@Autowired
	private JavaMailSender sender;

	@Autowired
	private Configuration config;

	@Value("${spring.mail.username}")
	private String appEmail;

	@Value("${email.content.confirmemail}")
	private String content;

	@Value("${email.content.resetpassword}")
	private String contentRP;

	@Override
	public BaseResponse sendVerifyCode(String role, String email, String subject) {
		String code = RandomString.get(10);
		String name = "";

		if (role.equals(RolePrefix.EMPLOYER)) {
			Optional<Employer> oEmp = empRepo.findByEmail(email);
			if (oEmp.isEmpty()) {
				throw new CommonRuntimeException("No employer is associated with this email");
			}
			Employer emp = oEmp.get();
			name = emp.getName();
			emp.setCode(code);
			empRepo.save(emp);
		} else if (role.equals(RolePrefix.USER)) {
			Optional<User> oUser = userRepo.findByEmail(email);
			if (oUser.isEmpty()) {
				throw new CommonRuntimeException("No buyer user is associated with this email");
			}
			User user = oUser.get();
			name = user.getName();
			user.setCode(code);
			userRepo.save(user);
		}
		MimeMessage message = sender.createMimeMessage();
		try {
			// set mediaType
			MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
					StandardCharsets.UTF_8.name());
			// add attachment

			Template t = config.getTemplate("verify-code-email-template.ftl");
			Map<String, Object> model = new HashMap<>();
			if (subject.equals(EmailType.RESET_PASSWORD)) {
				model.put("subject", "Reset Password");
				model.put("content", contentRP);

			} else {
				model.put("subject", "Confirm Email");
				model.put("content", content);

			}

			model.put("name", name);
			model.put("email", email);
			model.put("code", code);
			String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

			helper.setTo(email);
			helper.setText(html, true);
			helper.setSubject(subject);
			helper.setFrom(appEmail);
			sender.send(message);

		} catch (MessagingException | IOException | TemplateException | MailException e) {
			throw new CommonRuntimeException(String.format("Send verify code failed (%s)", e.getMessage()));
		}

		return new BaseResponse(true, "Send code success fully ! Check your email to recieve code.");
	}
}
