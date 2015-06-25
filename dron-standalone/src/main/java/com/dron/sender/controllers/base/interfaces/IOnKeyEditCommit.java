package com.dron.sender.controllers.base.interfaces;

import javafx.collections.ObservableList;

public interface IOnKeyEditCommit<T> {
	void onKeyEditCommit(final ObservableList<T> observableList);
}
