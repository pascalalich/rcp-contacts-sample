package com.zuehlke.contacts.ui.editor;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

import com.zuehlke.contacts.service.dto.Customer;

public class CustomerFormPage extends FormPage {

	public CustomerFormPage(FormEditor editor) {
		super(editor, CustomerFormPage.class.getName(), "Customer");
	}

	@Override
	protected void createFormContent(IManagedForm managedForm) {

		FormToolkit toolkit = managedForm.getToolkit();
		ScrolledForm form = managedForm.getForm();
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		form.getBody().setLayout(layout);

		Section section = toolkit.createSection(form.getBody(), Section.TWISTIE
				| Section.DESCRIPTION);

		Composite client = toolkit.createComposite(section, SWT.WRAP);
		layout = new GridLayout();
		layout.numColumns = 2;
		client.setLayout(layout);
		toolkit.createLabel(client, "Name");
		toolkit.createText(client, getCustomer().getName());
		toolkit.createLabel(client, "Number");
		toolkit.createText(client, getCustomer().getNumber());
		section.setText("General");
		section.setClient(client);
		section.setExpanded(true);

	}

	private Customer getCustomer() {
		return ((CustomerEditorInput) getEditorInput()).getCustomer();
	}

}
