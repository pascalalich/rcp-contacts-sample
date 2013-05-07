package com.zuehlke.contacts4.internal.ui.handlers;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MBasicFactory;
import org.eclipse.e4.ui.model.application.ui.basic.MInputPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;

@SuppressWarnings("restriction")
public class EditHandler {
	@Execute
	public void execute(MApplication application, EModelService modelService,
			EPartService partService, ESelectionService selectionService) {
		// Object selection = selectionService.getSelection();

		MPartStack stack = (MPartStack) modelService.find(
				"com.zuehlke.contacts4.internal.ui.editors.customer", application);

		MInputPart part = MBasicFactory.INSTANCE.createInputPart();
		// Pointing to the contributing class
		part.setContributionURI("bundleclass://de.vogella.rcp.e4.todo/com.zuehlke.contacts4.internal.ui.editors.CustomerEditor");
		// part.setInputURI(input.getInputURI());
		part.setIconURI("platform:/plugin/de.vogella.rcp.e4.todo/icons/sample.gif");
		// part.setLabel(input.getName());
		// part.setTooltip(input.getTooltip());
		part.setCloseable(true);
		stack.getChildren().add(part);
		partService.showPart(part, PartState.ACTIVATE);
	}

}