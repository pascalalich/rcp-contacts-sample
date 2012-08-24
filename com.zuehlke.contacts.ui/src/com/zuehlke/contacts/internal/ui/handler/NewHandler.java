package com.zuehlke.contacts.internal.ui.handler;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

import com.zuehlke.contacts.service.dto.Contact;
import com.zuehlke.contacts.service.dto.Customer;

public class NewHandler extends EditorHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		String type = event.getParameter("type");
		if ("customer".equals(type)) {
			openEditor(new Customer());

		} else if ("contact".equals(type)) {
			IStructuredSelection selection = (IStructuredSelection) HandlerUtil
					.getCurrentSelection(event);
			if (!selection.isEmpty()
					&& selection.getFirstElement() instanceof Customer) {
				Customer customer = (Customer) selection.getFirstElement();
				Contact contact = new Contact();
				contact.setCustomer(customer.getId());
				openEditor(contact);
			}
		}
		return null;
	}

}
