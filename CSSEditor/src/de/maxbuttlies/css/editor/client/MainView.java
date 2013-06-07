package de.maxbuttlies.css.editor.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import de.maxbuttlies.css.editor.client.rpc.RPC;

public class MainView extends Composite {

	private static MainViewUiBinder uiBinder = GWT
			.create(MainViewUiBinder.class);

	interface MainViewUiBinder extends
			UiBinder<Widget, MainView> {
	}

	@UiField
	EditorPanel editor;

	private String domain = "";

	public MainView() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiFactory
	public LeftSideBar getLeftSideBar() {
		return new LeftSideBar(this);
	}

	@UiFactory
	public EditorPanel geteEditorPanel() {
		return new EditorPanel(this);
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

	public void saveCSS(String css) {
		RPC.EDITOR.saveCSS(domain, css,
				new AsyncCallback<Void>() {

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

}
