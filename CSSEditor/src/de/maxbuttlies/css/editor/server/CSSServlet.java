package de.maxbuttlies.css.editor.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CSSServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3126038728684722293L;
	private static final String PARAM_DOMAIN = "d";

	@Override
	protected void doGet(HttpServletRequest req,
			HttpServletResponse resp) throws ServletException,
			IOException {
		if (req.getParameter(PARAM_DOMAIN) != null) {
			resp.setContentType("text/css");
			resp.getWriter().write(
					DBConnector.getInstance().getCSS(
							req.getParameter(PARAM_DOMAIN)));
		}

	}
}
