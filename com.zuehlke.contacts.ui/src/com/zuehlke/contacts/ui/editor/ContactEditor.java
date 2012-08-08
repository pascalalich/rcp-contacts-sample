package com.zuehlke.contacts.ui.editor;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.PartInitException;

public class ContactEditor extends BasicFormEditor {

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
	protected String getTitleImageKey() {
		return "contact";
	}

}
