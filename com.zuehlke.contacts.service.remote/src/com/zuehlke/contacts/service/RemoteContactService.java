package com.zuehlke.contacts.service;

import java.util.Collection;

import com.zuehlke.contacts.service.dto.Contact;

public class RemoteContactService implements ContactService {

	@Override
	public Contact create(Contact contact) {
		throw new UnsupportedOperationException("not yet implemented");
	}

	@Override
	public Contact findById(Long id) {
		throw new UnsupportedOperationException("not yet implemented");
	}

	@Override
	public Collection<Contact> findByExample(Contact example) {
		throw new UnsupportedOperationException("not yet implemented");
	}

	@Override
	public Collection<Contact> findByCustomer(Long customerId) {
		throw new UnsupportedOperationException("not yet implemented");
	}

	@Override
	public void update(Contact contact) {
		throw new UnsupportedOperationException("not yet implemented");
	}

	@Override
	public void delete(Long id) {
		throw new UnsupportedOperationException("not yet implemented");
	}

}
