package com.zuehlke.contacts4.internal.ui.provider;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.zuehlke.contacts.service.ContactService;
import com.zuehlke.contacts.service.dto.Customer;
import com.zuehlke.contacts4.internal.ui.Activator;

public class ContactTreeContentProvider extends ArrayContentProvider implements
		ITreeContentProvider {

	@Override
	public void dispose() {

	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// intentionally do nothing
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof Customer) {
			Customer customer = (Customer) parentElement;
			// this is very bad as we normally would use deferred tree support
			// here...
			ContactService contactService = Activator.getDefault().getService(
					ContactService.class);
			if (contactService != null) {
				return contactService.findByCustomer(customer.getId())
						.toArray();
			}
			MessageDialog
					.openError(null, "Error",
							"Could not load contacts, the service is currently not available!");
			return new Object[0];
		}
		return new Object[0];
	}

	@Override
	public Object getParent(Object element) {
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		return element instanceof Customer;
	}

}
