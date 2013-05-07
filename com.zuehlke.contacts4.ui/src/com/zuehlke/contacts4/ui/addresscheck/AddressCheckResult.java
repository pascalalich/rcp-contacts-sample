package com.zuehlke.contacts4.ui.addresscheck;

/**
 * Result of an address check.
 */
public class AddressCheckResult {

	private final AddressCheckStatus status;

	private final String message;

	// static factory methods
	private AddressCheckResult(AddressCheckStatus status, String message) {
		this.status = status;
		this.message = message;
	}

	public AddressCheckStatus getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

	/**
	 * @return An {@link AddressCheckStatus#OK} result.
	 */
	public static AddressCheckResult ok() {
		return new AddressCheckResult(AddressCheckStatus.OK, null);
	}

	/**
	 * @param message
	 *            the error message
	 * @return An {@link AddressCheckStatus#ERROR} result with given message.
	 */
	public static AddressCheckResult error(String message) {
		return new AddressCheckResult(AddressCheckStatus.ERROR, message);
	}

}
