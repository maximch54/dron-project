package com.dron.sender.exim.parsers.postman.v2.models;

import java.util.ArrayList;
import java.util.List;

import com.dron.sender.pattern.interfaces.IBaseTransformer;
import com.dron.sender.sequence.models.Param;
import com.dron.sender.sequence.models.Sequence;

public class PostmanValueToSequenceTransformer implements
		IBaseTransformer<PostmanValueStructureModel, Sequence> {

	@Override
	public Sequence transform(PostmanValueStructureModel from, Sequence to) {
		List<Param> params = new ArrayList<Param>(from.getValues().size());

		from.getValues().forEach((value) -> {
			Param param = new Param(value.getKey(), value.getValue());
			params.add(param);
		});
		to.setParams(params);
		return to;
	}

	@Override
	public PostmanValueStructureModel reverseTransform(
			PostmanValueStructureModel from, Sequence to) {
		List<PostmanValueModel> values = new ArrayList<PostmanValueModel>(to
				.getParams().size());
		to.getParams().forEach(
				(param) -> {
					PostmanValueModel model = new PostmanValueModel(param
							.getKey(), param.getValue());
					values.add(model);
				});
		from.setValues(values);
		return from;
	}

}
