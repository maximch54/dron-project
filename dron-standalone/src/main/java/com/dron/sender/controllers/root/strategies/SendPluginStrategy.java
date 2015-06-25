package com.dron.sender.controllers.root.strategies;

import javafx.application.Platform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.dron.sender.controllers.base.interfaces.IBaseController;
import com.dron.sender.controllers.root.RootController;
import com.dron.sender.controllers.root.models.BaseRootController;
import com.dron.sender.controllers.root.tasks.PluginTask;
import com.dron.sender.pattern.interfaces.IControllerStrategy;
import com.dron.sender.pattern.models.strategy.ControllerActionStrategy;
import com.dron.sender.pattern.models.transformers.TransformKey;
import com.dron.sender.pattern.services.observers.BaseFormatLoggerObserver;
import com.dron.sender.pattern.services.observers.BaseLoggerObserver;
import com.dron.sender.pattern.services.strategies.ControllerStrategyContext;
import com.dron.sender.pattern.services.transformers.TransformerFactory;
import com.dron.sender.sequence.models.Param;
import com.dron.sender.sequence.models.Plugin;
import com.dron.sender.sequence.models.Sequence;

@Component
public class SendPluginStrategy extends BaseRootController implements
		IControllerStrategy {

	@Autowired
	private ControllerStrategyContext context;

	private RootController controller;

	@Autowired
	private ApplicationContext ctx;

	@Override
	public ControllerActionStrategy getStrategy() {
		return ControllerActionStrategy.ROOT_SEND_PLUGIN;
	}

	@Override
	public void execute(IBaseController iBaseController) {
		controller = (RootController) iBaseController;
		setUp(controller);

		Sequence sequence = fillRootSequence();
		initLogging(sequence);

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				context.execute(controller,
						ControllerActionStrategy.ROOT_DISABLE_CONTROLS);
			}
		});

		PluginTask pluginTask = new PluginTask(sequence, ctx);

		controller.setSendRequestThread(new Thread(pluginTask));
		controller.getSendRequestThread().start();
	}

	private Sequence fillRootSequence() {
		Sequence sequence = new Sequence();
		sequence.setSelectedPluginId(controller.getHistoryUiPlugin()
				.getUiPlugin().getId().get());

		sequence.getOrder().add(
				controller.getHistoryUiPlugin().getUiPlugin().getId().get());

		sequence.getSentPlugins().clear();

		TransformerFactory.reverseTransformEntity(sequence.getParams(),
				controller.getHistoryUiPlugin().getUiParams(),
				TransformKey.ROOT_PARAMS);

		Plugin plugin = new Plugin();
		TransformerFactory.reverseTransformEntity(plugin, controller
				.getHistoryUiPlugin().getUiPlugin(), TransformKey.ROOT_PLUGIN);
		sequence.getPlugins().add(plugin);
		return sequence;
	}

	private void initLogging(Sequence sequence) {
		for (Plugin plugin : sequence.getPlugins()) {
			new BaseLoggerObserver(plugin, txaResponce,
					Plugin.PROPERTY_RESPONCE);
			new BaseFormatLoggerObserver(plugin, txaConsole,
					Plugin.PROPERTY_RESPONCE);
		}

		for (Param param : sequence.getParams()) {
			new BaseLoggerObserver(param, txaResponce);
		}
	}

}
