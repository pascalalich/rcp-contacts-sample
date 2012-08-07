package com.zuehlke.contacts.ui.editor;

import org.eclipse.core.runtime.IProgressMonitor;
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

import com.zuehlke.contacts.service.CustomerService;
import com.zuehlke.contacts.service.dto.Customer;
import com.zuehlke.contacts.ui.Activator;

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
				setDirty(true);
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

	private void setDirty(boolean dirty) {
		if (this.dirty != dirty) {
			this.dirty = dirty;
			getEditor().editorDirtyStateChanged();
		}
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		super.doSave(monitor);
		CustomerService customerService = Activator.getDefault().getService(
				CustomerService.class);
		if (customerService != null) {
			updateModel();
			Customer customer = getCustomer();
			if (customer.getId() == null) {
				customerService.create(customer);
			} else {
				customerService.update(customer);
			}
			setDirty(false);
			// TODO send event to refresh view
		} else {
			// TODO error handling
			throw new RuntimeException("Customer could not be saved: "
					+ getCustomer().getId());
		}
	}

	private void updateModel() {
		Customer customer = getCustomer();
		customer.setName(nameText.getText());
		customer.setNumber(numberText.getText());
		Long mainContact = null;
		String mainContactId = mainContactText.getText().trim();
		if (!mainContactId.isEmpty()) {
			mainContact = Long.parseLong(mainContactId);
		}
		customer.setMainContact(mainContact);
	}
}
