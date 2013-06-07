package de.maxbuttlies.css.editor.client.rpc;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface EditorServiceAsync {

	void getCSS(String domain, AsyncCallback<String> callback);

	void getDomains(AsyncCallback<List<String>> callback);

	void saveCSS(String domain, String css,
			AsyncCallback<Void> callback);

}
