package com.be.service;

import com.be.payload.BaseResponse;

public interface SendEmailService {

	BaseResponse sendVerifyCode(String role, String email, String subject);

}
