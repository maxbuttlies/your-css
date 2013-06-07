package de.maxbuttlies.css.editor.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

public class CSSEditor implements EntryPoint {

	public void onModuleLoad() {

		RootPanel.get().add(new MainView());

	}
}
