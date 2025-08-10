zk.afterMount(function () {
    const sidebar = document.querySelector(".sidebar");
    const sidebarClose = document.querySelector(".sidebar-close");
    const menu = document.querySelector(".menu-content");

    sidebarClose.addEventListener("click", () => {
        sidebar.classList.toggle("close");
    });

    menu.addEventListener("click", (e) => {
        const submenuItem = e.target.closest(".submenu-item");
        const backButton = e.target.closest(".menu-title");

        // Abrir submenú
        if (submenuItem) {
            const submenu = submenuItem.nextElementSibling;
            if (submenu && submenu.classList.contains("submenu")) {
                submenu.classList.add("active");
            }
        }

        // Volver al menú anterior
        if (backButton) {
            const currentMenu = backButton.closest(".submenu");
            currentMenu.classList.remove("active");
        }
    });
});
