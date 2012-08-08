package com.zuehlke.contacts.ui.editor;

import com.zuehlke.contacts.service.dto.Contact;

public class ContactEditorInput extends BasicEditorInput<Contact> {

	public ContactEditorInput(Contact contact) {
		super(contact);
	}

	@Override
	public String getName() {
		Contact contact = getObject();
		StringBuilder text = new StringBuilder();
		if (contact.getId() != null) {
			text.append(contact.getGiven()).append(" ")
					.append(contact.getName());
		} else {
			text.append("New contact");
		}
		return text.toString();
	}

	@Override
	public String getToolTipText() {
		Contact contact = getObject();
		StringBuilder text = new StringBuilder();
		text.append(getName());
		if (contact.getId() != null) {
			if (contact.getEmail() != null) {
				text.append(" (").append(contact.getEmail()).append(")");
			}
		}
		return text.toString();
	}
}
