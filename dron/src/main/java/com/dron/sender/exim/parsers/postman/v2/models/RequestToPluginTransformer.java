package com.dron.sender.exim.parsers.postman.v2.models;

import com.dron.sender.pattern.interfaces.IBaseTransformer;
import com.dron.sender.sequence.models.Plugin;

public class RequestToPluginTransformer implements
		IBaseTransformer<RequestModel, Plugin> {

	@Override
	public Plugin transform(RequestModel from, Plugin to) {
		to.setId(from.getId());
		to.setName(from.getName());
		to.setPostBody(from.getData());
		to.setUrl(from.getUrl());
		to.setHttpMethod(from.getMethod());
		return to;
	}

	@Override
	public RequestModel reverseTransform(RequestModel from, Plugin to) {
		from.setId(to.getId());
		from.setName(to.getName());
		from.setData(to.getPostBody());
		from.setUrl(to.getUrl());
		from.setMethod(to.getHttpMethod());
		return from;
	}
}
