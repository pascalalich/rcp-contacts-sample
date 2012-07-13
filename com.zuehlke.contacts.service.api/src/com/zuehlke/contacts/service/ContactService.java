package com.zuehlke.contacts.service;

import java.util.Collection;

import com.zuehlke.contacts.service.dto.Contact;

public interface ContactService {

	public Contact create(Contact contact);

	/**
	 * Find by technical id
	 * 
	 * @param id
	 * @return
	 */
	public Contact findById(Long id);

	/**
	 * Find by an example contact
	 * 
	 * @param example
	 * @return
	 */
	public Collection<Contact> findByExample(Contact example);

	/**
	 * @return all contacts for a customer
	 */
	public Collection<Contact> findByCustomer(Long customerId);

	/**
	 * @param contact
	 */
	public void update(Contact contact);

	/**
	 * Delete by technical id
	 * 
	 * @param id
	 */
	public void delete(Long id);

}
