package com.zuehlke.contacts4.ui.intent.skype;

import java.awt.Desktop;
import java.net.URI;

import com.zuehlke.contacts4.ui.intent.ContactIntentContext;
import com.zuehlke.contacts4.ui.intent.IContactIntent;

public class SkypeCallIntent implements IContactIntent {

	@Override
	public void call(ContactIntentContext context, String value) {
		try {
			URI uri = new URI("skype:" + value);
			Desktop.getDesktop().browse(uri);
		} catch (Exception e) {
			throw new RuntimeException("Unable to call via Skype", e);
		}
	}

}
