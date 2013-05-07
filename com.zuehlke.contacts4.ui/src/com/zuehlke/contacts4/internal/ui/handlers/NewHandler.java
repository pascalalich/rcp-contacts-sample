package com.zuehlke.contacts4.internal.ui.handlers;

import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;

import com.zuehlke.contacts.service.dto.Contact;
import com.zuehlke.contacts.service.dto.Customer;

public class NewHandler extends EditorHandler {

	@CanExecute
	public boolean canExecute(@Named("type") String type) {
		Object selectedElement = getSelection();
		return ("customer".equals(type) || ("contact".equals(type) && selectedElement instanceof Customer));
	}

	@Execute
	public void execute(@Named("type") String type) {
		if ("customer".equals(type)) {
			System.out.println("Open new customer editor");
			openEditor(new Customer());

		} else if ("contact".equals(type)) {
			Object selectedElement = getSelection();
			if (selectedElement instanceof Customer) {
				Customer customer = (Customer) selectedElement;
				Contact contact = new Contact();
				contact.setCustomer(customer.getId());
				System.out.println("Open new contact editor for customer "
						+ customer.getNumber());
				openEditor(contact);
			}
		}
	}

}
