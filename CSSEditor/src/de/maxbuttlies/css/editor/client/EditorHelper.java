package de.maxbuttlies.css.editor.client;

public class EditorHelper {

	private static EditorHelper singleton = null;

	private static final String[] selector = { ".", "#" };
	private static final String[] attr = { "font-size", "line-height" };
	private static final String[] breaks = { "{", "}" };
	private static final String[] elements = { "p", "li" };
	private static final String tab = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
	private static final String maxSize = "line-height:60px;\nfont-size:50px;";
	private static final String COMMENTBEGIN = "/*";
	private static final String COMMENTEND = "*/";

	private EditorHelper() {

	}

	public static String replacePlaceHolder(String text) {
		// TODO Konfiguration in Datenbank
		if (text.contains("%maxSize%")) {
			text = text.replace("%maxSize%", maxSize);
		}
		return text;
	}

	public static String formatText(String text) {

		EditorHelper helper = new EditorHelper();

		text = text.replace("\n", "<br/>");
		text = helper.replaceArray(attr, text, "#0000ff");
		text = helper.replaceSelector(text);
		text = text.replace("\t", tab);
		text = helper.replaceComment(text);
		text = helper.format(text);
		return text;
	}

	private String replaceArray(String[] arr, String content, String color) {
		for (String value : arr) {

			content = content.replace(value, "<span style=\"color:" + color
					+ ";\">" + value + "</span>");

		}

		return content;
	}

	private String replaceComment(String content) {
		String tmp = content.trim();
		content = "";
		while (tmp.contains(COMMENTBEGIN)) {
			int begin = tmp.indexOf(COMMENTBEGIN);
			int end = tmp.indexOf(COMMENTEND) + COMMENTEND.length();
			if (end > tmp.length() || end < begin) {
				end = tmp.length();
			}
			String comment = tmp.substring(begin, end);
			System.out.println(comment);
			String pre = tmp.substring(0, begin);
			tmp = tmp.substring(end);
			content += pre + "<span style=\"color:#ccc;\">" + comment
					+ "</span>";
		}
		content += tmp;
		return content;
	}

	private String replaceSelector(String content) {
		String[] v = content.replace("<br>", " ").replace("{", " ").split(" ");
		for (String value : v) {

			for (String v1 : selector) {

				if (value.indexOf(v1) == 0) {
					content = content.replace(value,
							"<span style=\"color:red\">" + value + "</span>");
				}
			}
		}
		return content;
	}

	private String format(String text) {
		// text = text.replaceAll(" ", "").replaceAll("\n", "").replace("\t",
		// "");
		text = text.replace(";", ";<br>" + tab);
		text = text.replace("{", " {<br>" + tab);
		text = text.replace(tab + "}", "}<br>");
		text = text.replace(":", ": ");
		return text;
	}

}
