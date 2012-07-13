package com.zuehlke.contacts.service.dto;

public class Customer {

	/**
	 * Technical id
	 */
	private Long id;

	/**
	 * Functional id (unique)
	 */
	private String number;

	private String name;

	/**
	 * Main contact's technical id
	 */
	private Long mainContact;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getMainContact() {
		return mainContact;
	}

	public void setMainContact(Long mainContact) {
		this.mainContact = mainContact;
	}

}
