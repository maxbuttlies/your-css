package de.maxbuttlies.css.editor.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class DomainPanel extends Composite {

	private static DomainPanelUiBinder uiBinder = GWT
			.create(DomainPanelUiBinder.class);

	interface DomainPanelUiBinder extends UiBinder<Widget, DomainPanel> {
	}

	@UiField
	Image image;

	@UiField
	Label label;

	private static final String IMAGEURL = "https://plus.google.com/_/favicon?domain=";

	private String domain;

	public DomainPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public DomainPanel(String domain) {
		this();
		this.setDomain(domain);

	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
		image.setUrl(IMAGEURL + domain);
		label.setText(domain);
	}

}
