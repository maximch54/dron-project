package com.dron.sender.controllers.root.models.transformers;

import java.util.List;

import javafx.collections.ObservableList;

import com.dron.sender.controllers.root.models.UIParam;
import com.dron.sender.pattern.interfaces.IBaseTransformer;
import com.dron.sender.sequence.models.Param;

public class ParamTransformer implements
		IBaseTransformer<List<Param>, ObservableList<UIParam>> {

	@Override
	public ObservableList<UIParam> transform(final List<Param> params,
			final ObservableList<UIParam> uiParams) {
		uiParams.clear();
		params.forEach(param -> {
			uiParams.add(new UIParam(param));
		});
		uiParams.add(new UIParam());
		return uiParams;
	}

	@Override
	public List<Param> reverseTransform(final List<Param> params,
			final ObservableList<UIParam> uiParams) {
		params.clear();
		uiParams.forEach(uiParam -> {
			if (!uiParam.isEmpty()) {
				params.add(new Param(uiParam.getKey(), uiParam.getValue()));
			}
		});
		return params;
	}

}
