package com.zuehlke.contacts.ui.provider;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import com.zuehlke.contacts.service.dto.Contact;
import com.zuehlke.contacts.service.dto.Customer;
import com.zuehlke.contacts.ui.Activator;

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

	@Override
	public Image getImage(Object element) {
		Image image = null;
		if (element instanceof Contact) {
			image = Activator.getDefault().getImage("contact");
		} else if (element instanceof Customer) {
			image = Activator.getDefault().getImage("customer");
		}
		return image;
	}
}
