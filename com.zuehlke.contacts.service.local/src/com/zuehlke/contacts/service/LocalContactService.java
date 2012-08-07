package com.zuehlke.contacts.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.zuehlke.contacts.service.dto.Address;
import com.zuehlke.contacts.service.dto.Contact;

public class LocalContactService extends BasicLocalService<Contact> implements
		ContactService {

	@Override
	public Collection<Contact> findByExample(Contact example) {
		return null;
	}

	@Override
	public Collection<Contact> findByCustomer(Long customerId) {
		Set<Contact> matches = new HashSet<Contact>();
		for (Contact contact : getData().values()) {
			if (customerId.equals(contact.getCustomer())) {
				matches.add(findById(contact.getId()));
			}
		}
		return matches;
	}


	@Override
	protected Contact copy(Contact source, Contact target) {
		target.setId(source.getId());
		target.setCustomer(source.getCustomer());
		target.setEmail(source.getEmail());
		target.setGiven(source.getGiven());
		target.setName(source.getName());
		target.setPhone(source.getPhone());
		if (source.getAddress() == null) {
			target.setAddress(null);
		} else {
			Address address = new Address();
			address.setCity(source.getAddress().getCity());
			address.setCity(source.getAddress().getCountry());
			address.setCity(source.getAddress().getPostalCode());
			address.setCity(source.getAddress().getStreet());
			address.setCity(source.getAddress().getStreetNumber());
			target.setAddress(address);
		}
		return target;
	}

	@Override
	protected Collection<Contact> getInitialData() {
		Collection<Contact> contacts = new ArrayList<Contact>();
		Contact contact1 = new Contact();
		contact1.setId(1l);
		contact1.setCustomer(1l);
		contact1.setGiven("Pascal");
		contact1.setName("Alich");
		contact1.setEmail("alp@zuehlke.com");
		contacts.add(contact1);
		Contact contact2 = new Contact();
		contact2.setId(2l);
		contact2.setCustomer(1l);
		contact2.setGiven("Stefan");
		contact2.setName("Reichert");
		contact2.setEmail("srt@zuehlke.com");
		contacts.add(contact2);
		Contact contact3 = new Contact();
		contact3.setId(3l);
		contact3.setCustomer(2l);
		contact3.setGiven("Max");
		contact3.setName("Marke");
		contact3.setEmail("mm@post.de");
		contacts.add(contact3);
		return contacts;
	}

	@Override
	protected Long getId(Contact t) {
		return t.getId();
	}

	@Override
	protected void setId(Contact t, Long id) {
		t.setId(id);
	}

	protected Contact clone(Contact contact) {
		return copy(contact, new Contact());
	}

}
