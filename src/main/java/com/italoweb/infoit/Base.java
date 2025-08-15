package com.italoweb.infoit;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.BookmarkEvent;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zk.ui.util.ConventionWires;
import org.zkoss.zul.A;
import org.zkoss.zul.Div;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.Span;
import org.zkoss.zul.Style;
import org.zkoss.zul.Window;

import com.italoweb.infoit.core.apariencia.Apariencia;
import com.italoweb.infoit.core.apariencia.AparienciaManager;
import com.italoweb.infoit.core.navegation.MenuItem;
import com.italoweb.infoit.core.navegation.MenuManager;
import com.italoweb.infoit.core.navegation.NavigationMdel;
import com.italoweb.infoit.core.util.HEXUtil;

public class Base extends Window implements AfterCompose {

	private static final long serialVersionUID = -1336740925222122770L;
	private Div menu_content;
	private Div div_user;
	private Include include_main_root;
	private Index father = (Index) Executions.getCurrent().getArg().get("FATHER");
	private MenuManager manager = new MenuManager();
	private AparienciaManager aparienciaManager = new AparienciaManager();

	@Override
	public void afterCompose() {
		ConventionWires.wireVariables(this, this);
		this.loadComponents();
		father.addEventListener("onBookmarkChange", (evt) -> {
	      BookmarkEvent event = (BookmarkEvent) evt;
	      String newBookmark = event.getBookmark();
	      loadBookmarkChange(newBookmark);
		});
		this.buildMenu();
		this.loadPages(NavigationMdel.INDEX_PAGE);
	}
	
	private void loadComponents() {
		this.setupLook();
		this.div_user.addEventListener(Events.ON_CLICK, event -> closeSession());
	}
	
	private void setupLook() {
        /*Styles*/
        String colorPrimario = "#3f8f71";
        boolean degradadoNavbar = true;
        String colorPrimarioDegr = "#41d59d";
        String colorSecundarioDegr = "#3f8f71";
        String colorNavbar = degradadoNavbar
                ? String.format("linear-gradient(-45deg, %s 0%%, %s 100%%)", colorPrimarioDegr, colorSecundarioDegr)
                : colorPrimario;
        String borderNavbar = "black";
        String backgroundSidebar = "#ffff";
        String itemMenu = "#ffff";
        String hoverMenu = "#ffff";
        String hoverColorMenu = "#ffff";
        List<Apariencia> list = this.aparienciaManager.getApariencia();
        if (list.size() > 0){
            colorPrimario = list.get(0).getColorPrimary();
            colorPrimarioDegr = list.get(0).getGradientStartNavbar();
            colorSecundarioDegr = list.get(0).getGradientEndNavbar();
            colorNavbar = degradadoNavbar
                    ? String.format("linear-gradient(-45deg, %s 0%%, %s 100%%)", colorPrimarioDegr, colorSecundarioDegr)
                    : colorPrimario;
            borderNavbar = list.get(0).getBorderNavbar();
            backgroundSidebar = list.get(0).getBackgroundSidebar();
            itemMenu = list.get(0).getColorItemMenu();
            hoverMenu = list.get(0).getHoverMenu();
            hoverColorMenu = list.get(0).getHoverColorMenu();
        }
        StringBuilder css = new StringBuilder();
        css.append(":root {\n");
        css.append(String.format("    --color-navbar: %s !important;\n", colorNavbar));
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
        if (this.include_main_root.getChildPage() != null) {
            this.include_main_root.getChildPage().removeComponents();
            this.include_main_root.setSrc(null);
        }
        this.include_main_root.setSrc(src);
	}

	private List<MenuItem> buildMenuData() {
        List<MenuItem> menuItems = new ArrayList<>();
        menuItems = manager.getMenu();
        menuItems.sort(Comparator.comparingInt(MenuItem::getOrder));
        return menuItems;
    }
	
	private void buildMenu() {
	    Div menuContent = new Div();
	    menuContent.setSclass("menu-content-base");

	    Div menuItemsContainer = new Div();
	    menuItemsContainer.setSclass("menu-items");

	    // Título del menú
	    Div menuTitle = new Div();
	    menuTitle.setSclass("menu-title");
	    menuTitle.appendChild(new Label("Menu"));
	    menuItemsContainer.appendChild(menuTitle);

	    // Construir menú
	    for (MenuItem item : this.buildMenuData()) {
	        menuItemsContainer.appendChild(createMenuItem(item));
	    }

	    menuContent.appendChild(menuItemsContainer);
	    menu_content.appendChild(menuContent); // o el contenedor donde quieras agregarlo
	}

	private Component createMenuItem(MenuItem item) {
	    Div itemDiv = new Div();
	    itemDiv.setSclass("item");

	    if (item.getSubMenu() == null || item.getSubMenu().isEmpty()) {
	        // Es un enlace simple
	        A link = new A();
	        link.setHref(item.getHref() != null ? item.getHref() : "#");
	        if (item.getIconClass() != null) {
	            link.setIconSclass(item.getIconClass());
	        }
	        Span span = new Span();
	        span.setStyle("margin:4px");
	        span.appendChild(new Label(item.getTitle()));
	        link.appendChild(span);

	        itemDiv.appendChild(link);
	    } else {
	        // Es un submenú
	        Div submenuItem = new Div();
	        submenuItem.setSclass("submenu-item");

	        if (item.getIconClass() != null) {
	            Div iconDiv = new Div();
	            iconDiv.setSclass(item.getIconClass());
	            Span span = new Span();
	            span.setStyle("margin:4px");
	            span.appendChild(new Label(item.getTitle()));
	            iconDiv.appendChild(span);
	            submenuItem.appendChild(iconDiv);
	        } else {
	            Span span = new Span();
	            span.setStyle("margin:4px");
	            span.appendChild(new Label(item.getTitle()));
	            submenuItem.appendChild(span);
	        }

	        Div chevron = new Div();
	        chevron.setSclass("fa-solid fa-chevron-right");
	        submenuItem.appendChild(chevron);

	        itemDiv.appendChild(submenuItem);

	        // Contenedor del submenú
	        Div submenuContainer = new Div();
	        submenuContainer.setSclass("menu-items submenu");

	        // Título del submenú
	        Div submenuTitle = new Div();
	        submenuTitle.setSclass("menu-title");

	        Div chevronLeft = new Div();
	        chevronLeft.setSclass("fa-solid fa-chevron-left");
	        submenuTitle.appendChild(chevronLeft);
	        submenuTitle.appendChild(new Label(item.getTitle()));

	        submenuContainer.appendChild(submenuTitle);

	        // Agregar hijos recursivamente
	        for (MenuItem sub : item.getSubMenu()) {
	            submenuContainer.appendChild(createMenuItem(sub));
	        }

	        itemDiv.appendChild(submenuContainer);
	    }

	    return itemDiv;
	}
	
}