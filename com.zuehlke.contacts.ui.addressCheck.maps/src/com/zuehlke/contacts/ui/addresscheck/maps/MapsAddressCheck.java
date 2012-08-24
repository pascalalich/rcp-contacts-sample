package com.zuehlke.contacts.ui.addresscheck.maps;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.eclipse.jface.dialogs.Dialog;

import com.zuehlke.contacts.service.dto.Address;
import com.zuehlke.contacts.ui.addresscheck.AddressCheckResult;
import com.zuehlke.contacts.ui.addresscheck.IAddressCheck;

public class MapsAddressCheck implements IAddressCheck {

	private static final String TITLE = "Google Maps Address Check";
	private static final String URL_PREFIX = "http://maps.google.com/?q=";

	@Override
	public AddressCheckResult check(Address address) {
		String url = getURL(address);
		BrowserDialog dialog = new BrowserDialog(800, 600, TITLE, url);
		int result = dialog.open();
		if (result == Dialog.OK) {
			return AddressCheckResult.ok();
		} else {
			return AddressCheckResult
					.error("Google Maps address check failed.");
		}
	}

	private String getAddressString(Address address) {
		StringBuilder s = new StringBuilder();
		s.append(address.getStreet()).append(" ")
				.append(address.getStreetNumber());
		s.append(", ");
		s.append(address.getPostalCode()).append(" ").append(address.getCity());
		s.append(", ");
		s.append(address.getCountry());
		return s.toString();
	}

	private String getURL(Address address) {
		String addressString = getAddressString(address);
		try {
			return URL_PREFIX
					+ URLEncoder.encode(addressString,
							System.getProperty("file.encoding", "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException(
					"Unable to construct Google Maps URL.", e);
		}
	}
}
