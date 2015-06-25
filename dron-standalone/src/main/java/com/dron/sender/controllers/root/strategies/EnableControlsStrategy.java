package com.dron.sender.controllers.root.strategies;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dron.sender.cache.ProjectCache;
import com.dron.sender.controllers.base.interfaces.IBaseController;
import com.dron.sender.controllers.root.RootController;
import com.dron.sender.controllers.root.controls.RootConfig;
import com.dron.sender.controllers.root.models.BaseRootController;
import com.dron.sender.controllers.root.models.HistoryUiPlugin;
import com.dron.sender.controllers.root.models.UIParam;
import com.dron.sender.pattern.interfaces.IControllerStrategy;
import com.dron.sender.pattern.models.strategy.ControllerActionStrategy;

@Component
public class EnableControlsStrategy extends BaseRootController implements
		IControllerStrategy {

	@Autowired
	private ProjectCache cache;

	@Override
	public ControllerActionStrategy getStrategy() {
		return ControllerActionStrategy.ROOT_ENABLE_CONTROLS;
	}

	@Override
	public void execute(IBaseController iBaseController) {
		final RootController controller = (RootController) iBaseController;
		setUp(controller);

		RootConfig.setDisableRootProperty(false);
		autoFillSequenceTextBox.disableProperty().set(false);
		tblParams.disableProperty().set(false);
		tbtnParams.disableProperty().set(false);
		accPlugins.disableProperty().set(false);
		tfNewPluginName.disableProperty().set(false);
		btnStopSendingSequence.setVisible(false);
		btnSendSequence.requestFocus();

		// Add sent plugins to the history and update history list
		ObservableList<UIParam> params = FXCollections.observableArrayList();
		uiSequence.getUIParams().forEach(param -> params.add(param.clone()));
		uiSequence.getSentPlugins().forEach(
				plugin -> cache.getSentPlugins().add(
						new HistoryUiPlugin(plugin, params)));
		ObservableList<HistoryUiPlugin> items = FXCollections
				.observableArrayList();

		cache.getSentPlugins().forEach(plugin -> items.add(plugin));
		lvHistory.getItems().clear();
		FXCollections.reverse(items);
		lvHistory.setItems(items);
	}
}
