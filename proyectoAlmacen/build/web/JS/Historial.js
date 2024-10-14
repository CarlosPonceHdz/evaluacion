class HistorialManager {
    constructor() {
        this.tableBody = document.querySelector('table tbody');
        this.filtro = document.getElementById('tipoMovimiento'); // Seleccionar el elemento del filtro
        this.filtro.addEventListener('change', () => this.renderizarHistorial()); // Escuchar cambios en el filtro
    }

    // Obtener productos de la API
    async obtenerHistorial() {
        try {
            const response = await fetch('http://localhost:8080/proyectoAlmacen/api/historial/consultarHistorial');
            if (!response.ok) throw new Error('Error: ' + response.statusText);
            return await response.json();
        } catch (error) {
            console.error("Error en la consulta de productos:", error);
            return [];
        }
    }

    // Filtrar historial según el tipo de movimiento
    async filtrarHistorial(historial) {
        const tipoSeleccionado = this.filtro.value;
        if (!tipoSeleccionado) return historial; // Si no hay filtro, devolver todo

        return historial.filter(item => item.tipoMovimiento === tipoSeleccionado);
    }

    // Renderizar productos en la tabla
    async renderizarHistorial() {
        const historial = await this.obtenerHistorial();
        const historialFiltrado = await this.filtrarHistorial(historial);

        this.tableBody.innerHTML = historialFiltrado.map(historial => `
            <tr>
                <td>${historial.tipoMovimiento}</td>
                <td>${historial.idUsuario.gafet}</td>
                <td>${historial.idUsuario.nombre}</td>
                <td>${historial.idUsuario.apellido}</td>
                <td>${historial.idProducto.nombre}</td>
                <td>${historial.cantidadAnterior}</td>
                <td>${historial.cantidadNueva}</td>
                <td>${historial.fecha}</td> 
                <td>${historial.hora}</td>  
            </tr>
        `).join('');
    }
}

document.addEventListener('DOMContentLoaded', () => {
    window.historialManager = new HistorialManager(); // Almacena la instancia en la ventana para que sea accesible en el HTML
    historialManager.renderizarHistorial();
});
