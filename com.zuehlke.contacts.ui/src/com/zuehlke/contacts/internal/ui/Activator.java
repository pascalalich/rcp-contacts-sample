package com.zuehlke.contacts.internal.ui;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceException;
import org.osgi.framework.ServiceReference;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.zuehlke.contacts.ui"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;

	private Map<String, ServiceReference<?>> serviceReferenceMap = new ConcurrentHashMap<String, ServiceReference<?>>();

	/**
	 * The constructor
	 */
	public Activator() {
	}

	/**
	 * {@inheritDoc}
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		registerImages();
		plugin = this;
	}

	private void registerImages() {
		getImageRegistry().put("customer",
				getImageDescriptor("/icons/customer.gif"));
		getImageRegistry().put("contact",
				getImageDescriptor("/icons/contact.gif"));
	}

	/**
	 * {@inheritDoc}
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		for (ServiceReference<?> serviceReference : serviceReferenceMap
				.values()) {
			getBundle().getBundleContext().ungetService(serviceReference);
		}
		serviceReferenceMap.clear();
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
	
	@SuppressWarnings("unchecked")
	public <T> T getService(Class<T> serviceClass) {
		String serviceName = serviceClass.getName();
		BundleContext bundleContext = getBundle().getBundleContext();
		ServiceReference<T> serviceReference = null;
		if (!serviceReferenceMap.containsKey(serviceName)) {
			serviceReference = (ServiceReference<T>) bundleContext
					.getServiceReference(serviceName);
			if (serviceReference != null) {
				serviceReferenceMap.put(serviceName, serviceReference);
			} else {
				throw new ServiceException("service is not available: "
						+ serviceClass.getSimpleName());
			}
		} else {
			serviceReference = (ServiceReference<T>) serviceReferenceMap
					.get(serviceName);
		}
		return bundleContext.getService(serviceReference);
	}

	/**
	 * Returns an image descriptor for the image file at the given plug-in
	 * relative path
	 * 
	 * @param path
	 *            the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
}
