package com.dron.sender.pattern.services.transformers;

import org.springframework.util.Assert;

import com.dron.sender.controllers.root.models.transformers.FillPluginFutureParamsTransformer;
import com.dron.sender.controllers.root.models.transformers.FutureParamTransformer;
import com.dron.sender.controllers.root.models.transformers.HttpHeaderTransformer;
import com.dron.sender.controllers.root.models.transformers.ParamTransformer;
import com.dron.sender.controllers.root.models.transformers.PluginTransformer;
import com.dron.sender.controllers.root.models.transformers.SequenceTransformer;
import com.dron.sender.pattern.interfaces.IBaseTransformer;
import com.dron.sender.pattern.models.transformers.TransformKey;

public class TransformerFactory {

	public static <F, T> T transformEntity(F from, Class<T> toClazz,
			TransformKey key) {
		try {
			T to = toClazz.newInstance();
			return transformEntity(from, to, key);
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static <T, F> T transformEntity(F from, T to, TransformKey key) {
		Assert.notNull(from,
				"Transfromation is wrong as transformed from object is null.");
		Assert.notNull(to,
				"Transformation is wrong as transformed to object is null.");
		IBaseTransformer<F, T> transformer = getTransformer(from, to, key);
		return transformer.transform(from, to);
	}

	public static <F, T> F reverseTransformEntity(F from, Class<T> toClazz,
			TransformKey key) {
		try {
			T to = toClazz.newInstance();
			return reverseTransformEntity(from, to, key);
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static <T, F> F reverseTransformEntity(F from, T to, TransformKey key) {
		Assert.notNull(from,
				"Transfromation is wrong as transformed from object is null.");
		Assert.notNull(to,
				"Transformation is wrong as transformed to object is null.");
		IBaseTransformer<F, T> transformer = getTransformer(from, to, key);
		return transformer.reverseTransform(from, to);
	}

	@SuppressWarnings({ "unchecked" })
	private static <F, T> IBaseTransformer<F, T> getTransformer(F from, T to,
			TransformKey key) {
		switch (key) {
		case ROOT_HTTP_HEADERS:
			return (IBaseTransformer<F, T>) new HttpHeaderTransformer();
		case ROOT_PARAMS:
			return (IBaseTransformer<F, T>) new ParamTransformer();
		case ROOT_FUTURE_PARAMS:
			return (IBaseTransformer<F, T>) new FutureParamTransformer();
		case ROOT_PLUGIN:
			return (IBaseTransformer<F, T>) new PluginTransformer();
		case ROOT_SEQUENCE:
			return (IBaseTransformer<F, T>) new SequenceTransformer();
		case ROOT_FILL_PLUGIN_FUTURE_PARAMS:
			return (IBaseTransformer<F, T>) new FillPluginFutureParamsTransformer();
		default:
			return null;
		}
	}
}
