package com.zuehlke.contacts.internal.ui.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.zuehlke.contacts.internal.ui.editor.BasicFormEditor;
import com.zuehlke.contacts.internal.ui.editor.ContactEditorInput;
import com.zuehlke.contacts.internal.ui.editor.CustomerEditorInput;
import com.zuehlke.contacts.internal.ui.view.IRefreshable;
import com.zuehlke.contacts.service.dto.Contact;
import com.zuehlke.contacts.service.dto.Customer;

public abstract class EditorHandler extends AbstractHandler {

	protected void openEditor(Contact contact) throws ExecutionException {
		ContactEditorInput input = new ContactEditorInput(contact);
		openEditor("com.zuehlke.contacts.ui.editor.contact", input);
	}

	protected void openEditor(Customer customer) throws ExecutionException {
		CustomerEditorInput input = new CustomerEditorInput(customer);
		openEditor("com.zuehlke.contacts.ui.editor.customer", input);
	}

	private void openEditor(String editorId, IEditorInput input)
			throws ExecutionException {
		try {
			IEditorPart editor = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage()
					.openEditor(input, editorId);
			editor.addPropertyListener(new IPropertyListener() {

				@Override
				public void propertyChanged(Object source, int propId) {
					if (propId == BasicFormEditor.PROP_SAVED) {
						refreshViews();
					}
				}

			});
		} catch (PartInitException e) {
			throw new ExecutionException("Could not open editor", e);
		}
	}

	protected void refreshViews() {
		IViewReference[] references = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getViewReferences();
		for (IViewReference reference : references) {
			IViewPart view = reference.getView(false);
			if (view != null) {
				IRefreshable adapter = (IRefreshable) view
						.getAdapter(IRefreshable.class);
				if (adapter != null) {
					adapter.refresh();
				}
			}
		}
	}

	protected void closeEditor(Contact contact) throws ExecutionException {
		ContactEditorInput input = new ContactEditorInput(contact);
		closeEditor("com.zuehlke.contacts.ui.editor.contact", input);
	}

	protected void closeEditor(Customer customer) throws ExecutionException {
		CustomerEditorInput input = new CustomerEditorInput(customer);
		closeEditor("com.zuehlke.contacts.ui.editor.customer", input);
	}

	private void closeEditor(String editorId, IEditorInput input)
			throws ExecutionException {
		IEditorReference[] editors = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage()
				.findEditors(input, editorId, IWorkbenchPage.MATCH_INPUT);
		if (editors != null) {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow()
					.getActivePage().closeEditors(editors, false);
		}
	}
}
