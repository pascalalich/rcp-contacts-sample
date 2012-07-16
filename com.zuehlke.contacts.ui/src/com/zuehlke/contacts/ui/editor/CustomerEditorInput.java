package com.zuehlke.contacts.ui.editor;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

import com.zuehlke.contacts.service.dto.Customer;
import com.zuehlke.contacts.ui.Activator;

public class CustomerEditorInput implements IEditorInput {

	private Customer customer;

	public CustomerEditorInput(Customer customer) {
		this.customer = customer;
	}

	public Customer getCustomer() {
		return customer;
	}

	@Override
	public boolean exists() {
		return true;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return Activator.getDefault().getImageRegistry()
				.getDescriptor("customer");
	}

	@Override
	public String getName() {
		if (customer.getName() != null) {
			return customer.getName();
		}
		return "New customer";
	}

	@Override
	public String getToolTipText() {
		String text = null;
		if (customer.getName() != null) {
			text = customer.getName() + " (" + customer.getNumber() + ")";
		} else {
			text = "New customer";
		}
		return text;
	}

	@Override
	public IPersistableElement getPersistable() {
		return null;
	}

	@Override
	public Object getAdapter(Class adapter) {
		// TODO Auto-generated method stub
		return null;
	}
}
