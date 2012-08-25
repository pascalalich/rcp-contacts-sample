package com.zuehlke.contacts.service;

import java.util.ArrayList;
import java.util.Collection;

import com.zuehlke.contacts.service.dto.Contact;
import com.zuehlke.contacts.service.dto.Customer;

public class LocalCustomerService extends BasicLocalService<Customer> implements
		CustomerService {

	private LocalContactService contactService;

	public LocalCustomerService(LocalContactService contactService) {
		super();
		this.contactService = contactService;
	}

	@Override
	protected Customer copy(Customer source, Customer target) {
		target.setMainContact(source.getMainContact());
		target.setName(source.getName());
		target.setNumber(source.getNumber());
		target.setId(source.getId());
		return target;
	}

	@Override
	protected Collection<Customer> getInitialData() {
		Collection<Customer> customers = new ArrayList<Customer>();
		Customer customer_one = new Customer();
		customer_one.setId(1L);
		customer_one.setName("Zühlke Engineering");
		customer_one.setNumber("2012.001");
		customer_one.setMainContact(1l);
		customers.add(customer_one);
		Customer customer_two = new Customer();
		customer_two.setId(2L);
		customer_two.setName("Deutsche Post");
		customer_two.setNumber("2012.002");
		customer_two.setMainContact(2l);
		customers.add(customer_two);
		return customers;
	}

	@Override
	protected Long getId(Customer t) {
		return t.getId();
	}

	@Override
	protected void setId(Customer t, Long id) {
		t.setId(id);
	}

	@Override
	protected Customer clone(Customer t) {
		return copy(t, new Customer());
	}

	@Override
	public void delete(Long id) {
		Collection<Contact> contacts = contactService.findByCustomer(id);
		for (Contact contact : contacts) {
			contactService.delete(contact.getId());
		}
		super.delete(id);
	}

}
