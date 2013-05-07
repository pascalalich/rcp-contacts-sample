package com.zuehlke.contacts4.ui.addresscheck;

import com.zuehlke.contacts.service.dto.Address;

/**
 * Callback for address check extensions.
 */
public interface IAddressCheck {

	/**
	 * Checks the address.
	 * 
	 * @param address
	 *            the address to check (mandatory)
	 * @return the check result
	 */
	AddressCheckResult check(Address address);

}
