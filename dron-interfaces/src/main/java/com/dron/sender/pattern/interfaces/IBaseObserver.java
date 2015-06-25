package com.dron.sender.pattern.interfaces;

import java.beans.PropertyChangeListener;

public interface IBaseObserver {

	void addChangeListener(PropertyChangeListener newListener);
}
