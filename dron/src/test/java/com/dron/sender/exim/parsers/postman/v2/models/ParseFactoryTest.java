package com.dron.sender.exim.parsers.postman.v2.models;

import static org.fest.assertions.Assertions.assertThat;

import java.net.URI;

import org.junit.Test;

import com.dron.sender.exim.parsers.ImportFactory;
import com.dron.sender.exim.parsers.ParserType;
import com.dron.sender.sequence.models.Sequence;

public class ParseFactoryTest {

	@Test
	public void parseTest() throws Exception {
		Sequence sequence = ImportFactory.getInstance().importSequence(
				new URI(getClass().getClassLoader()
						.getResource("PostmanModelV2Version.json")
						.toExternalForm()), ParserType.POSTMAN_VALUES);

		assertThat(sequence).describedAs("Sequence can't be null").isNotNull();
	}

	@Test
	public void parseRemoteTest() throws Exception {
		Sequence sequence = ImportFactory
				.getInstance()
				.importSequence(
						new URI(
								"https://www.getpostman.com/collections/78b06675a9c07320f29f"),
						ParserType.POSTMAN_VALUES);

		System.out.println(sequence);
	}
}
