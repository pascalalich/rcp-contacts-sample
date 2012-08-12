package com.zuehlke.contacts.service;

import java.util.Collection;

import com.zuehlke.contacts.service.dto.Customer;

public class RemoteCustomerService implements CustomerService {

	@Override
	public Customer create(Customer customer) {
		throw new UnsupportedOperationException("not yet implemented");
	}

	@Override
	public Customer findById(Long id) {
		throw new UnsupportedOperationException("not yet implemented");
	}

	@Override
	public Collection<Customer> findByExample(Customer example) {
		throw new UnsupportedOperationException("not yet implemented");
	}

	@Override
	public Collection<Customer> findAll() {
		throw new UnsupportedOperationException("not yet implemented");
	}

	@Override
	public void update(Customer customer) {
		throw new UnsupportedOperationException("not yet implemented");
	}

	@Override
	public void delete(Long id) {
		throw new UnsupportedOperationException("not yet implemented");
	}

}
