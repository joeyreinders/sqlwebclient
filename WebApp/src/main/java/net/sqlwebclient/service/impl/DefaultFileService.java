package net.sqlwebclient.service.impl;

import com.google.common.collect.ImmutableList;
import net.sqlwebclient.service.FileService;
import net.sqlwebclient.util.Toolkit;
import net.sqlwebclient.util.logging.Log;
import net.sqlwebclient.util.logging.wrappers.LogFactory;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.List;

final class DefaultFileService implements FileService {
	private static final Log logger = LogFactory.getLog(DefaultFileService.class);

	private final String tempDir = FileUtils.getTempDirectoryPath() + File.separator + "SQLWebClient";
	private boolean tempDirCheck = true;

	@Override
	public String[] getFiles() {
		final File[] files = listFiles();
		final String[] res = new String[files.length];
		for(int i = 0; i < res.length ; i++) {
			res[i] = files[i].getName();
		}

		return res;
	}

	@Override
	public void clearFiles() {
		for(File file: listFiles()) {
			final String fileName = file.getName();
			final boolean res = file.delete();
			logger.debug("file with filename " + fileName + " deleted? " + res);
		}
	}

	@Override
	public String getTempDirectory() {
		createTempDirectoryIfNeeded();

		return tempDir;
	}

	@Override
	public File getTempDir() {
		return new File(getTempDirectory());
	}

	@Override
	public boolean createFile(final String fileName, final InputStream inputStream) {
		final File newFile = new File(getTempDirectory() + File.separator + fileName);
		logger.debug("creating new file: " + newFile);
		if(newFile.exists()) {
			logger.debug("file already exists");
			newFile.delete();
		}

		try {
			newFile.createNewFile();
			final OutputStream out = new FileOutputStream(newFile);
			Toolkit.copy(inputStream, out);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public String[] getFiles(final String fileType) {
		final ImmutableList.Builder<String> builder = ImmutableList.builder();

		for(File file: listFiles()) {
			if(file.getName().endsWith(fileType)) {
				builder.add(file.getName());
			}
		}

		final List<String> res = builder.build();
		return res.toArray(new String[res.size()]);
	}

	private void createTempDirectoryIfNeeded() {
		if(! tempDirCheck) {
			return;
		}

		final File f = new File(tempDir);
		if(f.exists()) {
			logger.debug("tempdir already exists");
			tempDirCheck = false;
		} else {
			final boolean created = f.mkdir();
			logger.debug("tempdir created? " + created);
			tempDirCheck = false;
		}
	}

	private File[] listFiles() {
		final File[] files = getTempDir().listFiles();
		return files == null ? new File[]{} : files;
	}
}
