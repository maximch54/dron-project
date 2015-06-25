/*

 *
 */
package com.dron.sender.controllers.base.controls;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.SkinBase;
import javafx.scene.control.TextField;
import javafx.stage.Popup;

public class AutoFillTextBoxSkin extends SkinBase<AutoFillTextBoxBase>
		implements ChangeListener<String> {

	private AutoFillTextBoxBase autoFillTextBox;

	private Popup popup;

	private TextField textField;

	public AutoFillTextBoxSkin(final AutoFillTextBoxBase autoFillTextBox) {
		super(autoFillTextBox);

		this.autoFillTextBox = autoFillTextBox;
		this.textField = autoFillTextBox.getTextBox();
		this.popup = autoFillTextBox.getPopup();

		textField.setOnKeyPressed(event -> {
			// WHEN USER PRESS DOWN ARROW KEY FOCUS TRANSFER TO LISTVIEW
				switch (event.getCode()) {
					case BACK_SPACE:
					case DELETE:
					case ESCAPE:
						textField.requestFocus();
						popup.hide();
						break;
					default:
						break;
				}
			});
		textField.textProperty().addListener(this);

		textField.focusedProperty().addListener(
				(observable, oldValue, newValue) -> {
					if (newValue) {
						textField.requestFocus();
					}
				});

		// Adding textbox in this control Children
		getChildren().addAll(textField);
	}

	/**
	 * ********************************************* When ever the the
	 * rawTextProperty is changed then this listener is activated
	 *
	 * @param observable
	 * @param oldValue
	 * @param newValue
	 *********************************************** */
	@Override
	public void changed(ObservableValue<? extends String> observable,
			String oldValue, String newValue) {
		autoFillTextBox.showPopup(newValue);
	}
}