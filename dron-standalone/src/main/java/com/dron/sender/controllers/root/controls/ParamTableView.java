package com.dron.sender.controllers.root.controls;

import com.dron.sender.controllers.base.controls.BasePropertyTableView;
import com.dron.sender.controllers.root.models.UIParam;
import com.dron.sender.sequence.models.Param;

public class ParamTableView extends BasePropertyTableView<UIParam> {

	private static final int TABLE_PROPERTY_WIDTH = 120;

	@Override
	protected int getMinWidth() {
		return TABLE_PROPERTY_WIDTH;
	}

	@Override
	protected String getKeyName() {
		return Param.PROPERTY_KEY;
	}

	@Override
	protected String getValueName() {
		return Param.PROPERTY_VALUE;
	}

	@Override
	protected UIParam getEmptyInstance() {
		return new UIParam();
	}

}
