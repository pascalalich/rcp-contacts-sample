package com.zuehlke.contacts4.ui.intent;

/**
 * <p>
 * Callback for an {@link IContactIntent}. An intent is a certain action that
 * can be called from the designated fields in the contact editor.
 * </p>
 * 
 * <p>
 * Each intent must be registered via the extension point TODO in order to be
 * reachable.
 * </p>
 */
public interface IContactIntent {

	/**
	 * Triggers the intent.
	 * 
	 * @param context
	 *            the contact attribute the intent relates to.
	 * @param value
	 */
	void call(ContactIntentContext context, String value);

}
