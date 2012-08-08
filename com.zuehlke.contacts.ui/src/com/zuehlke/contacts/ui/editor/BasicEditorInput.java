package com.zuehlke.contacts.ui.editor;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

public abstract class BasicEditorInput<T> implements IEditorInput {

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

	// TODO Customer and Contact DTO should also implement hashCode/equals
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}
		if (obj.getClass() != this.getClass()) {
			return false;
		}
		BasicEditorInput<?> other = (BasicEditorInput<?>) obj;
		return this.object.equals(other.object);
	}

	@Override
	public int hashCode() {
		return object.hashCode();
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
