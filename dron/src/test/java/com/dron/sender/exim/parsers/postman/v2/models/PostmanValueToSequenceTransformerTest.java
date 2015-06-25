package com.dron.sender.exim.parsers.postman.v2.models;

import static org.fest.assertions.Assertions.assertThat;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.dron.sender.sequence.models.Param;
import com.dron.sender.sequence.models.Sequence;
import com.dron.sender.transformers.TransformKey;
import com.dron.sender.transformers.TransformerFactory;

public class PostmanValueToSequenceTransformerTest {

	private static final String NAME = "Name";
	private static final String ID = "ID";
	private static final String VALUE1 = "Value1";
	private static final String VALUE2 = "Value2";
	private static final String KEY1 = "Key1";
	private static final String KEY2 = "Key2";
	private final PostmanValueStructureModel valueStructureModel = new PostmanValueStructureModel();
	private final List<Param> params = new ArrayList<Param>();
	private final List<PostmanValueModel> values = new ArrayList<PostmanValueModel>();

	@Before
	public void setUp() {
		Param param1 = new Param(KEY1, VALUE1);
		Param param2 = new Param(KEY2, VALUE2);
		params.add(param1);
		params.add(param2);

		PostmanValueModel postmanValueModel1 = new PostmanValueModel(KEY1,
				VALUE1);
		PostmanValueModel postmanValueModel2 = new PostmanValueModel(KEY2,
				VALUE2);
		values.add(postmanValueModel1);
		values.add(postmanValueModel2);

		valueStructureModel.setId(ID);
		valueStructureModel.setName(NAME);
		valueStructureModel.setValues(values);
		valueStructureModel.setTimestamp(new Timestamp(new Date().getTime()));
	}

	@Test
	public void transform() {
		Sequence sequence = new Sequence();
		TransformerFactory.transformEntity(valueStructureModel, sequence,
				TransformKey.POSTMAN_VALUE_V2_TO_SEQUENCE);

		valueStructureModel
				.getValues()
				.forEach(
						value -> {
							assertThat(
									sequence.getParams()
											.stream()
											.filter(p -> p.getKey().equals(
													value.getKey()))
											.filter(p -> p.getValue().equals(
													value.getValue())).count())
									.as("Transform postman values should be equals to sequence params")
									.isEqualTo(1);
						});
	}

	@Test
	public void reverseTransform() {
		Sequence sequence = new Sequence();
		sequence.setParams(params);

		PostmanValueStructureModel postmanValueStructureModel = new PostmanValueStructureModel();
		TransformerFactory.reverseTransformEntity(postmanValueStructureModel,
				sequence, TransformKey.POSTMAN_VALUE_V2_TO_SEQUENCE);

		sequence.getParams()
				.forEach(
						param -> {
							assertThat(
									postmanValueStructureModel
											.getValues()
											.stream()
											.filter(v -> v.getKey().equals(
													param.getKey()))
											.filter(v -> v.getValue().equals(
													param.getValue())).count())
									.as("Sequence params values should be equals to Transform postman")
									.isEqualTo(1);
						});
	}
}
