package com.italoweb.infoit;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zk.ui.util.ConventionWires;
import org.zkoss.zul.Div;
import org.zkoss.zul.Window;

public class Index extends Window implements AfterCompose {

	private static final long serialVersionUID = -1336740925222122770L;
	private Div main_root;

	@Override
	public void afterCompose() {
		ConventionWires.wireVariables(this, this);
        loadPages();
	}
	
	@SuppressWarnings("unused")
	private void loadPages() {
	    Map<String, Object> args = new HashMap<>();
	    args.put("FATHER", this);
	    this.main_root.getChildren().clear();
        Session session = Sessions.getCurrent();
        Object user = session.getAttribute("usuario");
	    if (user != null) {
	    	Executions.createComponents("/base.zul", this.main_root, args);
		}else {
			Executions.createComponents("/login.zul", this.main_root, args);
		}
	    
	}
}