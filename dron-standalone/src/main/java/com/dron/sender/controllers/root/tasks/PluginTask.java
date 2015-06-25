package com.dron.sender.controllers.root.tasks;

import javafx.application.Platform;
import javafx.concurrent.Task;

import org.springframework.context.ApplicationContext;

import com.dron.sender.controllers.root.RootController;
import com.dron.sender.exceptions.DronSenderException;
import com.dron.sender.exceptions.HandlerNotReadyException;
import com.dron.sender.exceptions.RequestException;
import com.dron.sender.pattern.models.strategy.ControllerActionStrategy;
import com.dron.sender.pattern.models.transformers.TransformKey;
import com.dron.sender.pattern.services.strategies.ControllerStrategyContext;
import com.dron.sender.pattern.services.transformers.TransformerFactory;
import com.dron.sender.sequence.models.Sequence;
import com.dron.sender.sequence.services.SequenceRunner;

public class PluginTask extends Task<String> {

	private Sequence sequence;

	private ControllerStrategyContext context;

	private RootController controller;

	public PluginTask(Sequence sequence, ApplicationContext ctx) {
		this.sequence = sequence;
		this.controller = ctx.getBean(RootController.class);
		this.context = ctx.getBean(ControllerStrategyContext.class);
	}

	@Override
	protected String call() {
		final SequenceRunner sequenceService = new SequenceRunner(sequence);

		try {
			sequenceService.runPlugin(sequence.getSelectedPluginId());
		} catch (DronSenderException e) {
			if (e instanceof HandlerNotReadyException) {
				System.out.println("Dron ERROR " + e.getMessage());
			}
			if (e instanceof RequestException) {
				System.out.println("Request ERROR " + e.getMessage());
			}
		} catch (Exception e) {
			System.out.println("System ERROR " + e.getMessage());
		} finally {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					TransformerFactory.transformEntity(sequence,
							controller.getUiSequence(),
							TransformKey.ROOT_SEQUENCE);

					context.execute(controller,
							ControllerActionStrategy.ROOT_ENABLE_CONTROLS);

				}
			});
		}

		return null;
	}
}
