package de.maxbuttlies.css.editor.server;

import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.maxbuttlies.css.editor.client.rpc.EditorService;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class EditorServiceImpl extends RemoteServiceServlet
		implements EditorService {

	@Override
	public String getCSS(String domain) {
		return DBConnector.getInstance().getCSS(domain);
	}

	@Override
	public List<String> getDomains() {
		return DBConnector.getInstance().getDomains();
	}

	@Override
	public void saveCSS(String domain, String css)
			throws Exception {
		System.out.println("Domain:" + domain);
		DBConnector.getInstance().saveCSS(domain, css);
	}
}
