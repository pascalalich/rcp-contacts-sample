package com.zuehlke.contacts.service.dto;

public class Contact extends BasicDto {

	private static final long serialVersionUID = 1L;
	
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Contact other = (Contact) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	

}
