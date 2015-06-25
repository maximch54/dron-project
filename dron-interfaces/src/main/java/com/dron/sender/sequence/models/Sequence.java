package com.dron.sender.sequence.models;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class Sequence extends BaseNotificationModel {

	public static final String PROPERTY_ID = "ID";

	public static final String PROPERTY_ORDER = "Order";

	public static final String PROPERTY_NAME = "Name";

	public static final String PROPERTY_SELECTED_PLUGIN_ID = "Selected plugin ID";

	public static final String PROPERTY_PARAMS = "Params";

	public static final String PROPERTY_PARAM = "Param";

	private String id;

	private String name;

	private List<String> order;

	private List<Plugin> plugins;

	private final List<Plugin> sentPlugins;

	private List<Param> params;

	private String selectedPluginId;

	public Sequence() {
		order = new ArrayList<String>();
		plugins = new LinkedList<Plugin>();
		sentPlugins = new LinkedList<Plugin>();
		params = new ArrayList<Param>();
	}

	public Plugin findPlugin(String id) {
		return plugins.stream().filter(p -> p.getId().equals(id)).findFirst()
				.orElse(null);
	}

	public String findParam(String key) {
		try {
			return params.stream().filter(p -> p.getKey().equals(key))
					.findFirst().get().getValue();
		} catch (NoSuchElementException e) {
			return null;
		}
	}

	public void updateParam(Param param) {
		params.stream()
				.filter(p -> p.getKey().equals(param.getKey()))
				.findFirst()
				.ifPresent(
						p -> {
							notifyListeners(this, PROPERTY_PARAM, p.getValue(),
									param.getValue());
							p.setValue(param.getValue());
						});
	}

	public void addParam(Param param) {
		notifyListeners(this, PROPERTY_PARAMS, this.params, params.add(param));
	}

	public List<Param> getParams() {
		return params;
	}

	public List<Plugin> getPlugins() {
		return plugins;
	}

	public void setPlugins(List<Plugin> plugins) {
		this.plugins = plugins;
	}

	public void setParams(List<Param> params) {
		notifyListeners(this, PROPERTY_PARAMS, this.params,
				this.params = params);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		notifyListeners(this, PROPERTY_NAME, this.name, this.name = name);
	}

	public String getSelectedPluginId() {
		return selectedPluginId;
	}

	public void setSelectedPluginId(String selectedPluginId) {
		notifyListeners(this, PROPERTY_SELECTED_PLUGIN_ID,
				this.selectedPluginId, this.selectedPluginId = selectedPluginId);
	}

	public List<String> getOrder() {
		return order;
	}

	public void setOrder(List<String> order) {
		notifyListeners(this, PROPERTY_ORDER, this.order, this.order = order);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		notifyListeners(this, PROPERTY_ID, this.id, this.id = id);
	}

	public List<Plugin> getSentPlugins() {
		return sentPlugins;
	}

}