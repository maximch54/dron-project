package com.dron.sender.controllers.root.models;

import com.dron.sender.controllers.base.interfaces.IModelTableView;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UIHttpHeaders implements IModelTableView, Cloneable {

	public static final String PROPERTY_VALUE = "Value";
	public static final String PROPERTY_HEADER = "Header";

	public UIHttpHeaders() {
		this("", "");
	}

	public UIHttpHeaders(String header, String value) {
		headerProperty(header);
		valueProperty(value);
	}

	private StringProperty header;

	private StringProperty value;

	@Override
	public String getModelKey() {
		return getHeader();
	}

	@Override
	public void updateModelKey(String key) {
		setHeader(key);
	}

	@Override
	public String getModelValue() {
		return getValue();
	}

	@Override
	public void updateModelValue(String value) {
		setValue(value);
	}

	public StringProperty headerProperty() {
		return headerProperty(null);
	}

	public StringProperty headerProperty(String initialValue) {
		if (this.header == null) {
			this.header = new SimpleStringProperty(this, PROPERTY_HEADER,
					initialValue);
		}
		return this.header;
	}

	public StringProperty valueProperty() {
		return valueProperty(null);
	}

	public StringProperty valueProperty(String initialValue) {
		if (this.value == null) {
			this.value = new SimpleStringProperty(this, PROPERTY_VALUE,
					initialValue);
		}
		return this.value;
	}

	public String getHeader() {
		return header.get();
	}

	public void setHeader(String header) {
		this.header.set(header);
	}

	public String getValue() {
		return value.get();
	}

	public void setValue(String value) {
		this.value.set(value);
	}

	@Override
	public UIHttpHeaders clone() {
		return new UIHttpHeaders(this.header.get(), this.value.get());
	}
}
