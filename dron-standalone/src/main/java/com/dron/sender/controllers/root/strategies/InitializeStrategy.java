package com.dron.sender.controllers.root.strategies;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.SelectionMode;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import com.dron.sender.config.AppProperties;
import com.dron.sender.controllers.base.interfaces.IBaseController;
import com.dron.sender.controllers.root.RootController;
import com.dron.sender.controllers.root.controls.AutoFillSequenceHelper;
import com.dron.sender.controllers.root.controls.ParamTableView;
import com.dron.sender.controllers.root.controls.RootConfig;
import com.dron.sender.controllers.root.models.BaseRootController;
import com.dron.sender.controllers.root.models.HistoryUiPlugin;
import com.dron.sender.pattern.interfaces.IControllerStrategy;
import com.dron.sender.pattern.models.strategy.ControllerActionStrategy;
import com.dron.sender.pattern.services.strategies.ControllerStrategyContext;

@Component
public class InitializeStrategy extends BaseRootController implements
		IControllerStrategy {

	@Resource
	private ApplicationContext ctx;

	@Autowired
	private ControllerStrategyContext context;

	@Autowired
	private AppProperties appProperties;

	@Override
	public ControllerActionStrategy getStrategy() {
		return ControllerActionStrategy.ROOT_INITIALIZE;
	}

	@Override
	public void execute(IBaseController iBaseController) {
		RootController controller = (RootController) iBaseController;
		setUp(controller);

		uiSequence.setUpEmptyForm();

		tfNewPluginName.textProperty().bindBidirectional(uiSequence.getName());

		btnSendSequence.disableProperty().bind(
				RootConfig.getDisableRootProperty());
		btnSendSequence.defaultButtonProperty().bind(
				btnSendSequence.focusedProperty());
		btnSendActivePlugin.disableProperty().bind(
				RootConfig.getDisableRootProperty());
		btnSendActivePlugin.defaultButtonProperty().bind(
				btnSendActivePlugin.focusedProperty());

		tfUrl.disableProperty().bind(RootConfig.getDisableRootProperty());
		tfUrl.textProperty().addListener((observer, oldValue, newValue) -> {
			uiSequence.getSelectedUIPLugin().setUrl(newValue);
		});

		cbMethods.disableProperty().bind(RootConfig.getDisableRootProperty());
		cbMethods.getItems().addAll(HttpMethod.GET.name(),
				HttpMethod.POST.name(), HttpMethod.PUT.name(),
				HttpMethod.DELETE.name());
		cbMethods.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> {
					cbMethods.getSelectionModel().select(newValue);
					uiSequence.getSelectedUIPLugin().setMethod(newValue);

					RootConfig.bindPostBody(txaPostBody, newValue, spHeaders, tblHeaders);
				});

		tblParams = new ParamTableView()
				.initialize(
						uiSequence.getUIParams(),
						tblParams,
						onKey -> {
							uiSequence.getKeys().clear();
							onKey.forEach(key -> uiSequence.getKeys().add(
									key.getKey()));
						});
		tblParams
				.editableProperty()
				.addListener(
						(observer, oldValue, newValue) -> {
							context.execute(
									controller,
									ControllerActionStrategy.ROOT_FILL_UI_PLUGIN_ACCORDION);
						});

		txaPostBody.managedProperty().bind(txaPostBody.visibleProperty());
		txaPostBody.disableProperty().bind(RootConfig.getDisableRootProperty());
		txaPostBody.textProperty().addListener(
				(observer, oldValue, newValue) -> {
					uiSequence.getSelectedUIPLugin().setPostBody(newValue);
				});
		btnAddNewPlugin.disableProperty().bind(
				tfNewPluginName.textProperty().isEmpty());

		tbtnHeaders.setSelected(true);
		tbtnHeaders.disableProperty().bind(RootConfig.getDisableRootProperty());
		tbtnHeaders.setOnAction(event -> {
			tblHeaders.setVisible(!tblHeaders.isVisible());
			RootConfig.bindPostBody(txaPostBody, cbMethods.getSelectionModel()
					.getSelectedItem(), spHeaders, tblHeaders);
		});

		tbtnParams.setSelected(true);
		tbtnParams.setOnAction(event -> {
			tblParams.setVisible(!tblParams.isVisible());
			spParams.setDividerPositions(tblParams.isVisible() ? 0.4 : 0.0);
		});

		tblHeaders.disableProperty().bind(RootConfig.getDisableRootProperty());

		autoFillSequenceTextBox.setUp(ctx, controller);
		autoFillSequenceTextBox.setItems(AutoFillSequenceHelper
				.getFiles(appProperties.getFilePath()));

		lvHistory.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		lvHistory.getSelectionModel().selectedItemProperty()
				.addListener(new ChangeListener<HistoryUiPlugin>() {
					@Override
					public void changed(
							ObservableValue<? extends HistoryUiPlugin> observable,
							HistoryUiPlugin oldValue, HistoryUiPlugin newValue) {
						if (newValue != null) {
							controller.setHistoryUiPlugin(newValue);
							context.execute(
									controller,
									ControllerActionStrategy.ROOT_FILL_ROOT_CONTROLS);
						}
					}
				});

		context.execute(controller,
				ControllerActionStrategy.ROOT_NEW_UI_SEQUENCE);

		context.execute(controller,
				ControllerActionStrategy.ROOT_FILL_ROOT_CONTROLS);
	}
}
