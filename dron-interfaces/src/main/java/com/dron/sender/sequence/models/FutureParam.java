package com.dron.sender.sequence.models;

import java.util.ArrayList;
import java.util.List;

public class FutureParam extends BaseNotificationModel implements Cloneable {

	public static final String PROPERTY_KEY = "Key";

	public static final String PROPERTY_DEPENDENCE = "Dependence";

	public static final String PROPERTY_RULES = "Rules";

	public FutureParam() {
		rules = new ArrayList<Rule>();
	}

	public FutureParam(final String key, final String dependence) {
		this();
		this.key = key;
		this.dependence = dependence;
	}

	private String key;

	private String dependence;

	private List<Rule> rules;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		notifyListeners(this, PROPERTY_KEY, this.key, this.key = key);
	}

	public String getDependence() {
		return dependence;
	}

	public void setDependence(String dependence) {
		notifyListeners(this, PROPERTY_DEPENDENCE, dependence,
				this.dependence = dependence);
	}

	public List<Rule> getRules() {
		return rules;
	}

	public void setRules(List<Rule> rules) {
		notifyListeners(this, PROPERTY_RULES, rules, this.rules = rules);
	}

	@Override
	protected FutureParam clone() throws CloneNotSupportedException {
		FutureParam futureParam = new FutureParam();
		futureParam.setKey(key);
		futureParam.setDependence(dependence);
		rules.forEach(rule -> futureParam.getRules().add(rule));
		return futureParam;
	}
}