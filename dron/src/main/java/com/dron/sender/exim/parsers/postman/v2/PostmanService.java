package com.dron.sender.exim.parsers.postman.v2;

import java.nio.file.Path;

import com.dron.sender.exceptions.DronSenderException;
import com.dron.sender.exim.ImportService;
import com.dron.sender.exim.parsers.postman.IPostmanService;
import com.dron.sender.exim.parsers.postman.v2.models.PostmanModel;
import com.dron.sender.exim.parsers.postman.v2.models.PostmanValueStructureModel;
import com.dron.sender.sequence.models.Sequence;
import com.dron.sender.transformers.TransformKey;
import com.dron.sender.transformers.TransformerFactory;

public class PostmanService implements IPostmanService {

	private ImportService importService = ImportService.getInstance();

	@Override
	public Sequence parseRequests(Path path, Sequence sequence)
			throws DronSenderException {
		PostmanModel postmenModel = importService.imports(path,
				PostmanModel.class);

		TransformerFactory.transformEntity(postmenModel, sequence,
				TransformKey.POSTMAN_V2_TO_SEQUENCE);
		return sequence;
	}

	@Override
	public Sequence parseValues(Path path, Sequence sequence)
			throws DronSenderException {
		PostmanValueStructureModel postmanValueStructureModel = importService
				.imports(path, PostmanValueStructureModel.class);

		TransformerFactory.transformEntity(postmanValueStructureModel,
				sequence, TransformKey.POSTMAN_VALUE_V2_TO_SEQUENCE);
		return sequence;
	}
}
