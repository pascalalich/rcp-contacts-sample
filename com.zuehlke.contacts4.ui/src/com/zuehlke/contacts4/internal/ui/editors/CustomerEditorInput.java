package com.zuehlke.contacts4.internal.ui.editors;

import com.zuehlke.contacts.service.dto.Customer;

public class CustomerEditorInput extends BasicEditorInput<Customer> {

	public CustomerEditorInput(Customer customer) {
		super(customer);
	}

	@Override
	public String getName() {
		Customer customer = getObject();
		StringBuilder text = new StringBuilder();
		if (customer.getId() != null) {
			text.append(customer.getName());
		} else {
			text.append("New customer");
		}
		return text.toString();
	}

	@Override
	public String getToolTipText() {
		Customer customer = getObject();
		StringBuilder text = new StringBuilder();
		text.append(getName());
		if (customer.getId() != null) {
			text.append(" (").append(customer.getNumber()).append(")");
		}
		return text.toString();
	}
}
