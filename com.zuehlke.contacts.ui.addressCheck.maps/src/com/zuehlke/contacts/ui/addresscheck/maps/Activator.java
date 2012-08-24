package com.zuehlke.contacts.ui.addresscheck.maps;

import org.eclipse.core.internal.registry.osgi.OSGIUtils;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.zuehlke.contacts.ui.addressCheck.maps"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;

	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
	 * )
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		registerServices();
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
	 * )
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	/**
	 * Registers OSGi services.
	 */
	private void registerServices() {
		// Defer until workbench is running
		Job job = new Job("Register services for "
				+ getBundle().getSymbolicName()) {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				BundleContext bundleContext = getBundle().getBundleContext();
				while (OSGIUtils.getDefault()
						.getBundle("com.zuehlke.contacts.ui").getState() != Bundle.ACTIVE) {
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						throw new IllegalStateException(
								"Interrupted while waiting for workbench.", e);
					}
				}
				// fully qualified to defer startup of UI bundle
				bundleContext
						.registerService(
								com.zuehlke.contacts.ui.addresscheck.IAddressCheck.class,
								new MapsAddressCheck(), null);

				return Status.OK_STATUS;
			}
		};
		job.setSystem(true);
		job.schedule();
	}
}
