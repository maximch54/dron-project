package com.dron.sender.pattern.interfaces;

import com.dron.sender.controllers.base.interfaces.IBaseController;
import com.dron.sender.pattern.models.strategy.ControllerActionStrategy;

public interface IControllerStrategy {
	ControllerActionStrategy getStrategy();
	void execute(IBaseController iBaseController);
}
