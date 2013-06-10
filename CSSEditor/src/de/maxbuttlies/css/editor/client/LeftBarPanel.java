package de.maxbuttlies.css.editor.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

public class LeftBarPanel extends Composite {

	private static LeftBarPanelUiBinder uiBinder = GWT
			.create(LeftBarPanelUiBinder.class);

	interface LeftBarPanelUiBinder extends UiBinder<Widget, LeftBarPanel> {
	}

	@UiField
	HTMLPanel domains;
	private ClientController controller;

	public LeftBarPanel(ClientController controller) {
		initWidget(uiBinder.createAndBindUi(this));
		this.controller = controller;
		controller.loadDomains();
	}

	@UiHandler("newDomain")
	public void onClickNew(ClickEvent e) {
		String name = Window.prompt("Bitte gebe die Domain ein", "");
		controller.saveNewCSS(name, "");

	}

}
