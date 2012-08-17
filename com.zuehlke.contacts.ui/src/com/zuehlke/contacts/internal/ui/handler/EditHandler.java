package com.zuehlke.contacts.internal.ui.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

import com.zuehlke.contacts.internal.ui.editor.ContactEditorInput;
import com.zuehlke.contacts.internal.ui.editor.CustomerEditorInput;
import com.zuehlke.contacts.service.dto.Contact;
import com.zuehlke.contacts.service.dto.Customer;

public class EditHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection selection = HandlerUtil.getCurrentSelection(event);
		if (!selection.isEmpty() && selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			Object selectedElement = structuredSelection.getFirstElement();
			if (selectedElement instanceof Customer) {
				Customer customer = (Customer) selectedElement;
				CustomerEditorInput input = new CustomerEditorInput(customer);
				openEditor("com.zuehlke.contacts.ui.editor.customer", input);

			} else if (selectedElement instanceof Contact) {
				Contact contact = (Contact) selectedElement;
				ContactEditorInput input = new ContactEditorInput(contact);
				openEditor("com.zuehlke.contacts.ui.editor.contact", input);
			}
		}
		return null;
	}

	private void openEditor(String editorId, IEditorInput input)
			throws ExecutionException {
		try {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow()
					.getActivePage().openEditor(input, editorId);
		} catch (PartInitException e) {
			throw new ExecutionException("Could not open editor", e);
		}
	}
}
