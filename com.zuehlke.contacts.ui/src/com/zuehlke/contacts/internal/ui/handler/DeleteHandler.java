package com.zuehlke.contacts.internal.ui.handler;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

import com.zuehlke.contacts.internal.ui.Activator;
import com.zuehlke.contacts.service.ContactService;
import com.zuehlke.contacts.service.CustomerService;
import com.zuehlke.contacts.service.dto.Contact;
import com.zuehlke.contacts.service.dto.Customer;

public class DeleteHandler extends EditorHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IStructuredSelection selection = (IStructuredSelection) HandlerUtil
				.getCurrentSelection(event);
		if (!selection.isEmpty()) {
			if (MessageDialog.openQuestion(HandlerUtil.getActiveShell(event),
					"Confirmation",
					"Do you really want to delete this element?")) {
				Object selectedElement = selection.getFirstElement();
				if (selectedElement instanceof Contact) {
					Contact contact = (Contact) selectedElement;
					closeEditor(contact);
					Activator.getDefault().getService(ContactService.class)
							.delete(contact.getId());
					refreshViews();
				} else if (selectedElement instanceof Customer) {
					Customer customer = (Customer) selectedElement;
					closeEditor(customer);
					for (Contact contact : Activator.getDefault()
							.getService(ContactService.class)
							.findByCustomer(customer.getId())) {
						closeEditor(contact);
					}
					Activator.getDefault().getService(CustomerService.class)
							.delete(customer.getId());
					refreshViews();
				}
			}
		}
		return null;
	}
}
