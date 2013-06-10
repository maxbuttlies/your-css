package de.maxbuttlies.css.editor.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class MainView extends Composite {

	private static MainViewUiBinder uiBinder = GWT
			.create(MainViewUiBinder.class);

	interface MainViewUiBinder extends UiBinder<Widget, MainView> {
	}

	@UiField
	EditorPanel editor;

	private ClientController controller;

	public MainView() {
		controller = new ClientController();
		initWidget(uiBinder.createAndBindUi(this));

	}

	@UiFactory
	public LeftBarPanel getLeftBarPanel() {
		return controller.getLeftBar();
	}

	@UiFactory
	public EditorPanel geteEditorPanel() {
		return controller.getEditor();
	}

}
