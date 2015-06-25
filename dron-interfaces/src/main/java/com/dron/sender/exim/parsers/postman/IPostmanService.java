package com.dron.sender.exim.parsers.postman;

import java.nio.file.Path;

import com.dron.sender.exceptions.DronSenderException;
import com.dron.sender.sequence.models.Sequence;

public interface IPostmanService {

	Sequence parseRequests(Path path, Sequence sequence)
			throws DronSenderException;

	Sequence parseValues(Path path, Sequence sequence)
			throws DronSenderException;
}
