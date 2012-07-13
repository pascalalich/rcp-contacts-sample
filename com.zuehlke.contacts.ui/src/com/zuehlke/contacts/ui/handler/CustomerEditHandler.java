package com.zuehlke.contacts.ui.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

import com.zuehlke.contacts.service.dto.Customer;

public class CustomerEditHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection selection = HandlerUtil.getCurrentSelection(event);
		if (!selection.isEmpty() && selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			Object selectedElement = structuredSelection.getFirstElement();
			if (selectedElement instanceof Customer) {
				Customer customer = (Customer) selectedElement;
				MessageDialog
						.openInformation(
								null,
								"Edit Customer",
								"Editing customer "
										+ customer.getNumber()
										+ " is impossible as the corresponding editor is missing!");
			}
		}
		return null;
	}

}
