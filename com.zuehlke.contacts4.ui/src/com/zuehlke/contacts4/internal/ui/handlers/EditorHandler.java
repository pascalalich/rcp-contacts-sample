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
	@Inject
	private IEventBroker eventBroker;

	protected Object getSelection() {
		return selectionService.getSelection();
	}

	protected void openEditor(Contact contact) {
		System.out.println("Open editor for contact " + contact.getName());

		// TODO better id for new objects
		String partId = String.format(
				"com.zuehlke.contacts4.internal.ui.editors.ContactEditor#%d",
				contact.getId());

		MInputPart part = (MInputPart) partService.findPart(partId);
		if (part == null) {
			part = MBasicFactory.INSTANCE.createInputPart();
			part.setElementId(partId);
			part.setContributionURI("bundleclass://com.zuehlke.contacts4.ui/com.zuehlke.contacts4.internal.ui.editors.ContactEditor");
			if (contact.getId() == null) {
				part.setInputURI(String.format(
						"resource:/customer/%d/contacts/new",
						contact.getCustomer()));
			} else {
				part.setInputURI(String.format(
						"resource:/customer/%d/contacts/%d",
						contact.getCustomer(), contact.getId()));
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

		// TODO better id for new objects
		String partId = String.format(
				"com.zuehlke.contacts4.internal.ui.editors.CustomerEditor#%d",
				customer.getId());

		MInputPart part = (MInputPart) partService.findPart(partId);
		if (part == null) {
			part = MBasicFactory.INSTANCE.createInputPart();
			part.setElementId(partId);
			part.setContributionURI("bundleclass://com.zuehlke.contacts4.ui/com.zuehlke.contacts4.internal.ui.editors.CustomerEditor");
			if (customer.getId() == null) {
				part.setInputURI("resource:/customer/new");
			} else {
				part.setInputURI(String.format("resource:/customer/%d",
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

	protected void sendDataChangedEvent() {
		eventBroker.send(EventConstants.DATA_CHANGED, null);
	}

	protected void closeEditor(Contact contact) {
		System.out.println("Closing editor for contact " + contact.getName());
		String partId = String.format(
				"com.zuehlke.contacts4.internal.ui.editors.ContactEditor#%d",
				contact.getId());

		MInputPart part = (MInputPart) partService.findPart(partId);
		if (part != null) {
			partService.hidePart(part, true);
		}
	}

	protected void closeEditor(Customer customer) {
		System.out.println("Closing editor for customer "
				+ customer.getNumber());
		String partId = String.format(
				"com.zuehlke.contacts4.internal.ui.editors.CustomerEditor#%d",
				customer.getId());

		MInputPart part = (MInputPart) partService.findPart(partId);
		if (part != null) {
			partService.hidePart(part, true);
		}
	}
}
