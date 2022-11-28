package com.be.service.impl;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.be.controller.exception.CommonRuntimeException;
import com.be.service.CloudinaryService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import lombok.Getter;
import lombok.Setter;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {
	public static final String CLOUDINARY_CLOUD_NAME = "deixbfjom";
	public static final String CLOUDINARY_API_KEY = "517247982874954";
	public static final String CLOUDINARY_API_SECRET = "ae8IKE13I0bmU6koLgPuwwPKqbc";

	private Cloudinary cloudinary;

	public CloudinaryServiceImpl() {
		cloudinary = new Cloudinary(ObjectUtils.asMap("cloud_name", CLOUDINARY_CLOUD_NAME, "api_key",
				CLOUDINARY_API_KEY, "api_secret", CLOUDINARY_API_SECRET, "secure", true));
	}

	@Override
	public CloudinaryUploadResponse upload(byte[] data) throws IOException {
		Map response = cloudinary.uploader().upload(data, ObjectUtils.asMap("resource_type", "auto"));

		return new CloudinaryUploadResponse(response.get("secure_url").toString(), response.get("public_id").toString(),
				response.get("resource_type").toString());
	}

	@Override
	public boolean delete(String publicId) throws IOException {
		Map response = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());

		return response.get("result").toString().equals("ok");
	}

	@Getter
	@Setter
	public class CloudinaryUploadResponse {
		private String url;
		private String publicId;
		private String resourceType;

		public CloudinaryUploadResponse(String url, String publicId, String resourceType) {
			if (url.isEmpty()) {
				throw new CommonRuntimeException(
						"Upload file to cloudinary failed (no URL to access uploaded media found in Cloudinary response)");
			}
			if (publicId.isEmpty()) {
				throw new CommonRuntimeException(
						"Upload file to cloudinary failed (no public_id found in Cloudinary response)");
			}
			if (resourceType.isEmpty()) {
				throw new CommonRuntimeException(
						"Upload file to cloudinary failed (no resource_type found in Cloudinary response)");
			}

			this.url = url;
			this.publicId = publicId;
			this.resourceType = resourceType;
		}
	}
}
