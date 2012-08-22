package com.zuehlke.contacts.internal.ui;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;

public class ErrorDialogHelper {

	private static final String LINE_SEPARATOR = System
			.getProperty("line.separator");

	public static MultiStatus getErrorMultiStatus(Throwable throwable,
			String bundleSymbolicName, int severity) {

		// stacktrace as String
		StringWriter traceWriter = new StringWriter();
		throwable.printStackTrace(new PrintWriter(traceWriter));
		String stacktrace = traceWriter.toString();

		// each line as child status
		List<Status> childStatuses = new ArrayList<Status>();
		for (String line : stacktrace.split(LINE_SEPARATOR)) {
			childStatuses.add(new Status(severity, bundleSymbolicName, line));
		}

		return new MultiStatus(bundleSymbolicName, severity,
				childStatuses.toArray(new Status[] {}), throwable.getMessage(),
				null);
	}

}
