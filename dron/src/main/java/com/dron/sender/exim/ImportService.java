package com.dron.sender.exim;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import com.dron.sender.exceptions.DronSenderException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Service for exporting and importing sequences between dron and file system
 * 
 * @author Koropatva
 *
 */
public class ImportService {

	private static ImportService INSTANCE;

	private ObjectMapper mapper = new ObjectMapper();

	private ImportService() {
		mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
		mapper.configure(
				JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true);
	}

	public static ImportService getInstance() {
		if (INSTANCE == null) {
			synchronized (ImportService.class) {
				if (INSTANCE == null) {
					INSTANCE = new ImportService();
				}
			}
		}
		return INSTANCE;
	}

	/**
	 * Method for import sequence from the file
	 * 
	 * @param path
	 *            path for the file to import
	 * @return a sequence from the file
	 * @throws DronSenderException
	 */
	public <T> T imports(Object object, Class<T> importClass)
			throws DronSenderException {
		if (object instanceof Path) {
			return imports((Path) object, importClass);
		} else if (object instanceof InputStream) {
			return imports((InputStream) object, importClass);
		} else {
			throw new DronSenderException(
					"Current import type doesn't support.");
		}
	}

	/**
	 * Method for import sequence from the file
	 * 
	 * @param path
	 *            path for the file to import
	 * @return a sequence from the file
	 * @throws DronSenderException
	 */
	public <T> T imports(File file, Class<T> importClass)
			throws DronSenderException {
		return imports(file.toURI(), importClass);
	}

	/**
	 * Method for import sequence from the path
	 * 
	 * @param path
	 *            path for the file to import
	 * @return a sequence from the file
	 * @throws DronSenderException
	 */
	public <T> T imports(String path, Class<T> importClass)
			throws DronSenderException {
		return imports(Paths.get(URI.create(path)), importClass);
	}

	/**
	 * Method for import sequence from the URI
	 * 
	 * @param path
	 *            path for the file to import
	 * @return a sequence from the file
	 * @throws DronSenderException
	 */
	public <T> T imports(URI uri, Class<T> importClass)
			throws DronSenderException {
		return imports(Paths.get(uri), importClass);
	}

	/**
	 * Method for import sequence from the path
	 * 
	 * @param path
	 *            path for the file to import
	 * @return a sequence from the file
	 * @throws DronSenderException
	 */
	public <T> T imports(Path path, Class<T> importClass)
			throws DronSenderException {
		try {
			return imports(Files.newInputStream(path, StandardOpenOption.READ),
					importClass);
		} catch (IOException e) {
			throw new DronSenderException(e.getMessage());
		}
	}

	/**
	 * Method for import sequence from the InputStream
	 * 
	 * @param <T>
	 * 
	 * @param file
	 *            file to import
	 * @return a sequence from the file
	 * @throws DronSenderException
	 */
	public <T> T imports(InputStream src, Class<T> importClass)
			throws DronSenderException {
		try {
			return mapper.readValue(src, importClass);
		} catch (IOException e) {
			throw new DronSenderException(e.getMessage(), e);
		}
	}

}