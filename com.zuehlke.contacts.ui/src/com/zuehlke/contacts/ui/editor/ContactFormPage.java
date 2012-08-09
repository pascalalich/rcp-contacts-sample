package com.zuehlke.contacts.ui.editor;

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

import com.zuehlke.contacts.service.ContactService;
import com.zuehlke.contacts.service.dto.Contact;
import com.zuehlke.contacts.ui.Activator;

public class ContactFormPage extends BasicFormPage<Contact> {

	private Text nameText;
	private Text numberText;
	private Text mainContactText;

	public ContactFormPage(FormEditor editor) {
		super(editor, ContactFormPage.class.getName(), "Customer");
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
		section.setDescription("This section describes general information about this contact");
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
		Contact contact = getObject();
		// nameText.setText(customer.getName());
		// numberText.setText(customer.getNumber());
		// Long mainContact = customer.getMainContact();
		// if (mainContact != null) {
		// mainContactText.setText(mainContact.toString());
		// }
	}

	private void initDirtyListeners() {
		ModifyListener listener = getDirtyListener();
		// nameText.addModifyListener(listener);
		// numberText.addModifyListener(listener);
		// mainContactText.addModifyListener(listener);
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		super.doSave(monitor);
		ContactService contactService = Activator.getDefault().getService(
				ContactService.class);
		if (contactService != null) {
			updateModel();
			Contact contact = getObject();
			if (contact.getId() == null) {
				contactService.create(contact);
			} else {
				contactService.update(contact);
			}
			setDirty(false);
			// TODO send event to refresh view
		} else {
			// TODO error handling
			throw new RuntimeException("Contact could not be saved: "
					+ getObject().getId());
		}
	}

	private void updateModel() {
		Contact contact = getObject();
		// contact.setName(nameText.getText());
		// customer.setNumber(numberText.getText());
		// Long mainContact = null;
		// String mainContactId = mainContactText.getText().trim();
		// if (!mainContactId.isEmpty()) {
		// mainContact = Long.parseLong(mainContactId);
		// }
		// customer.setMainContact(mainContact);
	}
}
