package com.dron.sender.pattern.interfaces;

public interface IBaseTransformer<F, T> {
	T transform(F from, T to);

	F reverseTransform(F from, T to);
}
