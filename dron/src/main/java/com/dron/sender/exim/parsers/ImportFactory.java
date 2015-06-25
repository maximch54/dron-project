package com.dron.sender.exim.parsers;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.stereotype.Service;

import com.dron.sender.exceptions.DronSenderException;
import com.dron.sender.exim.parsers.postman.PostmanManager;
import com.dron.sender.sequence.models.Sequence;

@Service
public class ImportFactory {

	private static ImportFactory INSTANCE;

	private ImportFactory() {
	}

	public static ImportFactory getInstance() {
		if (INSTANCE == null) {
			synchronized (ImportFactory.class) {
				if (INSTANCE == null) {
					INSTANCE = new ImportFactory();
				}
			}
		}
		return INSTANCE;
	}

	private PostmanManager postmanManager = PostmanManager.getInstance();

	public Sequence importSequence(File file, ParserType type)
			throws DronSenderException {
		Sequence sequence = new Sequence();
		return importSequence(file, sequence, type);
	}

	public Sequence importSequence(URI uri, ParserType type)
			throws DronSenderException {
		Sequence sequence = new Sequence();
		return importSequence(uri, sequence, type);
	}

	public Sequence importSequence(File file, Sequence sequence, ParserType type)
			throws DronSenderException {
		try {
			return importSequence(new URI("file:" + file.getPath()), sequence,
					type);
		} catch (URISyntaxException e) {
			throw new DronSenderException(e.getMessage(), e);
		}
	}

	public Sequence importSequence(URI uri, Sequence sequence, ParserType type)
			throws DronSenderException {
		switch (type) {
			case POSTMAN_VALUES:
				postmanManager.parseValue(uri, sequence);
				break;
			case POSTMAN_REQUESTS:
				postmanManager.parseRequest(uri, sequence);
				break;

			default:
				throw new DronSenderException(String.format(
						"%s for now is not implemented.", type.name()));
		}
		return sequence;
	}
}
