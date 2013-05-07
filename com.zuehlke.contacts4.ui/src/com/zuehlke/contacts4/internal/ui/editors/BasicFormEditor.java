package com.zuehlke.contacts4.internal.ui.editors;

import org.eclipse.e4.ui.model.application.ui.basic.MInputPart;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;

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

	protected void createForm(Composite composite) {
		ScrolledForm scrolledForm = getToolkit().createScrolledForm(composite);
		createFormContent(scrolledForm.getForm());
	}

	public void updateInput(BasicEditorInput<T> editorInput) {
		this.editorInput = editorInput;
		updateWidgets();
		inputPart.setLabel(editorInput.getName());
		inputPart.setIconURI(getTitleImageURI());
	}

	public void save() {
		updateModel();
		inputPart.setLabel(editorInput.getName());
		setDirty(false);
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

	protected abstract void createFormContent(Form form);

	protected abstract String getTitleImageURI();

	protected abstract void updateModel();

	protected abstract void updateWidgets();

}
