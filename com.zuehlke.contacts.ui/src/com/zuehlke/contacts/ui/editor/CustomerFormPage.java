package com.zuehlke.contacts.ui.editor;

import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;

public class CustomerFormPage extends FormPage {

	public CustomerFormPage(FormEditor editor) {
		super(editor, CustomerFormPage.class.getName(), "Customer");
	}

	@Override
	protected void createFormContent(IManagedForm managedForm) {
		super.createFormContent(managedForm);
	}
}
