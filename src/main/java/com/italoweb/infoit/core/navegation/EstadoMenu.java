package com.italoweb.infoit.core.navegation;

public enum EstadoMenu {
    ACTIVO("Activo"),
    INACTIVO("Inactivo");

    private final String label;

    private EstadoMenu(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}