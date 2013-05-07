package com.zuehlke.contacts4.internal.ui.handlers;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MBasicFactory;
import org.eclipse.e4.ui.model.application.ui.basic.MInputPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;

import com.zuehlke.contacts.service.dto.Customer;

@SuppressWarnings("restriction")
public class EditHandler {
	@Execute
	public void execute(MApplication application, EModelService modelService,
			ESelectionService selectionService, EPartService partService) {
		Customer selection = (Customer) selectionService.getSelection();

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

}