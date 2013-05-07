package com.zuehlke.contacts4.ui.addresscheck.maps;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.StatusLineManager;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.browser.BrowserViewer;
import org.eclipse.ui.internal.browser.IBrowserViewerContainer;
import org.eclipse.ui.services.IServiceLocator;

@SuppressWarnings("restriction")
public class BrowserDialog extends Dialog implements IBrowserViewerContainer {

	private final int popupWidth;

	private final int popupHeight;

	private final String title;

	private final String url;

	private static final int DEFAULT_BROWSER_STYLE = SWT.NONE;

	private BrowserViewer viewer;

	private StatusLineManager statusLineManager = new StatusLineManager();
	private IActionBars actionBars = new StatusLineActionBars();

	public BrowserDialog(int popupWidth, int popupHeight, String title,
			String url) {
		super(PlatformUI.getWorkbench().getModalDialogShellProvider());
		this.popupWidth = popupWidth;
		this.popupHeight = popupHeight;
		this.title = title;
		this.url = url;
		setShellStyle(getShellStyle() | SWT.APPLICATION_MODAL);
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setSize(popupWidth, popupHeight);
		newShell.setText(title);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		GridLayout compositeLayout = new GridLayout();
		compositeLayout.marginHeight = 0;
		compositeLayout.marginBottom = 2;
		compositeLayout.marginWidth = 0;
		compositeLayout.verticalSpacing = 0;
		compositeLayout.horizontalSpacing = 0;
		composite.setLayout(compositeLayout);
		viewer = new BrowserViewer(composite, DEFAULT_BROWSER_STYLE);
		viewer.setContainer(this);
		viewer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		viewer.setURL(url);
		Control statusLine = statusLineManager.createControl(parent);
		statusLine.setEnabled(false);
		statusLine.setVisible(false);
		statusLine.setSize(0, 0);
		return composite;
	}

	@Override
	public IActionBars getActionBars() {
		return actionBars;
	}

	@Override
	public void openInExternalBrowser(String url) {
		throw new IllegalStateException(
				"External browsers are not supported yet.");
	}

	// Unfortunately the BrowserViewer requires an IActionBars implementation,
	// but only uses IStatusLineManager
	// we provide one implementation, but do not show the status bar
	private class StatusLineActionBars implements IActionBars {

		@Override
		public IStatusLineManager getStatusLineManager() {
			return statusLineManager;
		}

		@Override
		public void clearGlobalActionHandlers() {
		}

		@Override
		public IAction getGlobalActionHandler(String actionId) {
			return null;
		}

		@Override
		public IMenuManager getMenuManager() {
			return null;
		}

		@Override
		public IServiceLocator getServiceLocator() {
			return null;
		}

		@Override
		public IToolBarManager getToolBarManager() {
			return null;
		}

		@Override
		public void setGlobalActionHandler(String actionId, IAction handler) {
		}

		@Override
		public void updateActionBars() {
		}

	}
}
