package net.sqlwebclient.service;

import net.sqlwebclient.upload.UploadType;

import java.io.InputStream;

public interface UploadService {
	void upload(final String fileName,
				final InputStream is,
				final UploadType uploadType);
}
