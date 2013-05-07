package com.zuehlke.contacts4.ui.addresscheck.zipcity;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;

import com.zuehlke.contacts.service.dto.Address;
import com.zuehlke.contacts4.ui.addresscheck.AddressCheckResult;
import com.zuehlke.contacts4.ui.addresscheck.IAddressCheck;

public class ZipCityAddressCheck implements IAddressCheck {

	private GeoNamesPostalCodes postalCodes = GeoNamesPostalCodes.getInstance();

	@Override
	public AddressCheckResult check(Address address) {
		String postalCode = address.getPostalCode();
		String city = address.getCity();

		if (postalCode != null && city != null) {
			if (!postalCodes.exists(postalCode, city)) {
				String message = "Postal code / city combination does not exist.";
				ErrorDialog.openError(null, "Address Check Error", message,
						new Status(IStatus.ERROR, Activator.PLUGIN_ID, null));
				return AddressCheckResult.error(message);
			}
		}

		return AddressCheckResult.ok();
	}
}
