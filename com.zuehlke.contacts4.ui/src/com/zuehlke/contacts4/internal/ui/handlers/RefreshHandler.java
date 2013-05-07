package com.zuehlke.contacts4.internal.ui.handlers;

import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.services.IServiceConstants;

import com.zuehlke.contacts4.internal.ui.events.IRefreshable;

public class RefreshHandler {

	@Execute
	public void execute(@Named(IServiceConstants.ACTIVE_PART) MPart activePart) {
		IRefreshable refreshable = (IRefreshable) activePart.getObject();
		refreshable.refresh();
	}

}
