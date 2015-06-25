package com.dron.sender.exim.parsers.postman.v2.models;

import static org.fest.assertions.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpMethod;

import com.dron.sender.sequence.models.Plugin;
import com.dron.sender.sequence.models.Sequence;
import com.dron.sender.transformers.TransformKey;
import com.dron.sender.transformers.TransformerFactory;

public class PostmanToSequenceTransformerTest {

	private static final String NAME = "Name";
	private static final String TEST_DATA_URL = "Url";
	private static final String URL = TEST_DATA_URL;
	private static final String POST_DATA = "Post data";
	private static final String TEST_REQUEST_ID = "Test request id";
	private static final String TEST_REQUEST_NAME = "Test request name";
	private static final String TEST_POST_DATA = "Test post data";
	private static final String TEST_NAME = "Test name";
	private static final String ID = "12345";
	private final List<String> orders = new ArrayList<String>();
	private final List<RequestModel> requestModels = new ArrayList<RequestModel>();
	private final List<Plugin> plugins = new ArrayList<Plugin>();

	@Before
	public void setUp() {
		orders.add("Test");
		orders.add("Test1");

		Plugin plugin = new Plugin();
		plugin.setId(ID);
		plugin.setUrl(URL);
		plugin.setName(NAME);
		plugin.setPostBody(POST_DATA);
		plugin.setHttpMethod(HttpMethod.POST);
		plugins.add(plugin);

		RequestModel requestModel = new RequestModel();
		requestModel.setId(TEST_REQUEST_ID);
		requestModel.setName(TEST_REQUEST_NAME);
		requestModel.setData(TEST_POST_DATA);
		requestModel.setUrl(TEST_DATA_URL);
		requestModel.setMethod(HttpMethod.POST);
		requestModels.add(requestModel);
	}

	@Test
	public void transform() {
		PostmanModel postmanModel = new PostmanModel();
		postmanModel.setId(ID);
		postmanModel.setName(TEST_NAME);
		postmanModel.setOrder(orders);
		postmanModel.setRequests(requestModels);

		Sequence sequence = new Sequence();
		TransformerFactory.transformEntity(postmanModel, sequence,
				TransformKey.POSTMAN_V2_TO_SEQUENCE);

		assertThat(postmanModel.getId()).as(
				"Postman id should be equals to Sequence id").isEqualTo(
				sequence.getId());
		assertThat(postmanModel.getName()).as(
				"Postman name should be equals to Sequence name").isEqualTo(
				sequence.getName());
		assertThat(postmanModel.getOrder()).as(
				"Postman orders should be equals to Sequence orders")
				.isEqualTo(sequence.getOrder());

		postmanModel
				.getRequests()
				.forEach(
						request -> {
							assertThat(
									sequence.getPlugins()
											.stream()
											.filter(p -> p.getId().equals(
													request.getId()))
											.filter(p -> p.getName().equals(
													request.getName()))
											.filter(p -> p.getPostBody()
													.equals(request.getData()))
											.filter(p -> p.getUrl().equals(
													request.getUrl()))
											.filter(p -> p
													.getHttpMethod()
													.equals(request.getMethod()))
											.count())
									.as("Transform postman values should be equals to sequence params")
									.isEqualTo(1);
						});
	}

	@Test
	public void reverseTransform() {
		Sequence sequence = new Sequence();
		sequence.setId(ID);
		sequence.setName(TEST_NAME);
		sequence.setOrder(orders);
		sequence.setPlugins(plugins);

		PostmanModel postmanModel = new PostmanModel();
		TransformerFactory.reverseTransformEntity(postmanModel, sequence,
				TransformKey.POSTMAN_V2_TO_SEQUENCE);

		assertThat(sequence.getId()).as(
				"Postman id should be equals to Sequence id").isEqualTo(
				postmanModel.getId());
		assertThat(sequence.getName()).as(
				"Postman name should be equals to Sequence name").isEqualTo(
				postmanModel.getName());
		assertThat(sequence.getOrder()).as(
				"Postman orders should be equals to Sequence orders")
				.isEqualTo(postmanModel.getOrder());

		sequence.getPlugins()
				.forEach(
						plugin -> {
							assertThat(
									postmanModel
											.getRequests()
											.stream()
											.filter(r -> r.getId().equals(
													plugin.getId()))
											.filter(r -> r.getName().equals(
													plugin.getName()))
											.filter(r -> r.getData().equals(
													plugin.getPostBody()))
											.filter(r -> r.getUrl().equals(
													plugin.getUrl()))
											.filter(r -> r.getMethod().equals(
													plugin.getHttpMethod()))
											.count())
									.as("Transform postman values should be equals to sequence params")
									.isEqualTo(1);
						});
	}
}
