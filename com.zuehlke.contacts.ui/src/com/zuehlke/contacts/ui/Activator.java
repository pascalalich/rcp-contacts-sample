package com.zuehlke.contacts.ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import com.zuehlke.contacts.service.ContactService;
import com.zuehlke.contacts.service.CustomerService;
import com.zuehlke.contacts.service.dto.Contact;
import com.zuehlke.contacts.service.dto.Customer;

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
	 * )
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		registerDummyServices();
		plugin = this;
	}

	private void registerDummyServices() {
		getBundle().getBundleContext().registerService(CustomerService.class,
				new CustomerService() {

					@Override
					public void update(Customer customer) {
						// TODO Auto-generated method stub

					}

					@Override
					public Customer findById(Long id) {
						// TODO Auto-generated method stub
						return null;
					}

					@Override
					public Collection<Customer> findByExample(Customer example) {
						// TODO Auto-generated method stub
						return null;
					}

					@Override
					public Collection<Customer> findAll() {
						Customer customer_one = new Customer();
						customer_one.setId(1l);
						customer_one.setName("Reichert");
						customer_one.setNumber("REI_2012_0442");
						customer_one.setMainContact(1l);
						Customer customer_two = new Customer();
						customer_two.setId(2l);
						customer_two.setName("Alich");
						customer_two.setNumber("ALI_2012_0441");
						customer_two.setMainContact(2l);
						Collection<Customer> customers = new ArrayList<Customer>();
						customers.add(customer_two);
						customers.add(customer_one);
						return customers;
					}

					@Override
					public void delete(Long id) {
						// TODO Auto-generated method stub

					}

					@Override
					public Customer create(Customer customer) {
						// TODO Auto-generated method stub
						return null;
					}
				}, null);
		getBundle().getBundleContext().registerService(ContactService.class,
				new ContactService() {

					@Override
					public void update(Contact contact) {
						// TODO Auto-generated method stub

					}

					@Override
					public Contact findById(Long id) {
						// TODO Auto-generated method stub
						return null;
					}

					@Override
					public Collection<Contact> findByExample(Contact example) {
						// TODO Auto-generated method stub
						return null;
					}

					@Override
					public Collection<Contact> findByCustomer(Long customerId) {
						Collection<Contact> contacts = new ArrayList<Contact>();
						if (customerId.equals(1l)) {
							Contact contact = new Contact();
							contact.setId(1l);
							contact.setCustomer(1l);
							contact.setGiven("Stefan");
							contact.setName("Reichert");
							contact.setEmail("srt@zuehlke.com");
							contacts.add(contact);
						} else if (customerId.equals(2l)) {
							Contact contact = new Contact();
							contact.setId(2l);
							contact.setCustomer(2l);
							contact.setGiven("Pascal");
							contact.setName("Alich");
							contact.setEmail("alp@zuehlke.com");
							contacts.add(contact);
						}
						return contacts;
					}

					@Override
					public void delete(Long id) {
						// TODO Auto-generated method stub

					}

					@Override
					public Contact create(Contact contact) {
						// TODO Auto-generated method stub
						return null;
					}
				}, null);
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
		if (!serviceReferenceMap.containsKey(serviceName)) {
			ServiceReference<T> serviceReference = getBundle()
					.getBundleContext().getServiceReference(serviceClass);
			if (serviceReference != null) {
				serviceReferenceMap.put(serviceName, serviceReference);
			}
		}
		return (T) getBundle().getBundleContext().getService(
				serviceReferenceMap.get(serviceName));
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
