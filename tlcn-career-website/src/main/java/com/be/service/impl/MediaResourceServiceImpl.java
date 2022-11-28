package com.be.service.impl;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.be.controller.exception.CommonRuntimeException;
import com.be.model.MediaResource;
import com.be.repository.MediaResourceRepository;
import com.be.service.CloudinaryService;
import com.be.service.MediaResourceService;
import com.be.service.impl.CloudinaryServiceImpl.CloudinaryUploadResponse;

@Service
public class MediaResourceServiceImpl implements MediaResourceService {
	@Autowired
	private MediaResourceRepository repository;

	@Autowired
	CloudinaryService cloudinaryService;

	@Override
	public MediaResource save(byte[] data) {
		try {
			CloudinaryUploadResponse resp = cloudinaryService.upload(data);
			MediaResource m = new MediaResource(resp.getUrl(), resp.getPublicId(), resp.getResourceType());
			return repository.save(m);
		} catch (IOException e) {
			throw new CommonRuntimeException(
					"IOException occurred when upload data to Cloudinary service (" + e.getMessage() + ")");
		}
	}

	@Override
	public boolean delete(Long id) {
		Optional<MediaResource> m = repository.findById(id);
		if (!m.isPresent()) {
			throw new CommonRuntimeException("Media resource can not be found");
		}

		try {
			if (cloudinaryService.delete(m.get().getPublicId())) {
				repository.delete(m.get());
				return true;
			} else {
				return false;
			}
		} catch (IOException e) {
			throw new CommonRuntimeException(
					"IOException occurred when delete data from Cloudinary service (" + e.getMessage() + ")");
		}
	}
}
