package com.zuehlke.contacts.ui.intent.outlook;

import java.awt.Desktop;
import java.net.URI;

import com.zuehlke.contacts.ui.intent.ContactIntentContext;
import com.zuehlke.contacts.ui.intent.IContactIntent;

public class OutlookMailtoIntent implements IContactIntent {

	@Override
	public void call(ContactIntentContext context, String value) {
		String uriStr = "mailto:" + value;
		try {
			URI uri = new URI(uriStr);
			Desktop.getDesktop().mail(uri);
		} catch (Exception e) {
			throw new RuntimeException("Unable to call mailto link '" + uriStr
					+ "'", e);
		}
	}

}
