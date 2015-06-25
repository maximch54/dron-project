package com.dron.sender;

import java.io.IOException;

import com.dron.sender.exceptions.DronSenderException;
import com.dron.sender.exim.ImportService;
import com.dron.sender.sequence.models.Sequence;
import com.dron.sender.sequence.services.SequenceRunner;
import com.fasterxml.jackson.core.JsonProcessingException;

public class Application {

	/**
	 * @param args
	 * @throws IOException
	 * @throws JsonProcessingException
	 * @throws DronSenderException
	 * @throws PayPalRESTException
	 */
	public static void main(String[] args) throws JsonProcessingException,
			IOException, DronSenderException {

		Sequence sequence = ImportService.getInstance().imports(
				"/Users/admin/Documents/json/CreateUser.json", Sequence.class);
		SequenceRunner sequenceRunner = new SequenceRunner(sequence);

		sequenceRunner.runSequence();
	}
}
