package com.dron.sender.exim.parsers.postman.v2.models;

import java.util.ArrayList;

import com.dron.sender.pattern.interfaces.IBaseTransformer;
import com.dron.sender.sequence.models.Param;
import com.dron.sender.sequence.models.Plugin;
import com.dron.sender.sequence.models.Sequence;
import com.dron.sender.transformers.TransformKey;
import com.dron.sender.transformers.TransformerFactory;

public class PostmanToSequenceTransformer implements
		IBaseTransformer<PostmanModel, Sequence> {

	@Override
	public Sequence transform(PostmanModel from, Sequence to) {
		to.setId(from.getId());
		to.setName(from.getName());
		to.setOrder(from.getOrder());
		to.setParams(new ArrayList<Param>());
		to.setPlugins(new ArrayList<Plugin>());
		from.getRequests().forEach(
				request -> {
					Plugin plugin = new Plugin();
					TransformerFactory.transformEntity(request, plugin,
							TransformKey.REQUEST_V2_TO_PLUGIN);
					to.getOrder().add(plugin.getId());
					to.getPlugins().add(plugin);
				});
		return to;
	}

	@Override
	public PostmanModel reverseTransform(PostmanModel from, Sequence to) {
		from.setId(to.getId());
		from.setName(to.getName());
		from.setOrder(to.getOrder());
		from.setRequests(new ArrayList<RequestModel>());
		to.getPlugins().forEach(
				plugin -> {
					RequestModel request = new RequestModel();
					TransformerFactory.reverseTransformEntity(request, plugin,
							TransformKey.REQUEST_V2_TO_PLUGIN);
					from.getRequests().add(request);
				});
		return from;
	}

}
