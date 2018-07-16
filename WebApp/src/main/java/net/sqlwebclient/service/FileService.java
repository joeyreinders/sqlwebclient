package net.sqlwebclient.service;

import java.io.File;
import java.io.InputStream;

public interface FileService {
	String[] getFiles();

	void clearFiles();

	String getTempDirectory();

	File getTempDir();

	boolean createFile(final String fileName,
					   final InputStream inputStream);

	String[] getFiles(final String fileType);
}
