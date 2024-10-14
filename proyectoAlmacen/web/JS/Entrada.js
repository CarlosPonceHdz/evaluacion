class ProductoManager {
    
    constructor() {
        
        this.tableBody = document.querySelector('table tbody');

        // Agregar el event listener para el formulario aquí
        document.getElementById('addProductForm').addEventListener('submit', this.agregarProducto.bind(this));
    }
    // Obtener productos de la API
    async obtenerProductos() {
        try {
            const response = await fetch('http://localhost:8080/proyectoAlmacen/api/producto/consultarProducto');
            if (!response.ok)
                throw new Error('Error: ' + response.statusText);
            return await response.json();
        } catch (error) {
            console.error("Error en la consulta de productos:", error);
            return [];
        }
    }

    tableBody = document.querySelector('table tbody');
    // Renderizar productos en la tabla
    async renderizarProductos() {
        const productos = await this.obtenerProductos();
        this.tableBody.innerHTML = productos.map(producto => `
            <tr>
                <td style="display:none;">${producto.idProducto}</td>
                <td>${producto.nombre}</td>
                <td>${producto.tipo}</td>
                <td>${producto.provedor}</td>
                <td>${producto.precio}</td>
                <td>${producto.cantidad	}</td>
                <td>${producto.estatus === 1 ? 'Activo' : 'Baja'}</td>
                <td>
                
        
                ${producto.estatus === 0 ? formatString('<button class="btn btn-success btn-sm" onclick="preguntarAlta({0})">Alta</button>', [producto.idProducto]) : ''}
                ${producto.estatus === 1 ? formatString('<button class="btn btn-danger btn-sm" onclick="preguntarBaja({0})">Baja</button>', [producto.idProducto]) : ''}
                <button class="btn btn-primary btn-sm" onclick="preguntaEntrada(${producto.idProducto})">Dar Entrada</button>
            </td>
            </tr>
        `).join('');

        function formatString(template, values) {
            return template.replace(/{(\d+)}/g, function (match, number) {
                return typeof values[number] != 'undefined' ? values[number] : match;
            });
        }
    }

    async agregarProducto(event) {
        event.preventDefault();

        const nombre = document.getElementById('nombre').value;
        const tipo = document.getElementById('tipo').value;
        const provedor = document.getElementById('proveedor').value;
        const precio = document.getElementById('precio').value;
        const cantidad = document.getElementById('cantidad').value;
        
       

        let objProducto = new Object();

        objProducto.idProducto = 0;
        objProducto.nombre = nombre;
        objProducto.tipo = tipo;
        objProducto.provedor = provedor;
        objProducto.precio = precio;
        objProducto.cantidad = cantidad;
        objProducto.estatus = 0;

        let ObjUsuario = JSON.parse(localStorage.getItem("usuario"));

        const data = {
            producto: JSON.stringify(objProducto),
            idUsuario: ObjUsuario.idUsuario
        };

        confirmarProducto(data);
         $('#addProductModal').modal('hide'); // Cerrar el modal
         location.reload();     

    }

}



function preguntarBaja(idProducto) {
    const usuario = JSON.parse(localStorage.getItem('usuario'));
            if(usuario.idRol.idRol === 2){
               alert('No tienes permiso para esta accion');
            }else  {
                if (confirm("¿Quieres dar de baja?") == true) {
        confirmarBaja(idProducto);
        location.reload();
    } else {

    }
                 }
                 
    
}

function preguntarAlta(idProducto) {
    const usuario = JSON.parse(localStorage.getItem('usuario'));
            if(usuario.idRol.idRol === 2){
               alert('No tienes permiso para esta accion');
            }else  {
    if (confirm("¿Quieres dar de Alta?") == true) {
        confirmarAlta(idProducto);
        location.reload();
    } else {

    }
            }
}

function preguntaEntrada(idProducto) {
    
    let ObjUsuario = JSON.parse(localStorage.getItem("usuario"));
    
    if(ObjUsuario.idRol.idRol === 2){
               alert('No tienes permiso para esta accion');
            }else  {
     let cantidad = prompt("Ingresa la cantidad:");
    if (cantidad == null || cantidad == "") {
        document.getElementById("msg").innerHTML = "No has ingresado cantidad, intenta de nuevo";
    } else
    {
        confirmarEntrada(idProducto, cantidad, ObjUsuario.idUsuario);

    }
            }
}



async function confirmarBaja(idProducto) {
    const data = {
        idProducto: idProducto
    };

    let params = new URLSearchParams(data);

    fetch('http://localhost:8080/proyectoAlmacen/api/producto/desactivar', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
        },
        body: params
    })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok: ' + response.statusText);
                }
                return response.json();
            })
            .then(data => {
                if (data.exception != null) {
                    alert("Error del servidor");
                    return;
                }
                if (data.error != null) {
                    alert(data.error);
                    return;
                }



                alert("Producto dado de baja correctamente");
            })
            .catch(error => {
                console.error("Error en la solicitud:", error);
               
            });
}

async function confirmarAlta(idProducto) {
    const data = {
        idProducto: idProducto
    };

    let params = new URLSearchParams(data);

    fetch('http://localhost:8080/proyectoAlmacen/api/producto/activar', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
        },
        body: params
    })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok: ' + response.statusText);
                }
                return response.json();
            })
            .then(data => {
                if (data.exception != null) {
                    alert("Error del servidor");
                    return;
                }
                if (data.error != null) {
                    alert(data.error);
                    return;
                }



                alert("Producto dado de Alta correctamente");
            })
            .catch(error => {
                console.error("Error en la solicitud:", error);
               
            });
}

async function confirmarEntrada(idProducto, cantidad, idUsuario) {
    const data = {
        cantidad: cantidad,
        idProducto: idProducto,
        idUsuario: idUsuario
    };

    let params = new URLSearchParams(data);

    fetch('http://localhost:8080/proyectoAlmacen/api/producto/entrada', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
        },
        body: params
    })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok: ' + response.statusText);
                }
                return response.json();
            })
            .then(data => {
                if (data.exception != null) {
                    alert("Error del servidor");
                    return;
                }
                if (data.error != null) {
                    alert(data.error);
                    return;
                }



                alert("Entrada realizada correctamente");
            })
            .catch(error => {
                console.error("Error en la solicitud:", error);
               ;
            });
}


async function confirmarProducto(data) {


    let params = new URLSearchParams(data);

    fetch('http://localhost:8080/proyectoAlmacen/api/producto/insertar', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
        },
        body: params
    })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok: ' + response.statusText);
                }
                return response.json();
            })
            .then(data => {
                if (data.exception != null) {
                    alert("Error del servidor");
                    return;
                }
                if (data.error != null) {
                    alert(data.error);
                    return;
                }


                
                alert("Entrada realizada correctamente");
            })
            .catch(error => {
                console.error("Error en la solicitud:", error);
               
            });
}


document.getElementById('addProductButton').addEventListener('click', function() {
    const usuario = JSON.parse(localStorage.getItem('usuario'));
            if(usuario.idRol.idRol === 2){
               alert('No tienes permiso para esta accion');
            }else  {
                  $('#addProductModal').modal('show');
            }
});
// Ejecutar cuando el DOM esté cargado
document.addEventListener('DOMContentLoaded', () => {
    const productoManager = new ProductoManager();
    productoManager.renderizarProductos();
});
