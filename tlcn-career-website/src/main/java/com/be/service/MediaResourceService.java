package com.be.service;

import com.be.model.MediaResource;

public interface MediaResourceService {

	MediaResource save(byte[] data);

	boolean delete(Long id);

}