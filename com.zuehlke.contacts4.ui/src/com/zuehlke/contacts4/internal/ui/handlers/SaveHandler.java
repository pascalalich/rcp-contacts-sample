package com.zuehlke.contacts4.internal.ui.handlers;

import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.modeling.EPartService;

@SuppressWarnings("restriction")
public class SaveHandler extends EditorHandler {

	@Inject
	private EPartService partService;
	
	@CanExecute
	boolean canExecute(
			@Named(IServiceConstants.ACTIVE_PART) MDirtyable dirtyable) {
		return dirtyable == null ? false : dirtyable.isDirty();
	}

	@Execute
	void execute(@Named(IServiceConstants.ACTIVE_PART) MPart part) {
		partService.savePart(part, false);
		sendDataChangedEvent();
	}

}