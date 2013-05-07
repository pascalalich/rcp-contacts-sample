package com.zuehlke.contacts4.internal.ui.editors;


public abstract class BasicEditorInput<T> {

	private T object;

	public BasicEditorInput(T object) {
		if (object == null) {
			throw new IllegalArgumentException("Object must not be null.");
		}
		this.object = object;
	}

	public T getObject() {
		return object;
	}
	
	public abstract String getName();
	
	public abstract String getToolTipText();

	public String getInputURI() {
		return null;
	}
	
}
