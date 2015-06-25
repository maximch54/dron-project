package com.dron.sender.controllers.root.strategies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dron.sender.controllers.base.interfaces.IBaseController;
import com.dron.sender.controllers.root.RootController;
import com.dron.sender.controllers.root.models.BaseRootController;
import com.dron.sender.pattern.interfaces.IControllerStrategy;
import com.dron.sender.pattern.models.strategy.ControllerActionStrategy;
import com.dron.sender.pattern.services.strategies.ControllerStrategyContext;

@Component
public class MovePluginUpStrategy extends BaseRootController implements
		IControllerStrategy {

	@Autowired
	private ControllerStrategyContext context;

	@Override
	public ControllerActionStrategy getStrategy() {
		return ControllerActionStrategy.ROOT_MOVE_PLUGIN_UP;
	}

	@Override
	public void execute(final IBaseController iBaseController) {
		final RootController controller = (RootController) iBaseController;
		setUp(controller);

		uiSequence.movePluginUp(getHistoryUiPlugin().getUiPlugin().getId()
				.get());

		context.execute(controller,
				ControllerActionStrategy.ROOT_FILL_UI_PLUGIN_ACCORDION);
		uiSequence.selectedUIPLugin(getHistoryUiPlugin().getUiPlugin().getId()
				.get(), context, controller);
	}

}
