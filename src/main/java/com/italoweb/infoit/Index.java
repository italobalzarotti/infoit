package com.italoweb.infoit;

import java.io.InputStream;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.BookmarkEvent;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zk.ui.util.ConventionWires;
import org.zkoss.zul.Include;
import org.zkoss.zul.Window;

public class Index extends Window implements AfterCompose {

	private static final long serialVersionUID = -1336740925222122770L;
	private Include include_main_root;

	@Override
	public void afterCompose() {
		ConventionWires.wireVariables(this, this);
        this.addEventListener("onBookmarkChange", (evt) -> {
        	System.out.println("Auuijijfijfoi");
            BookmarkEvent event = (BookmarkEvent) evt;
            String newBookmark = event.getBookmark();
            
            loadBookmarkChange(newBookmark);
        });
        loadPages("/base.zul");
	}
	
	private void loadBookmarkChange(String bookmarkID) {
	    if (bookmarkID == null || bookmarkID.isBlank()) {
	        loadPages("/views/base.zul");
	        return;
	    }

	    String pagePath = "/views/" + bookmarkID + ".zul";

	    if (pageExists(pagePath)) {
	        loadPages(pagePath);
	    } else {
	        loadPages("/views/notfound.zul");
	    }
	}

	private void loadPages(String src) {
	    System.out.println("Cargando: " + src);
	    this.include_main_root.setSrc(src);
	}

	private boolean pageExists(String path) {
	    InputStream in = Executions.getCurrent().getDesktop().getWebApp().getResourceAsStream(path);
	    return in != null;
	}
	
}