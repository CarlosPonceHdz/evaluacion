function loadSidebar() {
    fetch('./sidebar.html')
        .then(response => response.text())
        .then(data => {
            document.getElementById('sidebar-container').innerHTML = data;
        })
        .catch(error => console.error('Error cargando el menú lateral:', error));
}

document.addEventListener('DOMContentLoaded', loadSidebar);


// Función para verificar el acceso según el rol
        function verificarAcceso(pagina) {
            const usuario = JSON.parse(localStorage.getItem('usuario'));
            if(pagina === "Entradas.html"){
                window.location.href = `../Vista/${pagina}`; 
            }else if(pagina === "Salidas.html") {
                 if (usuario.idRol.idRol === 2) {
                window.location.href = `../Vista/${pagina}`;
                 }else{
                      alert('No tienes permiso para acceder a esta página.');
                 }
                 
            }else if(pagina === "Historial.html"){
                 if (usuario.idRol.idRol === 1) {
                window.location.href = `../Vista/${pagina}`;
                 }else{
                      alert('No tienes permiso para acceder a esta página.');
                 }
            }
           
        }