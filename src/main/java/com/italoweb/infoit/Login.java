package com.italoweb.infoit;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.BookmarkEvent;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zk.ui.util.ConventionWires;
import org.zkoss.zul.Window;

public class Login extends Window implements AfterCompose {

	private static final long serialVersionUID = -6574833514407804338L;
	private Index father = (Index) Executions.getCurrent().getArg().get("FATHER");

	@Override
	public void afterCompose() {
		ConventionWires.wireVariables(this, this);
		father.addEventListener("onBookmarkChange", (evt) -> {
	      BookmarkEvent event = (BookmarkEvent) evt;
	      String newBookmark = event.getBookmark();
	      System.out.println(newBookmark);
		});
	}
	
	public void beginSession() {
        Session zkSession = Sessions.getCurrent();
        // Guardar usuario en sesión
        zkSession.setAttribute("usuario", true);
        Executions.sendRedirect("/");
	}
	
}