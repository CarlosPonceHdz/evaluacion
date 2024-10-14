class ProductoManager {
    constructor() {
        this.cantidadExistente = 0; // Inicializar la cantidad existente
        this.tableBody = document.querySelector('table tbody');

        // Agregar el event listener para el formulario aquí
        document.getElementById('addProductForm').addEventListener('submit', this.agregarProducto.bind(this));
    }

    async obtenerProductos() {
        try {
            const response = await fetch('http://localhost:8080/proyectoAlmacen/api/producto/consultarProductoActivos');
            if (!response.ok) throw new Error('Error: ' + response.statusText);
            return await response.json();
        } catch (error) {
            console.error("Error en la consulta de productos:", error);
            return [];
        }
    }

    async renderizarProductos() {
        const productos = await this.obtenerProductos();
        this.tableBody.innerHTML = productos.map(producto => `
            <tr>
                <td style="display:none;">${producto.idProducto}</td>
                <td>${producto.nombre}</td>
                <td>${producto.tipo}</td>
                <td>${producto.provedor}</td>
                <td>${producto.precio}</td>
                <td>${producto.cantidad}</td>
                <td>${producto.estatus === 1 ? 'Activo' : 'Inactivo'}</td>
                <td>
                    <button class="btn btn-warning btn-sm" data-toggle="modal" data-target="#addProductModal" onclick="productoManager.cargarProducto('${producto.idProducto}', '${producto.nombre}', '${producto.tipo}', '${producto.provedor}', ${producto.precio}, ${producto.cantidad}, ${producto.estatus})">Salida</button>
                </td>
            </tr>
        `).join('');
    }

    cargarProducto(id, nombre, tipo, proveedor, precio, cantidad, estatus) {
        // Llenar los campos del formulario con los datos del producto
        
         document.getElementById('productID').value = id;
        document.getElementById('productName').value = nombre;
        document.getElementById('productTipo').value = tipo;
        document.getElementById('productProvedor').value = proveedor;
        document.getElementById('productPrecio').value = precio;
        document.getElementById('productCantidad').value = cantidad; // Este campo permanecerá habilitado
        document.getElementById('productStatus').value = estatus === 1 ? 'Activo' : 'Inactivo';

        // Deshabilitar los campos excepto el de cantidad
        document.getElementById('productName').disabled = true;
        document.getElementById('productTipo').disabled = true;
        document.getElementById('productProvedor').disabled = true;
        document.getElementById('productPrecio').disabled = true;
        document.getElementById('productStatus').disabled = true;
        
        this.cantidadExistente = cantidad; // Guardar la cantidad existente
    }

   async agregarProducto(event) {
            event.preventDefault();
    
    const nuevaCantidad = parseInt(document.getElementById('productCantidad').value);
    const  id= parseInt(document.getElementById('productID').value);
    // Validar que la nueva cantidad sea menor a la cantidad existente
    if (nuevaCantidad > this.cantidadExistente) {
        alert(`No puede ser mayor a : ${this.cantidadExistente}`);
        return; // Salir de la función si la validación falla
    }

    // Obtener los datos necesarios para la llamada a la API
   
    const idUsuario = JSON.parse(localStorage.getItem('usuario'));
    
    const formData = new URLSearchParams();
    formData.append('cantidad', nuevaCantidad);
    formData.append('idProducto', id);
    formData.append('idUsuario', idUsuario.idUsuario);

    try {
        const response = await fetch('http://localhost:8080/proyectoAlmacen/api/producto/salida', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: formData.toString() // Convertir a string para enviar en el body
        });

        if (!response.ok) {
            const errorData = await response.json();
            alert(`Error: ${errorData.error || 'Error al realizar la salida del producto'}`);
            return;
        }

        const data = await response.json();
        alert(data.mensaje); // Mensaje de éxito
        $('#addProductModal').modal('hide'); // Cerrar el modal
    } catch (error) {
        console.error('Error al realizar la solicitud:', error);
        alert('Error de conexión. Intente nuevamente.');
    }

    }
}

// Ejecutar cuando el DOM esté cargado
document.addEventListener('DOMContentLoaded', () => {
    window.productoManager = new ProductoManager(); // Almacena la instancia en la ventana para que sea accesible en el HTML
    productoManager.renderizarProductos();
});
