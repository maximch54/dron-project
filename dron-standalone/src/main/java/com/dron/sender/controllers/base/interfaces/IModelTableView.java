package com.dron.sender.controllers.base.interfaces;

import org.apache.commons.lang3.StringUtils;

public interface IModelTableView {

	String getModelKey();

	String getModelValue();

	void updateModelKey(String key);

	void updateModelValue(String value);

	default boolean isEmpty() {
		return StringUtils.isBlank(getModelKey())
				&& StringUtils.isBlank(getModelValue());
	};
}
