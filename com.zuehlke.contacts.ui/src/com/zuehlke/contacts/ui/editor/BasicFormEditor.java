package com.zuehlke.contacts.ui.editor;

import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.forms.editor.FormEditor;

import com.zuehlke.contacts.ui.Activator;

public abstract class BasicFormEditor extends FormEditor {

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
