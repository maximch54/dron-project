package com.dron.sender.pattern.services.observers;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javafx.application.Platform;
import javafx.scene.control.TextArea;

import com.dron.sender.pattern.interfaces.IBaseObserver;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BaseLoggerObserver implements PropertyChangeListener {

	private TextArea txaLogger;

	private String property;

	private ObjectMapper mapper = new ObjectMapper();

	public BaseLoggerObserver(IBaseObserver observer, TextArea txaLogger) {
		this(observer, txaLogger, null);
	}

	public BaseLoggerObserver(IBaseObserver observer, TextArea txaLogger,
			String property) {
		observer.addChangeListener(this);
		this.txaLogger = txaLogger;
		this.property = property;
	}

	@Override
	public void propertyChange(final PropertyChangeEvent evt) {
		if (property == null || evt.getPropertyName().equals(property)) {
			Platform.runLater(() -> {
				try {
					Object json = mapper.readValue(
							evt.getNewValue().toString(), Object.class);
					txaLogger.setText(mapper.writerWithDefaultPrettyPrinter()
							.writeValueAsString(json));
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			});
		}
	}
}
