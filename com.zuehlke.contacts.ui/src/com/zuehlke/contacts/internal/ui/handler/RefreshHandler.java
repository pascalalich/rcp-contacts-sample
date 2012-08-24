package com.zuehlke.contacts.internal.ui.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.handlers.HandlerUtil;

import com.zuehlke.contacts.internal.ui.view.IRefreshable;

public class RefreshHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchPart activePart = HandlerUtil.getActivePart(event);
		if (activePart != null) {
			IRefreshable adapter = (IRefreshable) activePart
					.getAdapter(IRefreshable.class);
			if (adapter != null) {
				adapter.refresh();
			}
		}
		return null;
	}

}
