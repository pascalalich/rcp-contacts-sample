package com.zuehlke.contacts.ui.editor;

import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;

public class BasicFormPage<T> extends FormPage {

	private boolean dirty;
	private ModifyListener listener;

	public BasicFormPage(FormEditor editor, String id, String title) {
		super(editor, id, title);
	}

	@SuppressWarnings("unchecked")
	protected final T getObject() {
		return ((BasicEditorInput<T>) getEditorInput()).getObject();
	}

	@Override
	public final boolean isDirty() {
		return dirty;
	}

	protected final void setDirty(boolean dirty) {
		if (this.dirty != dirty) {
			this.dirty = dirty;
			getEditor().editorDirtyStateChanged();
		}
	}

	protected final ModifyListener getDirtyListener() {
		if (listener == null) {
			listener = new ModifyListener() {
				@Override
				public void modifyText(ModifyEvent e) {
					setDirty(true);
				}
			};
		}
		return listener;
	}
}
