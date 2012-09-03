package com.zuehlke.contacts.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.core.runtime.Path;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.zuehlke.contacts.service.dto.BasicDto;

public abstract class BasicLocalService<T extends BasicDto> {
	/** The private map of contacts. */
	private final Map<Long, T> data = new ConcurrentHashMap<Long, T>();

	/** The sequence. **/
	private long sequence = 0;

	public BasicLocalService() {
		for (T t : restore()) {
			data.put(getId(t), t);
			sequence = Math.max(sequence, getId(t) + 1);
		}
		// TODO remove when we have UI to manipulate and persist data
		persist();
	}

	public void update(T t) {
		if (data.containsKey(getId(t))) {
			copy(t, data.get(getId(t)));
			persist();
		} else {
			throw new RuntimeException("object with id " + getId(t)
					+ " does not exist!");
		}
	}

	public Set<T> findAll() {
		Set<T> matches = new HashSet<T>();
		for (T t : data.values()) {
			matches.add(clone(t));
		}
		return matches;
	}

	public T findById(Long id) {
		if (data.containsKey(id)) {
			return clone(data.get(id));
		}
		return null;
	}

	public Collection<T> findByExample(T t) {
		return null;
	}

	public void delete(Long id) {
		data.remove(id);
		persist();
	}

	public synchronized T create(T t) {
		T clone = clone(t);
		long id = sequence++;
		setId(clone, id);
		data.put(id, clone);
		persist();
		return clone(clone);
	}

	protected Map<Long, T> getData() {
		return data;
	}

	protected abstract T copy(T source, T target);

	protected abstract T clone(T t);

	protected abstract Long getId(T t);

	protected abstract void setId(T t, Long id);

	protected abstract Collection<T> getInitialData();

	private synchronized void persist() {
		File file = Activator.getDefault().getStateLocation()
				.append(new Path(getDataFileName())).toFile();
		try {
			new XStream(new DomDriver("UTF-8")).toXML(
					new HashSet<T>(data.values()), new FileOutputStream(file));
		} catch (FileNotFoundException exception) {
			exception.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private synchronized Collection<T> restore() {
		Collection<T> data = new HashSet<T>();
		File file = Activator.getDefault().getStateLocation()
				.append(new Path(getDataFileName())).toFile();
		if (file.exists()) {
			Collection<T> xmlData = (Collection<T>) new XStream(new DomDriver(
					"UTF-8")).fromXML(file);
			data.addAll(xmlData);
		} else {
			data.addAll(getInitialData());
		}
		return data;
	}

	@SuppressWarnings("unchecked")
	private String getDataFileName() {
		Class<T> clazz = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
		return clazz.getSimpleName().toLowerCase() + ".xml";
	}
}
