package com.dron.sender.controllers.root.controls;

import com.dron.sender.controllers.base.controls.BasePropertyTableView;
import com.dron.sender.controllers.root.models.UIHttpHeaders;

public class HeaderTableView extends BasePropertyTableView<UIHttpHeaders> {

	private static final int TABLE_PROPERTY_WIDTH = 120;

	@Override
	protected int getMinWidth() {
		return TABLE_PROPERTY_WIDTH;
	}

	@Override
	protected String getKeyName() {
		return UIHttpHeaders.PROPERTY_HEADER;
	}

	@Override
	protected String getValueName() {
		return UIHttpHeaders.PROPERTY_VALUE;
	}

	@Override
	protected UIHttpHeaders getEmptyInstance() {
		return new UIHttpHeaders();
	}

}
