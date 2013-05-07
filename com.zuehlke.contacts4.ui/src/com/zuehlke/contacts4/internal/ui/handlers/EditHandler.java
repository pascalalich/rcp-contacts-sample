package com.zuehlke.contacts4.internal.ui.handlers;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;

import com.zuehlke.contacts.service.dto.Contact;
import com.zuehlke.contacts.service.dto.Customer;

public class EditHandler extends EditorHandler {

	@CanExecute
	public boolean canExecute() {
		Object selectedElement = getSelection();
		return (selectedElement instanceof Customer || selectedElement instanceof Contact);
	}

	@Execute
	public void execute() {
		Object selectedElement = getSelection();
		if (selectedElement instanceof Customer) {
			openEditor((Customer) selectedElement);

		} else if (selectedElement instanceof Contact) {
			openEditor((Contact) selectedElement);
		}
	}
}
