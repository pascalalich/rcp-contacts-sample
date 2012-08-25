package com.zuehlke.contacts.internal.ui.editor;

import java.util.Collection;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

import com.zuehlke.contacts.internal.ui.Activator;
import com.zuehlke.contacts.internal.ui.ErrorDialogHelper;
import com.zuehlke.contacts.service.ContactService;
import com.zuehlke.contacts.service.dto.Address;
import com.zuehlke.contacts.service.dto.Contact;
import com.zuehlke.contacts.ui.addresscheck.AddressCheckResult;
import com.zuehlke.contacts.ui.addresscheck.AddressCheckStatus;
import com.zuehlke.contacts.ui.addresscheck.IAddressCheck;
import com.zuehlke.contacts.ui.intent.ContactIntentContext;
import com.zuehlke.contacts.ui.intent.IContactIntent;

import de.ikoffice.widgets.SplitButton;

public class ContactFormPage extends BasicFormPage<Contact> {

	private Text nameText;
	private Text givenText;
	private Text customerText;

	private Text emailText;
	private Text phoneText;

	private Text streetText;
	private Text streetNumberText;
	private Text postalCodeText;
	private Text cityText;
	private Text countryText;

	private SplitButton emailIntentButton;
	private SplitButton phoneIntentButton;

	public ContactFormPage(FormEditor editor) {
		super(editor, ContactFormPage.class.getName(), "Customer");
	}

	@Override
	protected void createFormContent(IManagedForm managedForm) {
		FormToolkit toolkit = managedForm.getToolkit();
		Composite formBody = managedForm.getForm().getBody();
		formBody.setLayout(new GridLayout(1, false));

		Section generalSection = createGeneralSection(toolkit, formBody);
		createGeneralClient(toolkit, generalSection);

		Section contactSection = createContactSection(toolkit, formBody);
		createContactClient(toolkit, contactSection);

		Section addressSection = createAddressSection(toolkit, formBody);
		createAddressClient(toolkit, addressSection);

		initDefaults();
		initDirtyListeners();
		initIntents();
	}

	private Section createGeneralSection(FormToolkit toolkit, Composite formBody) {
		Section section = toolkit.createSection(formBody, Section.DESCRIPTION
				| Section.TITLE_BAR | Section.TWISTIE | Section.EXPANDED);
		section.setText("General Information");
		section.setDescription("This section describes general information about this contact");
		section.setExpanded(true);
		GridData gridData = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		section.setLayoutData(gridData);

		return section;
	}

	private void createGeneralClient(FormToolkit toolkit, Section section) {
		Composite client = toolkit.createComposite(section, SWT.WRAP);
		client.setLayout(new GridLayout(2, false));
		Label givenLabel = toolkit.createLabel(client, "Given");
		givenLabel.setLayoutData(new GridData(70, SWT.DEFAULT));
		givenText = toolkit.createText(client, "");
		givenText.setLayoutData(new GridData(200, SWT.DEFAULT));
		toolkit.createLabel(client, "Name");
		nameText = toolkit.createText(client, "");
		nameText.setLayoutData(new GridData(200, SWT.DEFAULT));
		toolkit.createLabel(client, "Customer");
		customerText = toolkit.createText(client, "");
		customerText.setLayoutData(new GridData(200, SWT.DEFAULT));
		customerText.setEnabled(false);
		section.setClient(client);
	}

	private Section createContactSection(FormToolkit toolkit, Composite formBody) {
		Section section = toolkit.createSection(formBody, Section.DESCRIPTION
				| Section.TITLE_BAR | Section.TWISTIE | Section.EXPANDED);
		section.setText("Contact Information");
		section.setDescription("This section describes information on how to get in contact");
		section.setExpanded(true);
		GridData gridData = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		gridData.verticalIndent = 20;
		section.setLayoutData(gridData);

		return section;
	}

	private void createContactClient(FormToolkit toolkit, Section section) {
		Composite client = toolkit.createComposite(section, SWT.WRAP);
		client.setLayout(new GridLayout(3, false));
		Label emailLabel = toolkit.createLabel(client, "Email");
		emailLabel.setLayoutData(new GridData(70, SWT.DEFAULT));
		emailText = toolkit.createText(client, "");
		emailText.setLayoutData(new GridData(200, SWT.DEFAULT));
		emailIntentButton = new SplitButton(client, SWT.NONE);
		emailIntentButton.setText("Action");
		emailIntentButton.setLayoutData(new GridData(100, SWT.DEFAULT));
		emailIntentButton.setEnabled(false);
		toolkit.createLabel(client, "Phone");
		phoneText = toolkit.createText(client, "");
		phoneText.setLayoutData(new GridData(200, SWT.DEFAULT));
		phoneIntentButton = new SplitButton(client, SWT.NONE);
		phoneIntentButton.setText("Action");
		phoneIntentButton.setLayoutData(new GridData(100, SWT.DEFAULT));
		phoneIntentButton.setEnabled(false);
		section.setClient(client);
	}

	private Section createAddressSection(FormToolkit toolkit, Composite formBody) {
		Section section = toolkit.createSection(formBody, Section.DESCRIPTION
				| Section.TITLE_BAR | Section.TWISTIE | Section.EXPANDED);
		section.setText("Address Information");
		section.setDescription("This section contains address information.\nPostal code / city combination is validated for Germany.");
		section.setExpanded(true);
		GridData gridData = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		gridData.verticalIndent = 20;
		section.setLayoutData(gridData);

		return section;
	}

	private void createAddressClient(FormToolkit toolkit, Section section) {
		Composite client = toolkit.createComposite(section, SWT.WRAP);
		client.setLayout(new GridLayout(3, false));
		Label streetLabel = toolkit.createLabel(client, "Street + No.");
		streetLabel.setLayoutData(new GridData(70, SWT.DEFAULT));
		streetText = toolkit.createText(client, "");
		streetText.setLayoutData(new GridData(200, SWT.DEFAULT));
		streetNumberText = toolkit.createText(client, "");
		streetNumberText.setLayoutData(new GridData(87, SWT.DEFAULT));
		toolkit.createLabel(client, "Postal code");
		postalCodeText = toolkit.createText(client, "");
		postalCodeText.setLayoutData(new GridData(200, SWT.DEFAULT));
		toolkit.createLabel(client, "");
		toolkit.createLabel(client, "City");
		cityText = toolkit.createText(client, "");
		cityText.setLayoutData(new GridData(200, SWT.DEFAULT));
		toolkit.createLabel(client, "");
		toolkit.createLabel(client, "Country");
		countryText = toolkit.createText(client, "");
		countryText.setLayoutData(new GridData(200, SWT.DEFAULT));
		toolkit.createLabel(client, "");
		section.setClient(client);
	}

	private void initDefaults() {
		Contact contact = getObject();
		if (contact.getName() != null) {
			nameText.setText(contact.getName());
		}
		if (contact.getGiven() != null) {
			givenText.setText(contact.getGiven());
		}
		// TODO how to display customer?
		Long customerId = contact.getCustomer();
		if (customerId != null) {
			customerText.setText(customerId.toString());
		}
		if (contact.getEmail() != null) {
			emailText.setText(contact.getEmail());
		}
		if (contact.getPhone() != null) {
			phoneText.setText(contact.getPhone());
		}
		Address address = contact.getAddress();
		if (address != null) {
			if (address.getStreet() != null) {
				streetText.setText(address.getStreet());
			}
			if (address.getStreetNumber() != null) {
				streetNumberText.setText(address.getStreetNumber());
			}
			if (address.getPostalCode() != null) {
				postalCodeText.setText(address.getPostalCode());
			}
			if (address.getCity() != null) {
				cityText.setText(address.getCity());
			}
			if (address.getCountry() != null) {
				countryText.setText(address.getCountry());
			}
		}
	}

	/**
	 * private Text nameText; private Text givenText; private Text customerText;
	 * 
	 * private Text emailText; private Text phoneText;
	 * 
	 * private Text streetText; private Text streetNumberText; private Text
	 * postalCodeText; private Text cityText; private Text countryText;
	 */
	private void initDirtyListeners() {
		ModifyListener listener = getDirtyListener();
		nameText.addModifyListener(listener);
		givenText.addModifyListener(listener);
		emailText.addModifyListener(listener);
		phoneText.addModifyListener(listener);
		streetText.addModifyListener(listener);
		streetNumberText.addModifyListener(listener);
		postalCodeText.addModifyListener(listener);
		cityText.addModifyListener(listener);
		countryText.addModifyListener(listener);
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		super.doSave(monitor);
		ContactService contactService = Activator.getDefault().getService(
				ContactService.class);
		if (contactService != null) {
			updateModel();
			Contact contact = getObject();
			if (checkAddress(contact.getAddress())) {
				if (contact.getId() == null) {
					setInput(new ContactEditorInput(
							contactService.create(contact)));
					firePropertyChange(PROP_INPUT);
					initDefaults();
				} else {
					contactService.update(contact);
				}
			}
		} else {
			// TODO error handling
			throw new RuntimeException("Contact could not be saved: "
					+ getObject().getId());
		}
	}

	/**
	 * Checks the address via service extensions.
	 * 
	 * @param address
	 *            the address to check
	 * @return <tt>true</tt> if address is OK, <tt>false</tt> otherwise
	 */
	private boolean checkAddress(Address address) {
		boolean ok = true;
		try {
			BundleContext bundleContext = Activator.getDefault().getBundle()
					.getBundleContext();
			Collection<ServiceReference<IAddressCheck>> references = bundleContext
					.getServiceReferences(IAddressCheck.class, null);
			for (ServiceReference<IAddressCheck> reference : references) {
				IAddressCheck addressCheck = bundleContext
						.getService(reference);
				AddressCheckResult result = addressCheck.check(address);
				if (result.getStatus() == AddressCheckStatus.ERROR) {
					ok = false;
					break;
				}
			}
		} catch (InvalidSyntaxException e) {
			throw new RuntimeException("Unable to check address.", e);
		}
		return ok;
	}

	private void updateModel() {
		Contact contact = getObject();
		contact.setName(nameText.getText());
		contact.setGiven(givenText.getText());
		contact.setEmail(emailText.getText());
		contact.setPhone(phoneText.getText());
		Address address = contact.getAddress();
		if (address == null) {
			address = new Address();
			contact.setAddress(address);
		}
		address.setStreet(streetText.getText());
		address.setStreetNumber(streetNumberText.getText());
		address.setPostalCode(postalCodeText.getText());
		address.setCity(cityText.getText());
		address.setCountry(countryText.getText());
	}

	private void initIntents() {
		IExtensionPoint extensionPoint = Platform.getExtensionRegistry()
				.getExtensionPoint(Activator.PLUGIN_ID, "intent");
		IConfigurationElement[] configurations = Platform
				.getExtensionRegistry().getConfigurationElementsFor(
						extensionPoint.getUniqueIdentifier());
		for (final IConfigurationElement configuration : configurations) {
			String field = configuration.getAttribute("field");
			Menu menu = null;
			if ("email".equals(field)) {
				menu = emailIntentButton.getMenu();
				emailIntentButton.setEnabled(true);
			} else if ("phone".equals(field)) {
				menu = phoneIntentButton.getMenu();
				phoneIntentButton.setEnabled(true);
			}

			final MenuItem menuItem = new MenuItem(menu, SWT.PUSH);
			menuItem.setText(configuration.getAttribute("label"));
			menuItem.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent event) {
					IContactIntent intent;
					try {
						// TODO instantiated too often
						intent = (IContactIntent) configuration
								.createExecutableExtension("class");
					} catch (CoreException e) {
						throw new RuntimeException(e);
					}
					ContactIntentContext context = null;
					String value = null;
					Menu sourceMenu = ((MenuItem) event.getSource())
							.getParent();
					if (sourceMenu == emailIntentButton.getMenu()) {
						context = ContactIntentContext.EMAIL;
						value = emailText.getText();
					} else if (sourceMenu == phoneIntentButton.getMenu()) {
						context = ContactIntentContext.PHONE;
						value = phoneText.getText();
					}
					if (context != null && value != null
							&& !value.trim().isEmpty()) {
						try {
							intent.call(context, value);
						} catch (RuntimeException e) {
							e.printStackTrace();
							ErrorDialog
									.openError(
											null,
											"Intent Error",
											"Unable to perform the action",
											ErrorDialogHelper
													.getErrorMultiStatus(
															e,
															Activator.PLUGIN_ID,
															IStatus.ERROR));
						}
					}
				}
			});
		}
	}
}
