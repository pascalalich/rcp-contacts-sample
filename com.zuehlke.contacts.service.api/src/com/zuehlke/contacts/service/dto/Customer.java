package com.zuehlke.contacts.service.dto;

public class Customer extends BasicDto {

	private static final long serialVersionUID = 1L;

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
		Customer other = (Customer) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
