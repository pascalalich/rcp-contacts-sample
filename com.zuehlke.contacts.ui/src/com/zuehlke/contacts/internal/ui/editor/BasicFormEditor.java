package com.zuehlke.contacts.internal.ui.editor;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.editor.FormEditor;

import com.zuehlke.contacts.internal.ui.Activator;

public abstract class BasicFormEditor extends FormEditor {

	public final static int PROP_SAVED = 42;

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		super.init(site, input);
		setPartName(input.getName());
	}

	@Override
	public final void doSaveAs() {
	}

	public void updateInput(IEditorInput editorInput) {
		setInput(editorInput);
		firePropertyChange(PROP_INPUT);
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		setPartName(getEditorInput().getName());
		firePropertyChange(PROP_TITLE);
		firePropertyChange(PROP_SAVED);
	}

	@Override
	public final boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public final Image getTitleImage() {
		return Activator.getDefault().getImageRegistry()
				.get(getTitleImageKey());
	}

	protected abstract String getTitleImageKey();
}
