package com.dron.sender.sequence.models;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import com.dron.sender.pattern.interfaces.IBaseObserver;

public abstract class BaseNotificationModel implements IBaseObserver {

	private List<PropertyChangeListener> listeners = new ArrayList<>();

	protected void notifyListeners(final Object object, final String property,
			final Object oldValue, final Object newValue) {
		for (final PropertyChangeListener listener : listeners) {
			listener.propertyChange(new PropertyChangeEvent(object, property,
					oldValue, newValue));
		}
	}

	public void addChangeListener(PropertyChangeListener newListener) {
		listeners.add(newListener);
	}

}
