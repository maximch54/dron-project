package com.dron.sender.controllers.root.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import com.dron.sender.controllers.base.interfaces.IModelTableView;
import com.dron.sender.sequence.models.FutureParam;

public class UIFutureParam implements IModelTableView, Cloneable {

	public UIFutureParam() {
		this("", "");
	}

	public UIFutureParam(FutureParam futureParam) {
		keyProperty(futureParam.getKey());
		dependenceProperty(futureParam.getDependence());
	}

	public UIFutureParam(String key, String dependence) {
		keyProperty(key);
		dependenceProperty(dependence);
	}

	private StringProperty key;

	private StringProperty dependence;

	@Override
	public String getModelKey() {
		return getKey();
	}

	@Override
	public void updateModelKey(String key) {
		setKey(key);
	}

	@Override
	public String getModelValue() {
		return getDependence();
	}

	@Override
	public void updateModelValue(String value) {
		setDependence(value);
	}

	public StringProperty keyProperty() {
		return keyProperty(null);
	}

	public StringProperty keyProperty(String keyValue) {
		if (this.key == null) {
			this.key = new SimpleStringProperty(this, FutureParam.PROPERTY_KEY,
					keyValue);
		}
		return this.key;
	}

	public StringProperty dependenceProperty() {
		return dependenceProperty(null);
	}

	public StringProperty dependenceProperty(String dependenceValue) {
		if (this.dependence == null) {
			this.dependence = new SimpleStringProperty(this,
					FutureParam.PROPERTY_DEPENDENCE, dependenceValue);
		}
		return this.dependence;
	}

	public String getKey() {
		return key.get();
	}

	public void setKey(String key) {
		this.key.set(key);
	}

	public String getDependence() {
		return dependence.get();
	}

	public void setDependence(String dependence) {
		this.dependence.set(dependence);
	}

	@Override
	public UIFutureParam clone() {
		return new UIFutureParam(this.getKey(), this.getDependence());
	}

}
