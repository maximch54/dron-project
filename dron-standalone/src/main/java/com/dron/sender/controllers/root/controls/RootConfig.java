package com.dron.sender.controllers.root.controls;

import com.dron.sender.controllers.root.models.UIHttpHeaders;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

public class RootConfig {

	private static final double TXA_POST_BODY_HEIGHT = 300.0;

	private static BooleanProperty disableRootProperty = new SimpleBooleanProperty(
			false);

	private static DoubleProperty postBodyHeight = new SimpleDoubleProperty(
			TXA_POST_BODY_HEIGHT);

	public static void setDisableRootProperty(boolean disable) {
		RootConfig.disableRootProperty.set(disable);
	}

	public static void bindPostBody(TextArea txaPostBody, String method,
			SplitPane spHeaders, TableView<UIHttpHeaders> tblHeaders) {
		double headerPosition = tblHeaders.isVisible() ? 0.3 : 0.0;
		double postPosition;
		switch (method) {
			case "POST":
			case "PUT":
				postPosition = 0.3;
				txaPostBody.setVisible(true);
				spHeaders.setDividerPositions(headerPosition, postPosition, 1.0
						- headerPosition - postPosition);
				postBodyHeight.set(TXA_POST_BODY_HEIGHT);
				break;
			default:
				postPosition = 0.0;
				txaPostBody.setVisible(false);
				spHeaders.setDividerPositions(headerPosition, postPosition, 1.0
						- headerPosition - postPosition);
				postBodyHeight.set(-10);
				break;
		}
	}

	public static BooleanProperty getDisableRootProperty() {
		return disableRootProperty;
	}
}
