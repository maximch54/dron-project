package com.dron.sender.pattern.services.strategies;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dron.sender.controllers.base.interfaces.IBaseController;
import com.dron.sender.pattern.interfaces.IControllerStrategy;
import com.dron.sender.pattern.models.strategy.ControllerActionStrategy;

@Component
public class ControllerStrategyContext {

	private Map<ControllerActionStrategy, IControllerStrategy> controllerStrategies;

	@Autowired
	public void setUp(List<IControllerStrategy> strategies) {
		controllerStrategies = new HashMap<ControllerActionStrategy, IControllerStrategy>();
		strategies.forEach(strategy -> {
			controllerStrategies.put(strategy.getStrategy(), strategy);
		});
	}

	public void execute(IBaseController iBaseController,
			ControllerActionStrategy actionStrategy) throws RuntimeException {
		IControllerStrategy strategy = controllerStrategies.get(actionStrategy);
		if (strategy != null) {
			strategy.execute(iBaseController);
		} else {
			throw new RuntimeException("Can't find Strategy for "
					+ actionStrategy.name());
		}
	}
}
