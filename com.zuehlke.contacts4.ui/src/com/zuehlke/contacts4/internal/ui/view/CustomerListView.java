package com.zuehlke.contacts4.internal.ui.view;

import java.util.Collection;
import java.util.HashSet;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.e4.ui.workbench.swt.modeling.EMenuService;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Tree;

import com.zuehlke.contacts.service.CustomerService;
import com.zuehlke.contacts.service.dto.Customer;
import com.zuehlke.contacts4.internal.ui.Activator;
import com.zuehlke.contacts4.internal.ui.provider.ContactTreeContentProvider;
import com.zuehlke.contacts4.internal.ui.provider.ContactTreeLabelProvider;

public class CustomerListView {

	public static final String ID = "com.zuehlke.contacts.internal.ui.view.ContactListView"; //$NON-NLS-1$
	private TreeViewer treeViewerContacts;

	@Inject
	private Display display;

	@Inject
	private EMenuService menuService;

	@Inject
	private ESelectionService selectionService;

	@PostConstruct
	public void createPartControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new FillLayout(SWT.HORIZONTAL));
		treeViewerContacts = new TreeViewer(container, SWT.BORDER
				| SWT.FULL_SELECTION);
		Tree tree = treeViewerContacts.getTree();
		tree.setLinesVisible(false);

		// set providers
		final ContactTreeLabelProvider labelProvider = new ContactTreeLabelProvider();
		treeViewerContacts.setLabelProvider(labelProvider);
		treeViewerContacts.setSorter(new ViewerSorter() {
			@Override
			public int compare(Viewer viewer, Object e1, Object e2) {
				return labelProvider.getText(e1).compareTo(
						labelProvider.getText(e2));
			}
		});
		treeViewerContacts.setContentProvider(new ContactTreeContentProvider());
		addSelectionListener();
		// TODO open editor programmatically
		// treeViewerContacts.addDoubleClickListener(new IDoubleClickListener()
		// {
		// @Override
		// public void doubleClick(DoubleClickEvent event) {
		// IHandlerService handlerService = (IHandlerService) getViewSite()
		// .getService(IHandlerService.class);
		// try {
		// handlerService.executeCommand(
		// "com.zuehlke.contacts.ui.edit", null);
		// } catch (CommandException e) {
		// // TODO error handling
		// throw new RuntimeException("Unable to edit customer.", e);
		// }
		// }
		// });
		// TODO button to create new customer/contact
		// register menus & selection provider
		registerContextMenu();
		// getViewSite().setSelectionProvider(treeViewerContacts);
		// get initial data
		refreshUI();
	}

	private void addSelectionListener() {
		treeViewerContacts
				.addSelectionChangedListener(new ISelectionChangedListener() {
					@Override
					public void selectionChanged(SelectionChangedEvent event) {
						IStructuredSelection selection = (IStructuredSelection) treeViewerContacts
								.getSelection();
						selectionService.setSelection(selection
								.getFirstElement());
					}
				});

	}

	// TODO how to make it refreshable?
	// @SuppressWarnings("rawtypes")
	// @Override
	// public Object getAdapter(Class adapter) {
	// if (adapter.equals(IRefreshable.class)) {
	// return new IRefreshable() {
	//
	// @Override
	// public void refresh() {
	// refreshUI();
	// }
	// };
	// }
	// return super.getAdapter(adapter);
	// }

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
					display.asyncExec(new Runnable() {
						@Override
						public void run() {
							// save state
							ISelection selection = treeViewerContacts
									.getSelection();
							Object[] expandedElements = treeViewerContacts
									.getExpandedElements();
							treeViewerContacts.setInput(newInput);
							treeViewerContacts
									.setExpandedElements(expandedElements);
							treeViewerContacts.setSelection(selection);
						}
					});
				}
			}
		});
		// schedule job
		customerLoadJob.setUser(true);
		customerLoadJob.schedule();
	}

	// TODO register context menu the eclipse 4 way
	private void registerContextMenu() {
		// MenuManager contextMenuManager = new MenuManager();
		// contextMenuManager.add(new GroupMarker(
		// IWorkbenchActionConstants.MB_ADDITIONS));
		// Menu contextMenu = contextMenuManager
		// .createContextMenu(treeViewerContacts.getControl());
		// treeViewerContacts.getTree().setMenu(contextMenu);
		// getViewSite().registerContextMenu(contextMenuManager,
		// treeViewerContacts);

		menuService.registerContextMenu(treeViewerContacts.getTree(),
				"popup:com.zuehlke.contacts.ui.customerlist");
	}

	@Focus
	private void setFocus() {
		treeViewerContacts.getControl().setFocus();
	}

}
