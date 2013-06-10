package de.maxbuttlies.css.editor.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.dom.client.MouseWheelEvent;
import com.google.gwt.event.dom.client.MouseWheelHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;

public class EditorPanel extends Composite {

	private static EditorPanelUiBinder uiBinder = GWT
			.create(EditorPanelUiBinder.class);

	interface EditorPanelUiBinder extends UiBinder<Widget, EditorPanel> {
	}

	@UiField
	TextArea textarea;

	@UiField
	HTMLPanel custom;

	private boolean ctrlPressed = false;

	private int fontSize = 20;

	private ClientController controller;

	public EditorPanel(ClientController controller) {
		initWidget(uiBinder.createAndBindUi(this));
		this.controller = controller;
		init();
	}

	public void setText(String text) {
		textarea.setText(text);
		sync();
	}

	private void init() {
		textarea.addMouseWheelHandler(new MouseWheelHandler() {

			@Override
			public void onMouseWheel(MouseWheelEvent event) {
				if (ctrlPressed) {
					event.preventDefault();
					event.stopPropagation();
					if (event.isNorth()) {
						setFontSize(fontSize + 1);
					} else {
						setFontSize(fontSize - 1);

					}
				}
			}
		});
		textarea.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				sync();
			}
		});
		textarea.addKeyDownHandler(new KeyDownHandler() {

			@Override
			public void onKeyDown(KeyDownEvent event) {

				if (event.getNativeKeyCode() == KeyCodes.KEY_TAB) {
					event.preventDefault();
					event.stopPropagation();
					sync(true);

				} else {
					if (event.getNativeKeyCode() == KeyCodes.KEY_ALT) {
						ctrlPressed = true;
					}
					sync();
				}

			}

		});
		textarea.addKeyUpHandler(new KeyUpHandler() {

			@Override
			public void onKeyUp(KeyUpEvent event) {
				ctrlPressed = false;
				sync();
			}

		});

	}

	private void sync() {
		sync(false);
	}

	private void sync(boolean insertTab) {
		custom.clear();
		String text = textarea.getText();
		if (insertTab) {
			int index = textarea.getCursorPos();
			String pre = text.substring(0, index);
			String post = text.substring(index);

			text = pre + "\t" + post;
			textarea.setText(text);
		}
		text = EditorHelper.replacePlaceHolder(text);
		textarea.setText(text);
		controller.saveCSS(text);
		text = EditorHelper.formatText(text);
		custom.add(new HTMLPanel(text));
	}

	private void setFontSize(int fontSize) {
		this.fontSize = fontSize;
		String style = "font-size:" + fontSize + "px;";
		custom.getElement().setAttribute("style", style);

		textarea.getElement().setAttribute("style", style);
	}
}
