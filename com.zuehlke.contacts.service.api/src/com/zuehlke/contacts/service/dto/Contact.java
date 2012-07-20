package com.zuehlke.contacts.service.dto;

public class Contact extends BasicDto {

	/**
	 * Technical id
	 */
	private Long id;

	/**
	 * Technical customer id
	 */
	private Long customer;

	private String name;

	private String given;

	private String email;

	private String phone;

	private Address address;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		firePropertyChange("id", this.id, this.id = id);
	}

	public Long getCustomer() {
		return customer;
	}

	public void setCustomer(Long customer) {
		firePropertyChange("customer", this.customer, this.customer = customer);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		firePropertyChange("name", this.name, this.name = name);
	}

	public String getGiven() {
		return given;
	}

	public void setGiven(String given) {
		firePropertyChange("given", this.given, this.given = given);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		firePropertyChange("email", this.email, this.email = email);
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		firePropertyChange("phone", this.phone, this.phone = phone);
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		firePropertyChange("address", this.address, this.address = address);
	}

}
