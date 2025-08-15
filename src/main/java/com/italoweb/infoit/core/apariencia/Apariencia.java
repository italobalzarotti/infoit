package com.italoweb.infoit.core.apariencia;

public class Apariencia {

    private String colorPrimary;
    private String gradientStartNavbar;
    private String gradientEndNavbar;
    private String borderNavbar;
    private String backgroundSidebar;
    private String colorItemMenu;
    private String hoverMenu;
    private String hoverColorMenu;
    private String name;
    private String description;
    private String logo;
    private int sizeLogo;

    public Apariencia() {
    }

    public Apariencia(String colorPrimary, String gradientStartNavbar, String gradientEndNavbar) {
        this.colorPrimary = colorPrimary;
        this.gradientStartNavbar = gradientStartNavbar;
        this.gradientEndNavbar = gradientEndNavbar;
    }

    public String getColorPrimary() {
        return colorPrimary;
    }

    public void setColorPrimary(String colorPrimary) {
        this.colorPrimary = colorPrimary;
    }

    public String getGradientStartNavbar() {
        return gradientStartNavbar;
    }

    public void setGradientStartNavbar(String gradientStartNavbar) {
        this.gradientStartNavbar = gradientStartNavbar;
    }

    public String getGradientEndNavbar() {
        return gradientEndNavbar;
    }

    public void setGradientEndNavbar(String gradientEndNavbar) {
        this.gradientEndNavbar = gradientEndNavbar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

	public int getSizeLogo() {
		return sizeLogo;
	}

	public void setSizeLogo(int sizeLogo) {
		this.sizeLogo = sizeLogo;
	}

	public String getBackgroundSidebar() {
		return backgroundSidebar;
	}

	public void setBackgroundSidebar(String backgroundSidebar) {
		this.backgroundSidebar = backgroundSidebar;
	}

	public String getHoverMenu() {
		return hoverMenu;
	}

	public void setHoverMenu(String hoverMenu) {
		this.hoverMenu = hoverMenu;
	}

	public String getBorderNavbar() {
		return borderNavbar;
	}

	public void setBorderNavbar(String borderNavbar) {
		this.borderNavbar = borderNavbar;
	}

	public String getColorItemMenu() {
		return colorItemMenu;
	}

	public void setColorItemMenu(String colorItemMenu) {
		this.colorItemMenu = colorItemMenu;
	}

	public String getHoverColorMenu() {
		return hoverColorMenu;
	}

	public void setHoverColorMenu(String hoverColorMenu) {
		this.hoverColorMenu = hoverColorMenu;
	}
}
