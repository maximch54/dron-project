package com.dron.sender.sequence.utils;

import java.util.List;
import java.util.regex.Pattern;

import nl.flotsam.xeger.Xeger;

import com.dron.sender.sequence.enums.XegerTypes;
import com.dron.sender.sequence.models.Param;

public class ParamsUtils {

	private static final String DEFAULT_INT_REGEX = "[0-9]{1,5}";
	private static final String EMAIL_REGEX = "[a-zA-Z0-9]{5,10}[@][a-zA-Z0-9]{3,8}[.][a-zA-Z]{3}";
	private static final String TIME_REGEX = "([01][1-9]|2[0-3]|00):([0-5][0-9])";
	private static final String DATE_REGEX = "(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[012])/((19|20)[0-9][0-9])";
	private static final String DEFAULT_STRING_REGEX = "[a-zA-Z]{5,10}";
	private static final String PARAM_PREFIX = "{{";
	private static final String PARAM_SUFIX = "}}";
	private static final String RANDOM_VALUE_PREFIX = "{#";
	private static final String RANDOM_VALUE_SUFIX = "#}";

	public static String fillDataParams(String data, List<Param> params) {
		if (data == null)
			return null;

		String param = getFirstParamFromData(data);
		while (param != null) {
			data = data.replace(param, getValue(param, params));
			param = getFirstParamFromData(data);
		}
		data = fillRandomValue(data);
		return data;
	}

	public static String fillRandomValue(String data) {
		String randomValue = getFirstRandomValueFromData(data);
		while (randomValue != null) {
			data = data.replace(randomValue, getRandomValue());
			randomValue = getFirstRandomValueFromData(data);
		}
		return data;
	}

	public static String getRandomValue() {
		return getRandomValue(DEFAULT_STRING_REGEX);
	}

	private static String getRandomValue(String regex) {
		try {
			if (XegerTypes.valueOf(regex) != null) {
				switch (XegerTypes.valueOf(regex)) {
					case DATE:
						regex = DATE_REGEX;
						break;
					case TIME:
						regex = TIME_REGEX;
						break;
					case DATETIME:
						regex = DATE_REGEX + " " + TIME_REGEX;
						break;
					case EMAIL:
						regex = EMAIL_REGEX;
						break;
					case INT:
						regex = DEFAULT_INT_REGEX;
						break;
					case STRING:
						regex = DEFAULT_STRING_REGEX;
						break;
				}
			}
			Pattern.compile(regex);
		} catch (Exception e) {
			regex = DEFAULT_STRING_REGEX;
		}
		Xeger generator = new Xeger(regex);
		return generator.generate();
	}

	private static String getFirstParamFromData(String data) {
		int beginIndex = data.indexOf(PARAM_PREFIX);
		int endIndex = data.indexOf(PARAM_SUFIX) + PARAM_SUFIX.length();
		if (beginIndex >= 0 && endIndex >= 0) {
			return data.substring(beginIndex, endIndex);
		} else {
			return null;
		}
	}

	private static String getFirstRandomValueFromData(String data) {
		int beginIndex = data.indexOf(RANDOM_VALUE_PREFIX);
		int endIndex = data.indexOf(RANDOM_VALUE_SUFIX)
				+ RANDOM_VALUE_SUFIX.length();
		if (beginIndex >= 0 && endIndex >= 0) {
			return data.substring(beginIndex, endIndex);
		} else {
			return null;
		}
	}

	private static String getValue(String key, List<Param> params) {
		for (Param param : params) {
			if (param.getKey().equals(key)) {
				String newValue = fillRandomValue(param.getValue());
				if (!newValue.equals(param.getValue())) {
					param.setValue(newValue);
				}
				return newValue;
			}
		}
		return null;
	}
}