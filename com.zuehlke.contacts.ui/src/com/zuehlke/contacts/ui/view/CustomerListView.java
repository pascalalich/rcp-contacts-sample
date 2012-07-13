package com.zuehlke.contacts.ui.view;

import java.util.Collection;
import java.util.HashSet;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.part.ViewPart;

import com.zuehlke.contacts.service.CustomerService;
import com.zuehlke.contacts.service.dto.Customer;
import com.zuehlke.contacts.ui.Activator;
import com.zuehlke.contacts.ui.provider.ContactTreeLabelProvider;
import com.zuehlke.contacts.ui.provider.ContactTreeContentProvider;

public class CustomerListView extends ViewPart {

	public static final String ID = "com.zuehlke.contacts.ui.view.ContactListView"; //$NON-NLS-1$
	private TreeViewer treeViewerContacts;

	@Override
	public void createPartControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new FillLayout(SWT.HORIZONTAL));
		treeViewerContacts = new TreeViewer(container, SWT.BORDER | SWT.FULL_SELECTION);
		Tree tree = treeViewerContacts.getTree();
		tree.setLinesVisible(true);

		// set providers
		treeViewerContacts.setLabelProvider(new ContactTreeLabelProvider());
		treeViewerContacts.setContentProvider(new ContactTreeContentProvider());
		// register menus & selection provider
		registerContextMenu();
		getViewSite().setSelectionProvider(treeViewerContacts);
		// get initial data
		refreshUI();
	}

	private void refreshUI() {
		final Collection<Customer> newInput = new HashSet<Customer>();
		// create a job to get the new data...
		Job customerLoadJob = new Job("Load Customer") {

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				monitor.beginTask("Requesting all customers",
						IProgressMonitor.UNKNOWN);
				CustomerService customerService = Activator.getDefault()
						.getService(CustomerService.class);
				if (customerService != null) {
					newInput.addAll(customerService.findAll());
					return Status.OK_STATUS;
				}
				return new Status(IStatus.ERROR, Activator.PLUGIN_ID,
						"Could not load customers, the service is currently not available!");
			}
		};
		// .. update the UI on successful retrieval
		customerLoadJob.addJobChangeListener(new JobChangeAdapter() {
			@Override
			public void done(IJobChangeEvent event) {
				if (event.getResult().isOK()) {
					getViewSite().getShell().getDisplay()
							.asyncExec(new Runnable() {

								@Override
								public void run() {
									treeViewerContacts.setInput(newInput);
								}
							});
				}
			}
		});
		// schedule job
		customerLoadJob.setUser(true);
		customerLoadJob.schedule();
	}

	private void registerContextMenu() {
		MenuManager contextMenuManager = new MenuManager();
		contextMenuManager.add(new GroupMarker(
				IWorkbenchActionConstants.MB_ADDITIONS));
		Menu contextMenu = contextMenuManager
				.createContextMenu(treeViewerContacts.getControl());
		treeViewerContacts.getTree().setMenu(contextMenu);
		getViewSite().registerContextMenu(contextMenuManager,
				treeViewerContacts);
	}

	@Override
	public void setFocus() {
		treeViewerContacts.getControl().setFocus();
	}

}
