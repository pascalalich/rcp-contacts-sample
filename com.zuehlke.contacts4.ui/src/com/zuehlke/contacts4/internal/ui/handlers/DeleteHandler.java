package com.zuehlke.contacts4.internal.ui.handlers;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

import com.zuehlke.contacts.service.ContactService;
import com.zuehlke.contacts.service.CustomerService;
import com.zuehlke.contacts.service.dto.Contact;
import com.zuehlke.contacts.service.dto.Customer;
import com.zuehlke.contacts4.internal.ui.Activator;

public class DeleteHandler extends EditorHandler {

	@Inject
	private CustomerService customerService;

	@Inject
	private ContactService contactService;

	@CanExecute
	public boolean canExcecute() {
		Object selectedElement = getSelection();
		return (selectedElement instanceof Contact || selectedElement instanceof Customer);
	}

	@Execute
	public void execute(@Named(IServiceConstants.ACTIVE_SHELL) Shell activeShell) {
		Object selectedElement = getSelection();

		if (selectedElement != null) {
			if (MessageDialog.openQuestion(activeShell, "Confirmation",
					"Do you really want to delete this element?")) {

				if (selectedElement instanceof Contact) {
					Contact contact = (Contact) selectedElement;
					closeEditor(contact);
					contactService.delete(contact.getId());
					sendDataChangedEvent();
				} else if (selectedElement instanceof Customer) {
					Customer customer = (Customer) selectedElement;
					closeEditor(customer);
					for (Contact contact : Activator.getDefault()
							.getService(ContactService.class)
							.findByCustomer(customer.getId())) {
						closeEditor(contact);
					}
					customerService.delete(customer.getId());
					sendDataChangedEvent();
				}
			}
		}
	}
}
