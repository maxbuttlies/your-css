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

	interface EditorPanelUiBinder extends
			UiBinder<Widget, EditorPanel> {
	}

	@UiField
	TextArea textarea;

	@UiField
	HTMLPanel custom;

	String[] idClass = { ".", "#" };
	String[] attr = { "font-size", "line-height" };
	String[] breaks = { "{", "}" };
	String[] elements = { "p", "li" };
	String tab = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
	String maxSize = "line-height:60px;\nfont-size:50px;";
	private boolean ctrlPressed = false;

	private int fontSize = 20;

	private MainView parent;

	public EditorPanel(final MainView parent) {
		initWidget(uiBinder.createAndBindUi(this));
		this.parent = parent;
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

	public void setText(String text) {
		textarea.setText(text);
		sync();
	}

	private void sync() {
		sync(false);
	}

	private void sync(boolean insertTab) {

		custom.clear();
		String text = textarea.getText();
		if (text.contains("%maxSize%")) {
			textarea.setText(text);
		}

		parent.saveCSS(text);

		if (insertTab) {
			int index = textarea.getCursorPos();
			String pre = text.substring(0, index);
			String post = text.substring(index);

			text = pre + "\t" + post;
			textarea.setText(text);
		}

		text = text.replace("\n", "<br/>");
		text = replaceArray(attr, text, "#0000ff");
		text = replaceAfterArray(text);
		text = text.replace("\t", tab);
		custom.add(new HTMLPanel(text));
	}

	private String replaceArray(String[] arr, String content,
			String color) {
		for (String value : arr) {

			content = content.replace(value, "<span style=\"color:"
					+ color + ";\">" + value + "</span>");

		}

		return content;
	}

	private String replaceAfterArray(String content) {
		String[] v = content.replace("<br>", " ")
				.replace("{", " ").split(" ");
		for (String value : v) {

			for (String v1 : idClass) {

				if (value.indexOf(v1) == 0) {
					content = content.replace(value,
							"<span style=\"color:red\">" + value + "</span>");
				}
			}
		}
		return content;
	}

	private void setFontSize(int fontSize) {
		this.fontSize = fontSize;
		String style = "font-size:" + fontSize + "px;";
		custom.getElement().setAttribute("style", style);

		textarea.getElement().setAttribute("style", style);
	}
}
