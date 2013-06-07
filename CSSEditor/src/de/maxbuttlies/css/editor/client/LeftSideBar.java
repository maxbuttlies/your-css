package de.maxbuttlies.css.editor.client;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

import de.maxbuttlies.css.editor.client.rpc.RPC;

public class LeftSideBar extends Composite {

	private static LeftSideBarUiBinder uiBinder = GWT
			.create(LeftSideBarUiBinder.class);

	interface LeftSideBarUiBinder extends
			UiBinder<Widget, LeftSideBar> {
	}

	@UiField
	HTMLPanel domains;
	private MainView parent;

	public LeftSideBar(final MainView parent) {
		initWidget(uiBinder.createAndBindUi(this));
		this.parent = parent;
		RPC.EDITOR.getDomains(new AsyncCallback<List<String>>() {

			@Override
			public void onSuccess(List<String> res) {
				for (final String domain : res) {
					DomainPanel d = new DomainPanel(domain);
					d.addHandler(new ClickHandler() {

						@Override
						public void onClick(ClickEvent event) {
							parent.setEditor(domain);
						}
					}, ClickEvent.getType());
					d.sinkEvents(Event.ONCLICK);
					domains.add(d);

				}

			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}
		});

	}

	@UiHandler("new")
	public void onClickNew(ClickEvent e) {
		String name = Window.prompt("Bitte gebe die Domain ein",
				"");
		parent.setEditor(name);
		parent.saveCSS("");
	}
}
