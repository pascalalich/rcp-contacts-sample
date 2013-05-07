package com.zuehlke.contacts4.internal.ui.handlers;

import javax.inject.Inject;

import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MBasicFactory;
import org.eclipse.e4.ui.model.application.ui.basic.MInputPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;

import com.zuehlke.contacts.service.dto.Contact;
import com.zuehlke.contacts.service.dto.Customer;

@SuppressWarnings("restriction")
public abstract class EditorHandler {

	@Inject
	private MApplication application;
	@Inject
	private EModelService modelService;
	@Inject
	private EPartService partService;
	@Inject
	private ESelectionService selectionService;

	protected Object getSelection() {
		return selectionService.getSelection();
	}

	protected void openEditor(Contact contact) {
		System.out.println("Open editor for contact " + contact.getName());

		String partId = String.format(
				"com.zuehlke.contacts4.internal.ui.editors.ContactEditor#%d",
				contact.getId());

		MInputPart part = (MInputPart) partService.findPart(partId);
		if (part == null) {
			part = MBasicFactory.INSTANCE.createInputPart();
			part.setElementId(partId);
			part.setContributionURI("bundleclass://com.zuehlke.contacts4.ui/com.zuehlke.contacts4.internal.ui.editors.ContactEditor");
			if (contact.getId() == null) {
				part.setInputURI("resource:/contact#new");
			} else {
				part.setInputURI(String.format("resource:/contact#%d",
						contact.getId()));
			}
			part.setIconURI("platform:/plugin/com.zuehlke.contacts4.ui/icons/contact.gif");
			part.setCloseable(true);
			MPartStack stack = (MPartStack) modelService.find(
					"com.zuehlke.contacts4.right", application);
			stack.getChildren().add(part);
		}
		partService.showPart(part, PartState.ACTIVATE);
	}

	protected void openEditor(Customer customer) {
		System.out.println("Open editor for customer " + customer.getNumber());

		String partId = String.format(
				"com.zuehlke.contacts4.internal.ui.editors.CustomerEditor#%d",
				customer.getId());

		MInputPart part = (MInputPart) partService.findPart(partId);
		if (part == null) {
			part = MBasicFactory.INSTANCE.createInputPart();
			part.setElementId(partId);
			part.setContributionURI("bundleclass://com.zuehlke.contacts4.ui/com.zuehlke.contacts4.internal.ui.editors.CustomerEditor");
			if (customer.getId() == null) {
				part.setInputURI("resource:/customer#new");
			} else {
				part.setInputURI(String.format("resource:/customer#%d",
						customer.getId()));
			}
			part.setIconURI("platform:/plugin/com.zuehlke.contacts4.ui/icons/customer.gif");
			part.setCloseable(true);
			MPartStack stack = (MPartStack) modelService.find(
					"com.zuehlke.contacts4.right", application);
			stack.getChildren().add(part);
		}
		partService.showPart(part, PartState.ACTIVATE);
	}
	//
	// private void openEditor(String editorId, IEditorInput input)
	// throws ExecutionException {
	// try {
	// IEditorPart editor = PlatformUI.getWorkbench()
	// .getActiveWorkbenchWindow().getActivePage()
	// .openEditor(input, editorId);
	// editor.addPropertyListener(new IPropertyListener() {
	//
	// @Override
	// public void propertyChanged(Object source, int propId) {
	// if (propId == BasicFormEditor.PROP_SAVED) {
	// refreshViews();
	// }
	// }
	//
	// });
	// } catch (PartInitException e) {
	// throw new ExecutionException("Could not open editor", e);
	// }
	// }
	//
	// protected void refreshViews() {
	// IViewReference[] references = PlatformUI.getWorkbench()
	// .getActiveWorkbenchWindow().getActivePage().getViewReferences();
	// for (IViewReference reference : references) {
	// IViewPart view = reference.getView(false);
	// if (view != null) {
	// IRefreshable adapter = (IRefreshable) view
	// .getAdapter(IRefreshable.class);
	// if (adapter != null) {
	// adapter.refresh();
	// }
	// }
	// }
	// }
	//
	// protected void closeEditor(Contact contact) throws ExecutionException {
	// ContactEditorInput input = new ContactEditorInput(contact);
	// closeEditor("com.zuehlke.contacts4.ui.editor.contact", input);
	// }
	//
	// protected void closeEditor(Customer customer) throws ExecutionException {
	// CustomerEditorInput input = new CustomerEditorInput(customer);
	// closeEditor("com.zuehlke.contacts4.ui.editor.customer", input);
	// }
	//
	// private void closeEditor(String editorId, IEditorInput input)
	// throws ExecutionException {
	// IEditorReference[] editors = PlatformUI.getWorkbench()
	// .getActiveWorkbenchWindow().getActivePage()
	// .findEditors(input, editorId, IWorkbenchPage.MATCH_INPUT);
	// if (editors != null) {
	// PlatformUI.getWorkbench().getActiveWorkbenchWindow()
	// .getActivePage().closeEditors(editors, false);
	// }
	// }
}