$(document).ready(function() {//ejecute el codigo
  // on ready
});
//java scrip puro
async function inciarSesion(){//peticion request
    let datos = {};//los datos deben ser los mismo sque recibimos
    datos.email = document.getElementById('txtEmail').value;
    datos.password = document.getElementById('txtPassword').value;

    //llamado al servidor
  const request = await fetch('api/login', {//request=variable//cuando se utiliza await debemos indicar a la funcion que es asincronica
    method: 'POST',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
      //pasa los datos necesarios para hacer el inicio de sesion
      body: JSON.stringify(datos)//stringify coje cualquier objeto de js y le convierte a un string de json
  });
  const windowGlobal = typeof window !== 'undefined' && window
  const response = await request.text();//RESULTADO SE CONVIERTE EN JSON ES RESPONSE VA HA ESTAR ESA INFORMACION QUE QUEREMOS GUARDAR
  if (response != 'FAIL'){
 windowGlobal.localStorage.token = response;
 windowGlobal.localStorage.email = datos.email;
     window.location.href = 'usuarios.html'
  } else {
    alert("las credenciales son incorrectas. Por favor intente nuevamente.");
  }
}

async function inciarSesionPaciente(){//peticion request
    let datos = {};//los datos deben ser los mismo sque recibimos
    datos.email = document.getElementById('txtEmail').value;
    datos.password = document.getElementById('txtPassword').value;

    //llamado al servidor
  const request = await fetch('api/loginpaciente', {//request=variable//cuando se utiliza await debemos indicar a la funcion que es asincronica
    method: 'POST',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
      //pasa los datos necesarios para hacer el inicio de sesion
      body: JSON.stringify(datos)//stringify coje cualquier objeto de js y le convierte a un string de json
  });
  const windowGlobal = typeof window !== 'undefined' && window
  const response = await request.text();//RESULTADO SE CONVIERTE EN JSON ES RESPONSE VA HA ESTAR ESA INFORMACION QUE QUEREMOS GUARDAR
  if (response != 'FAIL'){
 windowGlobal.localStorage.token = response;
 windowGlobal.localStorage.email = datos.email;
     window.location.href = 'turnosPacientes.html'
  } else {
    alert("las credenciales son incorrectas. Por favor intente nuevamente.");
  }
}