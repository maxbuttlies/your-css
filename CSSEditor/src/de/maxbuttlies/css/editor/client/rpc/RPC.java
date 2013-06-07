package de.maxbuttlies.css.editor.client.rpc;

import com.google.gwt.core.client.GWT;

public class RPC {
	public static final EditorServiceAsync EDITOR = GWT
			.create(EditorService.class);
}
