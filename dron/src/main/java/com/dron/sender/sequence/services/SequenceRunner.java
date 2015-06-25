package com.dron.sender.sequence.services;

import com.dron.sender.exceptions.DronSenderException;
import com.dron.sender.exceptions.HandlerNotReadyException;
import com.dron.sender.sequence.models.Plugin;
import com.dron.sender.sequence.models.Sequence;

public class SequenceRunner {

	private RequestRunner restFullService = RequestRunner.getInstance();

	private FutureParamService futureParamService = FutureParamService
			.getInstance();

	private Sequence sequence;

	public SequenceRunner(Sequence sequence) {
		this.sequence = sequence;
	}

	public void runSequence() throws DronSenderException {
		for (String orderedId : sequence.getOrder()) {
			runPlugin(orderedId);
		}
	}

	public void runPlugin(String orderedId) throws DronSenderException {
		if (orderedId == null) {
			throw new HandlerNotReadyException("orderId can't be null");
		}

		Plugin plugin = sequence.findPlugin(orderedId);
		if (plugin == null) {
			throw new HandlerNotReadyException("Plugin can't be null");
		}

		try {
			plugin.setSequence(sequence);
			String response = restFullService.run(plugin);
			plugin.setResponce(response);
			plugin.setSuccess(true);
			futureParamService.fillFutureParams(plugin);

		} catch (Exception e) {
			plugin.setResponce(e.getMessage());
			plugin.setSuccess(false);
		} finally {
			// Added sent plugin to the history
			sequence.getSentPlugins().add(plugin.clone());
		}
	}
}
