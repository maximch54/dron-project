package com.dron.sender.cache;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.springframework.stereotype.Component;

import com.dron.sender.controllers.root.models.HistoryUiPlugin;

@Component
public class ProjectCache {

	private final ObservableList<HistoryUiPlugin> sentPlugins = FXCollections
			.observableArrayList();

	public ObservableList<HistoryUiPlugin> getSentPlugins() {
		return sentPlugins;
	}

}
