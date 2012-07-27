package com.zuehlke.contacts.service;

import java.util.ArrayList;
import java.util.Collection;

import com.zuehlke.contacts.service.ContactService;
import com.zuehlke.contacts.service.dto.Contact;

public class LocalContactService implements ContactService {

	@Override
	public void update(Contact contact) {
		// TODO Auto-generated method stub

	}

	@Override
	public Contact findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Contact> findByExample(Contact example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Contact> findByCustomer(Long customerId) {
		Collection<Contact> contacts = new ArrayList<Contact>();
		if (customerId.equals(1l)) {
			Contact contact1 = new Contact();
			contact1.setId(2l);
			contact1.setCustomer(2l);
			contact1.setGiven("Pascal");
			contact1.setName("Alich");
			contact1.setEmail("alp@zuehlke.com");
			contacts.add(contact1);
			Contact contact2 = new Contact();
			contact2.setId(1l);
			contact2.setCustomer(1l);
			contact2.setGiven("Stefan");
			contact2.setName("Reichert");
			contact2.setEmail("srt@zuehlke.com");
			contacts.add(contact2);
		} else if (customerId.equals(2l)) {
			Contact contact1 = new Contact();
			contact1.setId(2l);
			contact1.setCustomer(2l);
			contact1.setGiven("Max");
			contact1.setName("Marke");
			contact1.setEmail("mm@post.de");
			contacts.add(contact1);
		}
		return contacts;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Contact create(Contact contact) {
		// TODO Auto-generated method stub
		return null;
	}

}
