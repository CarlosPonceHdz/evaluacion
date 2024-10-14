function inicioSension() {
    let correo = document.getElementById("correo").value; 
    let contrasenia = document.getElementById("contrasenia").value; 

    if (correo != null && correo !== "" && contrasenia != null && contrasenia !== "") {
       
        const data = {
            usuario: correo,
            contrasenia: contrasenia
        };

        let params = new URLSearchParams(data);
        
        fetch('api/usuario/validarUsuariov2', {
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

            // Guardar información del usuario en el localStorage
            localStorage.setItem('usuario', JSON.stringify(data));

            // Redirigir a otra página HTML
            window.location.href = 'Vista/Entradas.html'; // Cambia 'otra_pagina.html' por la URL deseada.

            alert("Inicio de sesión exitoso.");
        })
        .catch(error => {
            console.error("Error en la solicitud:", error);
            alert("Datos no validos.");
        });
    } else {
        alert("Los campos están vacíos. Por favor, complete ambos.");  
    }
}
