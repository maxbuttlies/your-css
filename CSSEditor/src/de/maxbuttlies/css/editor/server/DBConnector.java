package de.maxbuttlies.css.editor.server;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import de.maxbuttlies.css.editor.server.database.Domain;
import de.maxbuttlies.css.editor.server.database.PMF;

public class DBConnector {
	private static DBConnector singleton = null;
	private static final String DIR = "./css/";

	private DBConnector() {

	}

	public static DBConnector getInstance() {
		if (singleton == null) {
			singleton = new DBConnector();
		}

		return singleton;
	}

	public void saveCSS(String domain, String css)
			throws Exception {
		PersistenceManager pm = null;
		try {
			pm = PMF.get().getPersistenceManager();
			System.out.println("Domain: " + domain);
			Key key = KeyFactory.createKey(
					Domain.class.getSimpleName(), domain);

			Domain d = null;

			try {
				d = pm.getObjectById(Domain.class, key);
				d.setCss(css);
				d.setDomain(domain);
			} catch (Exception e) {
				d = new Domain(domain, css);
				d.setKey(key);
				pm.makePersistent(d);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pm.close();
		}
	}

	public String getCSS(String domain) {
		String s = "";
		PersistenceManager pm = null;
		try {
			pm = PMF.get().getPersistenceManager();
			Key key = KeyFactory.createKey(
					Domain.class.getSimpleName(), domain);

			Domain d = pm.getObjectById(Domain.class, key);
			s = d.getCss();

		} finally {
			pm.close();
		}

		return s;
	}

	public List<String> getDomains() {

		List<String> names = new ArrayList<String>();

		PersistenceManager pm = null;
		try {
			pm = PMF.get().getPersistenceManager();

			List<Domain> domains = (List<Domain>) pm.newQuery(
					Domain.class).execute();

			for (Domain domain : domains) {
				names.add(domain.getDomain());
			}
		} finally {
			pm.close();
		}
		return names;
	}
}