package com.zuehlke.contacts.ui.editor;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import com.zuehlke.contacts.service.dto.Customer;

public class CustomerFormPage extends FormPage {

	private Text nameText;
	private Text numberText;
	private Text mainContactText;

	private boolean dirty;

	public CustomerFormPage(FormEditor editor) {
		super(editor, CustomerFormPage.class.getName(), "Customer");
	}

	@Override
	protected void createFormContent(IManagedForm managedForm) {

		FormToolkit toolkit = managedForm.getToolkit();
		Composite formBody = managedForm.getForm().getBody();
		formBody.setLayout(new GridLayout(1, false));

		Section section = createSection(toolkit, formBody);
		createClient(toolkit, section);

		initDefaults();
		initDirtyListeners();

	}

	private Section createSection(FormToolkit toolkit, Composite formBody) {
		Section section = toolkit.createSection(formBody, Section.DESCRIPTION
				| Section.TITLE_BAR | Section.TWISTIE | Section.EXPANDED);
		section.setText("General Information");
		section.setDescription("This section describes general information about this customer");
		section.setExpanded(true);
		section.setLayoutData(new GridData(GridData.FILL_BOTH));

		return section;
	}

	private void createClient(FormToolkit toolkit, Section section) {
		Composite client = toolkit.createComposite(section, SWT.WRAP);
		client.setLayout(new GridLayout(2, false));
		toolkit.createLabel(client, "Name");
		nameText = toolkit.createText(client, "");
		nameText.setLayoutData(new GridData(200, SWT.DEFAULT));
		toolkit.createLabel(client, "Number");
		numberText = toolkit.createText(client, "");
		numberText.setLayoutData(new GridData(200, SWT.DEFAULT));
		toolkit.createLabel(client, "Main contact");
		// TODO better main contact editing
		mainContactText = toolkit.createText(client, "");
		mainContactText.setLayoutData(new GridData(200, SWT.DEFAULT));
		section.setClient(client);
	}

	private void initDefaults() {
		Customer customer = getCustomer();
		nameText.setText(customer.getName());
		numberText.setText(customer.getNumber());
		Long mainContact = customer.getMainContact();
		if (mainContact != null) {
			mainContactText.setText(mainContact.toString());
		}
	}

	private void initDirtyListeners() {
		ModifyListener listener = new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				dirty = true;
				getEditor().editorDirtyStateChanged();
			}
		};
		nameText.addModifyListener(listener);
		numberText.addModifyListener(listener);
		mainContactText.addModifyListener(listener);
	}

	private Customer getCustomer() {
		return ((CustomerEditorInput) getEditorInput()).getCustomer();
	}

	@Override
	public boolean isDirty() {
		return dirty;
	}

}
