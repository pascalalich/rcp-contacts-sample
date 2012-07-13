package com.zuehlke.contacts.service;

import java.util.Collection;

import com.zuehlke.contacts.service.dto.Customer;

public interface CustomerService {

	public Customer create(Customer customer);

	/**
	 * Find by technical id
	 * 
	 * @param id
	 * @return
	 */
	public Customer findById(Long id);

	/**
	 * Find by an example customer
	 * 
	 * @param example
	 * @return
	 */
	public Collection<Customer> findByExample(Customer example);

	/**
	 * @return all customers
	 */
	public Collection<Customer> findAll();

	/**
	 * @param customer
	 */
	public void update(Customer customer);

	/**
	 * Delete by technical id
	 * 
	 * @param id
	 */
	public void delete(Long id);

}
