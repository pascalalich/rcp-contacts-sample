package com.zuehlke.contacts.ui.editor;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

public abstract class BasicEditorInput<T> implements IEditorInput {

	private T object;

	public BasicEditorInput(T object) {
		this.object = object;
	}

	public T getObject() {
		return object;
	}

	@Override
	public final boolean exists() {
		return true;
	}

	@Override
	public final ImageDescriptor getImageDescriptor() {
		return null;
	}

	@Override
	public final IPersistableElement getPersistable() {
		return null;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public final Object getAdapter(Class adapter) {
		// no adaptability
		return null;
	}
}
