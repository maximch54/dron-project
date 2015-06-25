package com.dron.sender.controllers.root.models;

import java.util.stream.IntStream;

import com.dron.sender.controllers.root.RootController;
import com.dron.sender.pattern.models.strategy.ControllerActionStrategy;
import com.dron.sender.pattern.services.strategies.ControllerStrategyContext;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UISequence {

	private final StringProperty name = new SimpleStringProperty();

	private final ObservableList<String> order = FXCollections
			.observableArrayList();

	private final ObservableList<UIParam> uiParams = FXCollections
			.observableArrayList();

	private final ObservableList<UIPlugin> uiPlugins = FXCollections
			.observableArrayList();

	private final ObservableList<UIPlugin> sentPlugins = FXCollections
			.observableArrayList();

	private final ObservableList<String> keys = FXCollections
			.observableArrayList();

	private String selectedUIPLuginID;

	public void setUpEmptyForm() {
		clear();
		prepareEmptySequence();
	}

	private void prepareEmptySequence() {
		uiParams.add(new UIParam());
		fillKeys();
		UIPlugin uiPlugin = new UIPlugin();
		uiPlugins.add(uiPlugin);
		order.add(uiPlugin.getId().get());
		selectedUIPLuginID = uiPlugin.getId().get();
	}

	public void clear() {
		uiParams.clear();
		sentPlugins.clear();
		fillKeys();
		uiPlugins.clear();
		order.clear();
		name.set("");
		selectedUIPLuginID = null;
	}

	private void fillKeys() {
		uiParams.forEach(param -> keys.add(param.getKey()));
	}

	public UIPlugin findPlugin(String id) {
		return uiPlugins.stream().filter(p -> p.getId().get().equals(id))
				.findFirst().get();
	}

	public void addPlugin(UIPlugin uiPlugin) {
		if (uiPlugin != null) {
			uiPlugins.add(uiPlugin);
			order.add(uiPlugin.getId().get());
		}
	}

	public void removePlugin(int index) {
		UIPlugin removePlugin = findPlugin(order.get(index));
		uiPlugins.remove(removePlugin);
		order.remove(index);

		// If order is empty we can clear all data and start at the beginning,
		// other way we jest update selectedUIPluginID with the next id
		if (order.isEmpty()) {
			setUpEmptyForm();
		} else {
			if (order.size() <= index) {
				index--;
			}

			selectedUIPLuginID = order.get(index);
		}
	}

	public void movePluginDown(String id) {
		int index = findSelectedIndex(id);
		if (index != order.size() - 1) {
			String tmpOrder = order.get(index);
			order.set(index, order.get(index + 1));
			order.set(index + 1, tmpOrder);
		}
	}

	public void movePluginUp(String id) {
		int index = findSelectedIndex(id);
		if (index != 0) {
			String tmpOrder = order.get(index);
			order.set(index, order.get(index - 1));
			order.set(index - 1, tmpOrder);
		}
	}

	public void selectedUIPLugin(ControllerStrategyContext context,
			RootController controller) {
		selectedUIPLugin(selectedUIPLuginID, context, controller);
	}

	public void selectedUIPLugin(String selectedUIPLugin,
			ControllerStrategyContext context, RootController controller) {
		if (selectedUIPLugin == null) {
			selectedUIPLugin = order.get(0);
		}
		this.selectedUIPLuginID = selectedUIPLugin;

		controller.setHistoryUiPlugin(new HistoryUiPlugin(
				getSelectedUIPLugin(), uiParams));

		context.execute(controller,
				ControllerActionStrategy.ROOT_FILL_ROOT_CONTROLS);
		controller.getAccPlugins().setExpandedPane(
				controller.getAccPlugins().getPanes()
						.get(findSelectedIndex(selectedUIPLugin)));
	}

	public int findSelectedIndex(String id) {
		return IntStream.range(0, order.size())
				.filter(i -> order.get(i).equals(id)).findFirst().getAsInt();
	}

	public ObservableList<UIParam> getUIParams() {
		return uiParams;
	}

	public ObservableList<UIPlugin> getUiPlugins() {
		return uiPlugins;
	}

	public ObservableList<String> getOrder() {
		return order;
	}

	public StringProperty getName() {
		return name;
	}

	public UIPlugin getSelectedUIPLugin() {
		return uiPlugins.stream()
				.filter(p -> p.getId().get().equals(selectedUIPLuginID))
				.findFirst().orElse(null);
	}

	public void setSelectedPluginId(String selectedPluginID) {
		this.selectedUIPLuginID = selectedPluginID;
	}

	public String getSelectedPluginId() {
		return selectedUIPLuginID;
	}

	public ObservableList<String> getKeys() {
		return keys;
	}

	public ObservableList<UIPlugin> getSentPlugins() {
		return sentPlugins;
	}

}
