$(document).ready(function() {//ejecute el codigo
  // on ready
});
//java scrip puro
async function registrarTurno(){//peticion request
    let datos = {};//los datos deben ser los mismo sque recibimos
    datos.cedula = document.getElementById('txtCedula').value;
    datos.especialidad = document.getElementById('txtEspecialidad').value;
    datos.fecha = document.getElementById('txtFecha').value;
    datos.hora = document.getElementById('txtHora').value;


    //if(repetirPassword != datos.password){
    //alert('La contraseña que escribiste es diferente.');
    //return;
    //}


  const request = await fetch('api/turnopost',  {//request=variable//cuando se utiliza await debemos indicar a la funcion que es asincronica
    method: 'POST',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
      body: JSON.stringify(datos)//stringify coje cualquier objeto de js y le convierte a un string de json
  });
  if(!datos==null){
  alert("El turno fue creado con exito");
     //window.location.href = 'Login.html'
     }else{
     alert("Datos incorrectos");
     }
  }
