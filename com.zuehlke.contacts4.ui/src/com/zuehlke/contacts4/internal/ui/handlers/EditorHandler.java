package com.zuehlke.contacts4.internal.ui.handlers;

import javax.inject.Inject;

import org.eclipse.e4.core.services.events.IEventBroker;
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
import com.zuehlke.contacts4.internal.ui.events.EventConstants;

public abstract class EditorHandler {

	@Inject
	private MApplication application;
	@Inject
	private EModelService modelService;
	@Inject
	private EPartService partService;
	@Inject
	private ESelectionService selectionService;
	@Inject
	private IEventBroker eventBroker;

	protected Object getSelection() {
		return selectionService.getSelection();
	}

	protected void openEditor(Contact contact) {
		System.out.println("Open editor for contact " + contact.getName());
		// TODO open editor the Eclipse 4 way
		// ContactEditorInput input = new ContactEditorInput(contact);
		// openEditor("com.zuehlke.contacts.ui.editor.contact", input);
	}

	protected void openEditor(Customer customer) {
		System.out.println("Open editor for customer " + customer.getNumber());
		MPartStack stack = (MPartStack) modelService.find(
				"com.zuehlke.contacts4.right", application);

		MInputPart part = MBasicFactory.INSTANCE.createInputPart();
		// Pointing to the contributing class
		part.setContributionURI("bundleclass://com.zuehlke.contacts4.ui/com.zuehlke.contacts4.internal.ui.editors.CustomerEditor");
		part.setInputURI("resource:/customer#1");
		part.setIconURI("platform:/plugin/com.zuehlke.contacts4.ui/icons/customer.gif");
		// part.setLabel(input.getName());
		// part.setTooltip(input.getTooltip());
		part.setCloseable(true);
		stack.getChildren().add(part);
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

	protected void sendDataChangedEvent() {
		eventBroker.send(EventConstants.DATA_CHANGED, null);
	}

	protected void closeEditor(Contact contact) {
		System.out.println("Closing editor for contact " + contact.getName());
		// ContactEditorInput input = new ContactEditorInput(contact);
		// closeEditor("com.zuehlke.contacts.ui.editor.contact", input);
	}

	protected void closeEditor(Customer customer) {
		System.out.println("Closing editor for customer "
				+ customer.getNumber());
		// CustomerEditorInput input = new CustomerEditorInput(customer);
		// closeEditor("com.zuehlke.contacts.ui.editor.customer", input);
	}

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
