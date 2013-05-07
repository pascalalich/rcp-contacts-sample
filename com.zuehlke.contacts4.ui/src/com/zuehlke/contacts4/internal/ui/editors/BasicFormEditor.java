package com.zuehlke.contacts4.internal.ui.editors;

import org.eclipse.e4.ui.model.application.ui.basic.MInputPart;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.FormToolkit;

@SuppressWarnings("restriction")
public abstract class BasicFormEditor<T> {

	private final FormToolkit toolkit;

	private final MInputPart inputPart;
	private ModifyListener listener;
	private BasicEditorInput<T> editorInput;

	public BasicFormEditor(MInputPart inputPart, Display display) {
		this.inputPart = inputPart;
		toolkit = new FormToolkit(display);
	}

	public void updateInput(BasicEditorInput<T> editorInput) {
		this.editorInput = editorInput;
		updateWidgets();
		inputPart.setLabel(editorInput.getName());
		inputPart.setIconURI(getTitleImageURI());
	}

	protected final ModifyListener getDirtyListener() {
		if (listener == null) {
			listener = new ModifyListener() {
				@Override
				public void modifyText(ModifyEvent e) {
					setDirty(true);
				}
			};
		}
		return listener;
	}

	protected void setDirty(boolean dirty) {
		inputPart.setDirty(dirty);
	}

	protected FormToolkit getToolkit() {
		return toolkit;
	}

	protected T getObject() {
		return editorInput.getObject();
	}

	protected MInputPart getInputPart() {
		return inputPart;
	}

	protected BasicEditorInput<T> getEditorInput() {
		return editorInput;
	}

	protected abstract String getTitleImageURI();

	protected abstract void updateModel();

	protected abstract void updateWidgets();

}
