package com.italoweb.infoit.core.navegation;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.A;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;
import org.zkoss.zul.Span;

public class MenuComponent extends Div {

	private static final long serialVersionUID = -5821196369144434719L;
	private MenuManager manager = new MenuManager();
	
	public MenuComponent() {
		this.manager = new MenuManager();
		this.buildMenu();
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
	    this.appendChild(menuContent); // o el contenedor donde quieras agregarlo
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