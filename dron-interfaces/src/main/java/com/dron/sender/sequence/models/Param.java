package com.dron.sender.sequence.models;


public class Param extends BaseNotificationModel {

	public static final String PROPERTY_ARRAY = "Array";

	public static final String PROPERTY_VALUE = "Value";

	public static final String PROPERTY_KEY = "Key";

	public Param() {
	}

	public Param(final String key, final String value) {
		this(key, value, false);
	}

	public Param(final String key, final String value, final boolean array) {
		this.key = key;
		this.value = value;
		this.array = array;
	}

	private String key;

	private String value;

	private boolean array;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		notifyListeners(this, PROPERTY_KEY, this.key, this.key = key);
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		// If we don't fill it before just create array with one element int the
		// list
		String newValue;
		if (array) {
			if (this.value == null) {
				newValue = "[" + value + "]";
			} else {
				newValue = this.value.substring(0, this.value.length() - 1)
						+ ", " + value + "]";
			}
		} else {
			newValue = value;
		}
		notifyListeners(this, PROPERTY_VALUE, this.value, this.value = newValue);
	}

	public boolean isArray() {
		return array;
	}

	public void setArray(boolean array) {
		notifyListeners(this, PROPERTY_ARRAY, this.array, this.array = array);
	}
}