package com.dron.sender.controllers.root.models.transformers;

import org.springframework.http.HttpMethod;

import com.dron.sender.controllers.root.models.UIPlugin;
import com.dron.sender.pattern.interfaces.IBaseTransformer;
import com.dron.sender.pattern.models.transformers.TransformKey;
import com.dron.sender.pattern.services.transformers.TransformerFactory;
import com.dron.sender.sequence.models.Plugin;

public class PluginTransformer implements IBaseTransformer<Plugin, UIPlugin> {

	@Override
	public UIPlugin transform(Plugin plugin, UIPlugin uiPlugin) {
		uiPlugin.setId(plugin.getId());
		uiPlugin.setName(plugin.getName());
		uiPlugin.setResponce(plugin.getResponce());
		uiPlugin.setUrl(plugin.getUrl());
		uiPlugin.setPostBody(plugin.getPostBody());
		uiPlugin.setSuccess(plugin.isSuccess());
		uiPlugin.setMethod(plugin.getHttpMethod().name());

		TransformerFactory.transformEntity(plugin.getHeaders(),
				uiPlugin.getHeadersList(), TransformKey.ROOT_HTTP_HEADERS);

		TransformerFactory.transformEntity(plugin.getFutureParams(),
				uiPlugin.getFutureParams(), TransformKey.ROOT_FUTURE_PARAMS);
		return uiPlugin;
	}

	@Override
	public Plugin reverseTransform(Plugin plugin, UIPlugin uiPlugin) {
		plugin.setId(uiPlugin.getId().get());
		plugin.setName(uiPlugin.getName().get());
		plugin.setResponce(uiPlugin.getResponce().get());
		plugin.setUrl(uiPlugin.getUrl().get());
		plugin.setPostBody(uiPlugin.getPostBody().get());
		plugin.setSuccess(uiPlugin.isSuccess().get());
		plugin.setHttpMethod(HttpMethod.valueOf(uiPlugin.getMethod().get()));
		TransformerFactory.reverseTransformEntity(plugin.getHeaders(),
				uiPlugin.getHeadersList(), TransformKey.ROOT_HTTP_HEADERS);

		TransformerFactory.reverseTransformEntity(plugin.getFutureParams(),
				uiPlugin.getFutureParams(), TransformKey.ROOT_FUTURE_PARAMS);

		return plugin;
	}
}
