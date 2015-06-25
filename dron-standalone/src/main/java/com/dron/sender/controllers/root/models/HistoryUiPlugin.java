package com.dron.sender.controllers.root.models;

import java.util.ArrayList;
import java.util.List;

import com.dron.sender.pattern.models.transformers.TransformKey;
import com.dron.sender.pattern.services.transformers.TransformerFactory;
import com.dron.sender.sequence.models.Param;
import com.dron.sender.sequence.utils.ParamsUtils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class HistoryUiPlugin {
	private UIPlugin uiPlugin;

	private final ObservableList<UIParam> uiParams = FXCollections
			.observableArrayList();

	public HistoryUiPlugin(UIPlugin uiPlugin, ObservableList<UIParam> uiParams) {
		this.uiPlugin = uiPlugin.clone();
		this.uiParams.clear();
		uiParams.forEach(param -> this.uiParams.add(param.clone()));
	}

	public ObservableList<UIParam> getUiParams() {
		return uiParams;
	}

	public UIPlugin getUiPlugin() {
		return uiPlugin;
	}

	@Override
	public String toString() {
		List<Param> params = new ArrayList<Param>();
		TransformerFactory.reverseTransformEntity(params, uiParams,
				TransformKey.ROOT_PARAMS);

		return ParamsUtils.fillDataParams(uiPlugin.getUrl().get(), params);
	}

}
