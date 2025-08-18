zk.afterMount(function () {
	const sidebarFather = document.querySelector(".sidebar-father");
    const sidebar = document.querySelector(".sidebar");
    const sidebarClose = document.querySelector(".sidebar-btn-close");
	const sidebarOpen = document.querySelector(".sidebar-btn-open");
	const sidebarDisabled = document.querySelector(".disabled_sidebar");
    const menu = document.querySelector(".menu-content");
	
    sidebarClose.addEventListener("click", () => {
		sidebarFather.classList.remove("sidebar-on-open");	
        sidebarFather.classList.add("sidebar-on-close");
		sidebarDisabled.classList.remove("sidebardisabled-on-open");
		sidebarDisabled.classList.add("sidebardisabled-on-close");
    });
	
	sidebarOpen.addEventListener("click", () => {
		sidebarFather.classList.remove("sidebar-on-close");
	    sidebarFather.classList.add("sidebar-on-open");
		sidebarDisabled.classList.remove("sidebardisabled-on-close");
		sidebarDisabled.classList.add("sidebardisabled-on-open");
	});
	
	sidebarDisabled.addEventListener("click", () => {
		sidebarFather.classList.remove("sidebar-on-open");	
	    sidebarFather.classList.add("sidebar-on-close");
		sidebarDisabled.classList.remove("sidebardisabled-on-open");
		sidebarDisabled.classList.add("sidebardisabled-on-close");
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
