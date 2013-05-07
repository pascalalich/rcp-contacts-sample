package com.zuehlke.contacts4.ui.intent.outlook;

import org.eclipse.swt.SWT;
import org.eclipse.swt.ole.win32.OLE;
import org.eclipse.swt.ole.win32.OleAutomation;
import org.eclipse.swt.ole.win32.OleClientSite;
import org.eclipse.swt.ole.win32.OleFrame;
import org.eclipse.swt.ole.win32.Variant;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import com.zuehlke.contacts4.ui.intent.ContactIntentContext;
import com.zuehlke.contacts4.ui.intent.IContactIntent;

public class OutlookMailIntent implements IContactIntent {

	@Override
	public void call(ContactIntentContext context, final String value) {
		// TODO doesn't work yet
		
		Display display = PlatformUI.getWorkbench().getDisplay();
		Shell shell = new Shell(display);
		OleFrame frame = new OleFrame(shell, SWT.NONE);
		// This should start outlook if it is not running yet
		OleClientSite site = new OleClientSite(frame, SWT.NONE, "OVCtl.OVCtl");
		site.doVerb(OLE.OLEIVERB_INPLACEACTIVATE);
		// Now get the outlook application
		OleClientSite site2 = new OleClientSite(frame, SWT.NONE,
				"Outlook.Application");
		OleAutomation outlook = new OleAutomation(site2);
		OleAutomation mail = invoke(outlook, "CreateItem", 0 /* Mail item */)
				.getAutomation();
		setProperty(mail, "To", "test@gmail.com"); /*
													 * Empty but could also be
													 * predefined
													 */
		setProperty(mail, "Bcc", "test@gmail.com"); /*
													 * Empty but could also be
													 * predefined
													 */
		setProperty(mail, "BodyFormat", 2 /* HTML */);
		setProperty(mail, "Subject", "Top News for you");
		setProperty(mail, "HtmlBody",
				"<html>Hello<p>, please find some infos here.</html>");
		invoke(mail, "Display" /* or "Send" */);
	}

	private static Variant invoke(OleAutomation auto, String command) {
		return auto.invoke(property(auto, command));
	}

	private static Variant invoke(OleAutomation auto, String command, int value) {
		return auto.invoke(property(auto, command),
				new Variant[] { new Variant(value) });
	}

	private static boolean setProperty(OleAutomation auto, String name,
			String value) {
		return auto.setProperty(property(auto, name), new Variant(value));
	}

	private static boolean setProperty(OleAutomation auto, String name,
			int value) {
		return auto.setProperty(property(auto, name), new Variant(value));
	}

	private static int property(OleAutomation auto, String name) {
		return auto.getIDsOfNames(new String[] { name })[0];
	}

	// final Display display = PlatformUI.getWorkbench().getDisplay();
	// display.syncExec(new Runnable() {
	//
	// @Override
	// public void run() {
	// Shell shell = new Shell(display);
	// shell.setText("New mail");
	// OleFrame frame = new OleFrame(shell, SWT.NONE);
	// // Now get the outlook application
	// OleClientSite site = new OleClientSite(frame, SWT.NONE,
	// "Outlook.Application");
	// OleAutomation outlook = new OleAutomation(site);
	//
	// int createItemProperty = outlook
	// .getIDsOfNames(new String[] { "CreateItem" })[0];
	// OleAutomation mailItem = outlook.invoke(createItemProperty,
	// new Variant[] { new Variant(0) }).getAutomation();
	// int toProperty = mailItem.getIDsOfNames(new String[] { "TO" })[0];
	// mailItem.setProperty(toProperty, new Variant(value));
	// }
	// });
}
