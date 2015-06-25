package com.dron.sender.transformers;

import org.springframework.util.Assert;

import com.dron.sender.exim.parsers.postman.v2.models.PostmanToSequenceTransformer;
import com.dron.sender.exim.parsers.postman.v2.models.PostmanValueToSequenceTransformer;
import com.dron.sender.exim.parsers.postman.v2.models.RequestToPluginTransformer;
import com.dron.sender.pattern.interfaces.IBaseTransformer;

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
			case POSTMAN_V2_TO_SEQUENCE:
				return (IBaseTransformer<F, T>) new PostmanToSequenceTransformer();
			case POSTMAN_VALUE_V2_TO_SEQUENCE:
				return (IBaseTransformer<F, T>) new PostmanValueToSequenceTransformer();
			case REQUEST_V2_TO_PLUGIN:
				return (IBaseTransformer<F, T>) new RequestToPluginTransformer();
			default:
				return null;
		}
	}
}
