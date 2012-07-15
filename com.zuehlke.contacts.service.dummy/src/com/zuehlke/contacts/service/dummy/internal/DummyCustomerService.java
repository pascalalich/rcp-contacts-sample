package com.zuehlke.contacts.service.dummy.internal;

import java.util.ArrayList;
import java.util.Collection;

import com.zuehlke.contacts.service.CustomerService;
import com.zuehlke.contacts.service.dto.Customer;

public class DummyCustomerService implements CustomerService {

	@Override
	public void update(Customer customer) {
		// TODO Auto-generated method stub

	}

	@Override
	public Customer findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Customer> findByExample(Customer example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Customer> findAll() {
		Customer customer_one = new Customer();
		customer_one.setId(1L);
		customer_one.setName("Zühlke Engineering");
		customer_one.setNumber("2012.001");
		customer_one.setMainContact(1l);
		Customer customer_two = new Customer();
		customer_two.setId(2L);
		customer_two.setName("Deutsche Post");
		customer_two.setNumber("2012.002");
		customer_two.setMainContact(2l);
		Collection<Customer> customers = new ArrayList<Customer>();
		customers.add(customer_two);
		customers.add(customer_one);
		return customers;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Customer create(Customer customer) {
		// TODO Auto-generated method stub
		return null;
	}

}
