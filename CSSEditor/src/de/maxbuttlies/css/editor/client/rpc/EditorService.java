package de.maxbuttlies.css.editor.client.rpc;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("editor")
public interface EditorService extends RemoteService {
	String getCSS(String domain);

	List<String> getDomains();

	void saveCSS(String domain, String css) throws Exception;
}
