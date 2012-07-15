package com.zuehlke.contacts.service.dummy;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.zuehlke.contacts.service.ContactService;
import com.zuehlke.contacts.service.CustomerService;
import com.zuehlke.contacts.service.dummy.internal.DummyContactService;
import com.zuehlke.contacts.service.dummy.internal.DummyCustomerService;

public class Activator implements BundleActivator {

	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext
	 * )
	 */
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		registerDummyServices();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}

	private void registerDummyServices() {
		context.registerService(CustomerService.class,
				new DummyCustomerService(), null);
		context.registerService(ContactService.class,
				new DummyContactService(), null);
	}

}
