package com.zuehlke.contacts4.internal.ui.editors;

import java.net.URI;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.e4.ui.model.application.ui.basic.MInputPart;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

import com.zuehlke.contacts.service.CustomerService;
import com.zuehlke.contacts.service.dto.Customer;

@SuppressWarnings("restriction")
public class CustomerEditor extends BasicFormEditor<Customer> {

	private Text nameText;
	private Text numberText;
	private Text mainContactText;

	@Inject
	private CustomerService customerService;

	@Inject
	public CustomerEditor(MInputPart inputPart, Display display) {
		super(inputPart, display);
	}

	@PostConstruct
	public void createPartControl(Composite composite) {
		// create the form
		ScrolledForm scrolledForm = getToolkit().createScrolledForm(composite);
		Composite formBody = scrolledForm.getForm().getBody();
		formBody.setLayout(new FillLayout());
		// create the form content section
		createSection(formBody);
		// update editor with input data
		updateInput(new CustomerEditorInput(loadCustomer()));
		// initalize dirty listeners
		initDirtyListeners();
	}

	@Persist
	public void save() {
		if (customerService != null) {
			updateModel();
			Customer customer = getObject();
			if (customer.getId() == null) {
				updateInput(new CustomerEditorInput(
						customerService.create(customer)));
			} else {
				customerService.update(customer);
				getInputPart().setLabel(getEditorInput().getName());
			}
			setDirty(false);
		} else {
			throw new RuntimeException("Customer could not be saved: "
					+ getObject().getId());
		}
	}

	
	@Focus
	public void setFocus(){
		nameText.setFocus();
	}
	
	@Override
	protected String getTitleImageURI() {
		return "platform:/plugin/com.zuehlke.contacts4.ui/icons/customer.gif";
	}

	@Override
	protected void updateWidgets() {
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

	@Override
	protected void updateModel() {
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

	private void createSection(Composite formBody) {
		// create the section
		Section section = getToolkit().createSection(
				formBody,
				Section.DESCRIPTION | Section.TITLE_BAR | Section.TWISTIE
						| Section.EXPANDED);
		section.setText("General Information");
		section.setDescription("This section describes general information about this customer");
		section.setExpanded(true);

		// create the section client
		Composite client = getToolkit().createComposite(section, SWT.WRAP);
		client.setLayout(new GridLayout(2, false));
		getToolkit().createLabel(client, "Name");
		nameText = getToolkit().createText(client, "");
		nameText.setLayoutData(new GridData(200, SWT.DEFAULT));
		getToolkit().createLabel(client, "Number");
		numberText = getToolkit().createText(client, "");
		numberText.setLayoutData(new GridData(200, SWT.DEFAULT));
		getToolkit().createLabel(client, "Main contact");
		// TODO better main contact editing
		mainContactText = getToolkit().createText(client, "");
		mainContactText.setLayoutData(new GridData(200, SWT.DEFAULT));
		section.setClient(client);
	}

	private Customer loadCustomer() {
		URI inputURI = URI.create(getInputPart().getInputURI());
		String customerId = inputURI.getFragment();
		Customer customer;
		if ("new".equals(customerId)) {
			customer = new Customer();
		} else {
			customer = customerService.findById(Long.parseLong(customerId));
		}
		return customer;
	};

	private void initDirtyListeners() {
		ModifyListener listener = getDirtyListener();
		nameText.addModifyListener(listener);
		numberText.addModifyListener(listener);
		mainContactText.addModifyListener(listener);
	}

}