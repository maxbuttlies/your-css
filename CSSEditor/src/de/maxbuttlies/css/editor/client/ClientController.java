package de.maxbuttlies.css.editor.client;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import de.maxbuttlies.css.editor.client.rpc.RPC;

public class ClientController {
	private EditorPanel editor = null;;
	private LeftBarPanel leftBar = null;
	private String domain = "";

	public ClientController() {
		super();
		editor = new EditorPanel(this);
		leftBar = new LeftBarPanel(this);
	}

	public EditorPanel getEditor() {
		return editor;
	}

	public LeftBarPanel getLeftBar() {
		return leftBar;
	}

	public void setEditor(String domain) {
		this.domain = domain;

		RPC.EDITOR.getCSS(domain, new AsyncCallback<String>() {

			@Override
			public void onSuccess(String result) {
				editor.setText(result);
			}

			@Override
			public void onFailure(Throwable caught) {
				editor.setText(caught.getMessage());

			}
		});

	}

	public void saveNewCSS(final String domain, String css) {
		RPC.EDITOR.saveCSS(domain, css, new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				editor.setText("nö");
			}

			@Override
			public void onSuccess(Void result) {
				loadDomains();
				setEditor(domain);
			}
		});

	}

	public void saveCSS(String css) {
		RPC.EDITOR.saveCSS(domain, css, new AsyncCallback<Void>() {

			@Override
			public void onSuccess(Void result) {
				// nichts erstmal
			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				Window.alert(caught.getLocalizedMessage());
			}
		});
	}

	public void loadDomains() {
		RPC.EDITOR.getDomains(new AsyncCallback<List<String>>() {

			@Override
			public void onSuccess(List<String> res) {
				leftBar.domains.clear();
				for (final String domain : res) {
					DomainPanel d = new DomainPanel(domain);
					d.addHandler(new ClickHandler() {

						@Override
						public void onClick(ClickEvent event) {
							setEditor(domain);
						}
					}, ClickEvent.getType());
					d.sinkEvents(Event.ONCLICK);
					leftBar.domains.add(d);

				}

			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}
		});

	}

}
