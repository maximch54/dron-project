package com.dron.sender.controllers.root.controls;

import com.dron.sender.controllers.base.controls.BasePropertyTableView;
import com.dron.sender.controllers.root.models.UIFutureParam;
import com.dron.sender.sequence.models.FutureParam;

public class FutureParamTableView extends BasePropertyTableView<UIFutureParam> {

	private static final int TABLE_PROPERTY_WIDTH = 120;

	@Override
	protected int getMinWidth() {
		return TABLE_PROPERTY_WIDTH;
	}

	@Override
	protected String getKeyName() {
		return FutureParam.PROPERTY_KEY;
	}

	@Override
	protected String getValueName() {
		return FutureParam.PROPERTY_DEPENDENCE;
	}

	@Override
	protected UIFutureParam getEmptyInstance() {
		return new UIFutureParam();
	}

}
