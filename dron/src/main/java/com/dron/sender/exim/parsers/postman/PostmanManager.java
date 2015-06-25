package com.dron.sender.exim.parsers.postman;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Paths;

import com.dron.sender.exceptions.DronSenderException;
import com.dron.sender.exim.ImportService;
import com.dron.sender.exim.parsers.postman.models.PostmanVersionModel;
import com.dron.sender.exim.parsers.postman.v2.models.PostmanModel;
import com.dron.sender.exim.parsers.postman.v2.models.PostmanValueStructureModel;
import com.dron.sender.sequence.models.Sequence;
import com.dron.sender.transformers.TransformKey;
import com.dron.sender.transformers.TransformerFactory;

public class PostmanManager {

	private static PostmanManager INSTANCE;

	private ImportService importService = ImportService.getInstance();

	private PostmanManager() {
	}

	public static PostmanManager getInstance() {
		if (INSTANCE == null) {
			synchronized (PostmanManager.class) {
				if (INSTANCE == null) {
					INSTANCE = new PostmanManager();
				}
			}
		}
		return INSTANCE;
	}

	public Sequence parseRequest(URI uri, Sequence sequence)
			throws DronSenderException {
		PostmanVersionModel model = importService.imports(
				getPathOrInputStream(uri), PostmanVersionModel.class);
		if (model != null && model.getRequests() != null
				&& !model.getRequests().isEmpty()) {
			switch (model.getRequests().get(0).getVersion()) {
				case 2:
					return TransformerFactory.transformEntity(importService
							.imports(getPathOrInputStream(uri),
									PostmanModel.class), sequence,
							TransformKey.POSTMAN_V2_TO_SEQUENCE);
				default:
					throw new DronSenderException(
							String.format(
									"Can't find postman transformer for %d version of the postman data",
									model.getRequests().get(0).getVersion()));
			}
		}
		throw new DronSenderException("Some issue duaring parse postman data. ");
	}

	public Sequence parseValue(URI uri, Sequence sequence)
			throws DronSenderException {
		return TransformerFactory.transformEntity(importService.imports(
				getPathOrInputStream(uri), PostmanValueStructureModel.class), sequence,
				TransformKey.POSTMAN_VALUE_V2_TO_SEQUENCE);
	}

	private Object getPathOrInputStream(URI uri) throws DronSenderException {
		if (uri.getScheme() != null && uri.getScheme().startsWith("http")) {
			try {
				return uri.toURL().openStream();
			} catch (IOException e) {
				throw new DronSenderException(e.getMessage(), e);
			}
		} else {
			return Paths.get(uri);
		}
	}
}
