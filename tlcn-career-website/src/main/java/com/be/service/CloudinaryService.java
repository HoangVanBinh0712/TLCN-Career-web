package com.be.service;

import java.io.IOException;

import com.be.service.impl.CloudinaryServiceImpl.CloudinaryUploadResponse;

public interface CloudinaryService {
	CloudinaryUploadResponse upload(byte[] data) throws IOException;

	boolean delete(String publicId) throws IOException;
}
