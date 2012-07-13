package com.zuehlke.contacts.ui.provider;

import org.eclipse.jface.viewers.LabelProvider;

import com.zuehlke.contacts.service.dto.Contact;
import com.zuehlke.contacts.service.dto.Customer;

public class ContactTreeLabelProvider extends LabelProvider {

	@Override
	public String getText(Object element) {
		if (element instanceof Contact) {
			Contact contact = (Contact) element;
			return contact.getName() + ", " + contact.getGiven();
		} else if (element instanceof Customer) {
			Customer customer = (Customer) element;
			return customer.getName() + " (" + customer.getNumber() + ")";
		}
		return "";
	}
}
