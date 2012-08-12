package com.zuehlke.contacts.internal.ui.editor;

import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.editor.FormEditor;

import com.zuehlke.contacts.internal.ui.Activator;

public abstract class BasicFormEditor extends FormEditor {

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		super.init(site, input);
		setPartName(input.getName());
	}

	@Override
	public final void doSaveAs() {
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
