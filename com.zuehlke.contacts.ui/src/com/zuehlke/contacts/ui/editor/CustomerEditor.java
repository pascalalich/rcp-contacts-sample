package com.zuehlke.contacts.ui.editor;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.editor.FormEditor;

import com.zuehlke.contacts.ui.Activator;

public class CustomerEditor extends FormEditor {

	private CustomerFormPage page;

	@Override
	protected void addPages() {
		try {
			page = new CustomerFormPage(this);
			addPage(page);
		} catch (PartInitException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		page.doSave(monitor);
	}

	@Override
	public void doSaveAs() {
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public Image getTitleImage() {
		return Activator.getDefault().getImageRegistry().get("customer");
	}
}
