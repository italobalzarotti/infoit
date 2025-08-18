package com.italoweb.infoit;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.BookmarkEvent;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.ConventionWires;
import org.zkoss.zul.Div;
import org.zkoss.zul.Image;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.Style;
import org.zkoss.zul.Window;

import com.italoweb.infoit.core.apariencia.Apariencia;
import com.italoweb.infoit.core.apariencia.AparienciaManager;
import com.italoweb.infoit.core.navegation.MenuComponent;
import com.italoweb.infoit.core.navegation.NavigationMdel;
import com.italoweb.infoit.core.util.AppProperties;
import com.italoweb.infoit.core.util.HEXUtil;

public class Base extends Window implements AfterCompose {

	private static final long serialVersionUID = -1336740925222122770L;
	private Div menu_content;
	private Div div_user;
	private Include include_main_root;
	private Index father = (Index) Executions.getCurrent().getArg().get("FATHER");
	private AparienciaManager aparienciaManager = new AparienciaManager();
    private static final String LOGO_DIR = AppProperties.get("logo.dir");
    private static final String LOGO_FILENAME = "logo_app.jpg";
    private Image logo_app;
    private Label lbl_title;

	@Override
	public void afterCompose() {
		ConventionWires.wireVariables(this, this);
		this.loadComponents();
		father.addEventListener("onBookmarkChange", (evt) -> {
	      BookmarkEvent event = (BookmarkEvent) evt;
	      String newBookmark = event.getBookmark();
	      loadBookmarkChange(newBookmark);
		});
		this.loadPages(NavigationMdel.INDEX_PAGE);
	}
	
	private void loadComponents() {
		MenuComponent menu = new MenuComponent();
		this.lbl_title.setValue(aparienciaManager.getApariencia().getFirst().getName());
		this.menu_content.appendChild(menu);
		this.cargarLogoActual();
		this.setupLook();
		this.div_user.addEventListener(Events.ON_CLICK, event -> closeSession());
	}
	
	private void setupLook() {
        /*Styles*/
        String colorPrimario = "#0065b3";
        boolean degradadoNavbar = true;
        String colorPrimarioDegr = "#0065b3";
        String colorSecundarioDegr = "#00518f";
        String backgNavbar = degradadoNavbar
                ? String.format("linear-gradient(-45deg, %s 0%%, %s 100%%)", colorPrimarioDegr, colorSecundarioDegr)
                : colorPrimario;
        String colorNavar = "#ffffff";
        String borderNavbar = "ffffff";
        String backgroundSidebar = "#00518f";
        String itemMenu = "#ffffff";
        String hoverMenu = "#ffffff";
        String hoverColorMenu = "#00518f";
        List<Apariencia> list = this.aparienciaManager.getApariencia();
        if (list.size() > 0){
            colorPrimario = list.get(0).getColorPrimary();
            colorPrimarioDegr = list.get(0).getGradientStartNavbar();
            colorSecundarioDegr = list.get(0).getGradientEndNavbar();
            backgNavbar = degradadoNavbar
                    ? String.format("linear-gradient(-45deg, %s 0%%, %s 100%%)", colorPrimarioDegr, colorSecundarioDegr)
                    : colorPrimario;
            colorNavar = list.get(0).getColorNavbar();
            borderNavbar = list.get(0).getBorderNavbar();
            backgroundSidebar = list.get(0).getBackgroundSidebar();
            itemMenu = list.get(0).getColorItemMenu();
            hoverMenu = list.get(0).getHoverMenu();
            hoverColorMenu = list.get(0).getHoverColorMenu();
        }
        StringBuilder css = new StringBuilder();
        css.append(":root {\n");
        css.append(String.format("    --backg-navbar: %s !important;\n", backgNavbar));
        css.append(String.format("    --color-navbar: %s !important;\n", colorNavar));
        css.append(String.format("    --border-navbar: %s !important;\n", borderNavbar));
        css.append(String.format("    --color-primario: %s;\n", colorPrimario));
        css.append(String.format("    --backg-sidebar: %s;\n", backgroundSidebar));
        css.append(String.format("    --color-item-menu: %s;\n", itemMenu));
        css.append(String.format("    --hover-menu: %s;\n", hoverMenu));
        css.append(String.format("    --hover-color-menu: %s;\n", hoverColorMenu));
        css.append("}\n\n");
        Style style = new Style();
        style.setContent(css.toString());
        this.appendChild(style);
	}
	
	private void closeSession() {
        Session zkSession = Sessions.getCurrent();
        zkSession.removeAttribute("usuario");
        zkSession.invalidate();
        Executions.sendRedirect("/");
	}

	private void loadBookmarkChange(String bookmarkID) {
	    if (bookmarkID == null || bookmarkID.isBlank()) {
	        loadPages(NavigationMdel.BLANK_ZUL);
	        return;
	    }
		String desencriptado = HEXUtil.convertirAOriginalDesdeHexLargo(bookmarkID);
	    String pagePath = "/views/" + desencriptado;
	    if (pageExists(pagePath)) {
	        loadPages(pagePath);
	    } else {
	        loadPages(NavigationMdel.BLANK_ZUL);
	    }
	}
	
	private boolean pageExists(String path) {
	    InputStream in = Executions.getCurrent().getDesktop().getWebApp().getResourceAsStream(path);
	    return in != null;
	}
	
    private void loadPages(String src) {
        Clients.evalJavaScript("	var sidebarFather = document.querySelector(\".sidebar-father\");\n"
        		+ "	var sidebarDisabled = document.querySelector(\".disabled_sidebar\");\n"
        		+ "	\n"
        		+ "	sidebarFather.classList.remove(\"sidebar-on-open\");	\n"
        		+ "	sidebarFather.classList.add(\"sidebar-on-close\");\n"
        		+ "	sidebarDisabled.classList.remove(\"sidebardisabled-on-open\");\n"
        		+ "	sidebarDisabled.classList.add(\"sidebardisabled-on-close\");");
        if (this.include_main_root.getChildPage() != null) {
            this.include_main_root.getChildPage().removeComponents();
            this.include_main_root.setSrc(null);
        }
        this.include_main_root.setSrc(src);
	}
    
    private void cargarLogoActual() {
        File file = new File(LOGO_DIR, LOGO_FILENAME);
        if (file.exists()) {
            try {
                byte[] data = Files.readAllBytes(file.toPath());
                mostrarPreview(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    private void mostrarPreview(byte[] data) {
        String base64 = "data:image/jpeg;base64," +
                Base64.getEncoder().encodeToString(data);
        List<Apariencia> apariencia = aparienciaManager.getApariencia();
        String widthLogo = "100px";
        if (apariencia.size() > 0) {
        	int size = apariencia.get(0).getSizeLogo();
			if (Objects.nonNull(size)) {
				widthLogo = size+"px";
			}
		}
        this.logo_app.setSrc(base64);
        this.logo_app.setWidth(widthLogo);
    }
}