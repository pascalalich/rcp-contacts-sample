package com.zuehlke.contacts.service.dto;

/**
 * Address type
 */
public class Address extends BasicDto {

	private String street;

	private String streetNumber;

	private String postalCode;

	private String city;

	private String country;

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		firePropertyChange("street", this.street, this.street = street);
	}

	public String getStreetNumber() {
		return streetNumber;
	}

	public void setStreetNumber(String streetNumber) {
		firePropertyChange("streetNumber", this.streetNumber,
				this.streetNumber = streetNumber);
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		firePropertyChange("postalCode", this.postalCode,
				this.postalCode = postalCode);
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		firePropertyChange("city", this.city, this.city = city);

	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		firePropertyChange("country", this.country, this.country = country);
	}

}
