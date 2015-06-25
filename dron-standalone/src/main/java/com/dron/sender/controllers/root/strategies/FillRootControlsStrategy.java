package com.dron.sender.controllers.root.strategies;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dron.sender.controllers.base.interfaces.IBaseController;
import com.dron.sender.controllers.root.RootController;
import com.dron.sender.controllers.root.controls.HeaderTableView;
import com.dron.sender.controllers.root.controls.ParamTableView;
import com.dron.sender.controllers.root.controls.RootConfig;
import com.dron.sender.controllers.root.models.BaseRootController;
import com.dron.sender.pattern.interfaces.IControllerStrategy;
import com.dron.sender.pattern.models.strategy.ControllerActionStrategy;
import com.dron.sender.pattern.services.strategies.ControllerStrategyContext;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class FillRootControlsStrategy extends BaseRootController implements
		IControllerStrategy {

	@Autowired
	private ControllerStrategyContext context;

	private ObjectMapper mapper = new ObjectMapper();

	@Override
	public ControllerActionStrategy getStrategy() {
		return ControllerActionStrategy.ROOT_FILL_ROOT_CONTROLS;
	}

	@Override
	public void execute(IBaseController iBaseController) {
		final RootController controller = (RootController) iBaseController;
		setUp(controller);

		tfUrl.setText(controller.getHistoryUiPlugin().getUiPlugin().getUrl()
				.get());

		cbMethods.getSelectionModel()
				.select(controller.getHistoryUiPlugin().getUiPlugin()
						.getMethod().get());

		Object json;
		try {
			if (controller.getHistoryUiPlugin().getUiPlugin().getPostBody()
					.get() != null) {
				json = mapper.readValue(controller.getHistoryUiPlugin()
						.getUiPlugin().getPostBody().get(), Object.class);
				txaPostBody.setText(mapper.writerWithDefaultPrettyPrinter()
						.writeValueAsString(json));
			} else {
				txaPostBody.setText("");
			}
		} catch (IOException e) {
			txaPostBody.setText(controller.getHistoryUiPlugin().getUiPlugin()
					.getPostBody().get());
		}

		try {
			if (controller.getHistoryUiPlugin().getUiPlugin().getResponce()
					.get() != null) {
				json = mapper.readValue(controller.getHistoryUiPlugin()
						.getUiPlugin().getResponce().get(), Object.class);
				txaResponce.setText(mapper.writerWithDefaultPrettyPrinter()
						.writeValueAsString(json));
			} else {
				txaResponce.setText("");
			}
		} catch (IOException e) {
			txaResponce.setText(controller.getHistoryUiPlugin().getUiPlugin()
					.getResponce().get());
		}

		RootConfig.bindPostBody(txaPostBody, controller.getHistoryUiPlugin()
				.getUiPlugin().getMethod().get(), spHeaders, tblHeaders);

		tblHeaders = new HeaderTableView().initialize(controller
				.getHistoryUiPlugin().getUiPlugin().getHeadersList(),
				tblHeaders, null);

		tblParams = new ParamTableView().initialize(controller
				.getHistoryUiPlugin().getUiParams(), tblParams, onKey -> {
			uiSequence.getKeys().clear();
			onKey.forEach(key -> uiSequence.getKeys().add(key.getKey()));
		});
		tblParams
				.editableProperty()
				.addListener(
						(observer, oldValue, newValue) -> {
							context.execute(
									controller,
									ControllerActionStrategy.ROOT_FILL_UI_PLUGIN_ACCORDION);
						});

	}
}
