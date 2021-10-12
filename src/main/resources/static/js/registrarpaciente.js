$(document).ready(function() {//ejecute el codigo
  // on ready
});
//java scrip puro
async function registrarPaciente(){//peticion request
    let datos = {};//los datos deben ser los mismo sque recibimos
    datos.nombre = document.getElementById('txtNombre').value;
    datos.apellido = document.getElementById('txtApellido').value;
    datos.email = document.getElementById('txtEmail').value;
    datos.password = document.getElementById('txtPassword').value;

    let repetirPassword = document.getElementById('txtRepetirPassword').value;

    if(repetirPassword != datos.password){
    alert('La contraseña que escribiste es diferente.');
    return;
    }


  const request = await fetch('api/pacientes', {//request=variable//cuando se utiliza await debemos indicar a la funcion que es asincronica
    method: 'POST',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
      body: JSON.stringify(datos)//stringify coje cualquier objeto de js y le convierte a un string de json
  });
  alert("La cuenta fue creado con exito");
     window.location.href = 'Login.html'
  }
