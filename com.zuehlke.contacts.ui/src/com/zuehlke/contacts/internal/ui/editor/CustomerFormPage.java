package com.zuehlke.contacts.internal.ui.editor;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import com.zuehlke.contacts.internal.ui.Activator;
import com.zuehlke.contacts.service.CustomerService;
import com.zuehlke.contacts.service.dto.Customer;

public class CustomerFormPage extends BasicFormPage<Customer> {

	private Text nameText;
	private Text numberText;
	private Text mainContactText;

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
		Customer customer = getObject();
		if (customer.getName() != null) {
			nameText.setText(customer.getName());
		}
		if (customer.getNumber() != null) {
			numberText.setText(customer.getNumber());
		}
		Long mainContact = customer.getMainContact();
		if (mainContact != null) {
			mainContactText.setText(mainContact.toString());
		}
	}

	private void initDirtyListeners() {
		ModifyListener listener = getDirtyListener();
		nameText.addModifyListener(listener);
		numberText.addModifyListener(listener);
		mainContactText.addModifyListener(listener);
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		super.doSave(monitor);
		CustomerService customerService = Activator.getDefault().getService(
				CustomerService.class);
		if (customerService != null) {
			updateModel();
			Customer customer = getObject();
			if (customer.getId() == null) {
				updateInput(new CustomerEditorInput(
						customerService.create(customer)));
				initDefaults();
			} else {
				customerService.update(customer);
			}
			setDirty(false);
		} else {
			// TODO error handling
			throw new RuntimeException("Customer could not be saved: "
					+ getObject().getId());
		}
	}

	private void updateModel() {
		Customer customer = getObject();
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
