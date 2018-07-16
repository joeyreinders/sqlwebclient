package net.sqlwebclient.service.impl;

import com.google.inject.Inject;
import net.sqlwebclient.service.FileService;
import net.sqlwebclient.service.UploadService;
import net.sqlwebclient.upload.UploadType;

import java.io.InputStream;

final class DefaultUploadService implements UploadService {
	final FileService fileService;

	@Inject
	DefaultUploadService(final FileService fileService) {
		this.fileService = fileService;
	}

	@Override
	public void upload(	final String fileName,
						final InputStream is,
					   	final UploadType uploadType) {
		fileService.createFile(fileName, is);
	}
}
