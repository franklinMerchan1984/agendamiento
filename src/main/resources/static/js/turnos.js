// Call the dataTables jQuery plugin
//libreria dejs para sleccionar la tabla  y luego le pono la funcionalidad
//de convertirlo en una tabla que tenga paginacion
$(document).ready(function() {//ejecute el codigo
  cargarTurnos();
  $('#turnos').DataTable();
  actualizarEmailDelTurno();


});

function actualizarEmailDelTurno(){
    document.getElementById('txt-email-paciente').outerHTML = localStorage.email;
}

//java scrip puro
async function cargarTurnos(){
//cuando se utiliza await debemos indicar a la funcion que es asincronica
  const request = await fetch('api/turnos', {//request=variable
    method: 'GET',
    headers: getHeaders()
  });
  const turnos = await request.json();//RESULTADO SE CONVIERTE EN JSON

    let listadoHtml = '';//por cada Turno que se recorra se agrega en esta variable
    for (let turno of turnos){
        let botonEliminar = '<a href="#" onclick="eliminarTurno(' + turno.id + ')" class="btn btn-danger btn-circle btn-sm"><i class="fas fa-trash"></i></a>';

    //mostrar los turnos
    //let telefonoTexto = turno.telefono == null ? '-' : turno.telefono;//?para hacer un if en una sola linea
     let turnoHtml = '<tr><td>' + turno.id + '</td><td>' + turno.cedula+ '</td><td>' + turno.especialidad + '</td><td>'
                     + turno.fecha + '</td><td>' + turno.hora
                     +'</td><td>' + botonEliminar + '</td></tr>';
    listadoHtml += turnoHtml;
    }


  document.querySelector("#turnos tbody").outerHTML = listadoHtml;
}

function getHeaders(){
    return {
              'Accept': 'application/json',
              'Content-Type': 'application/json',
              'Authorization' : localStorage.token
            };
}

async function eliminarTurno(id){
if(!confirm('¿Desea eliminar este turno?')){
return;//corta la funcion si hace click en false
}
//cuando se utiliza await debemos indicar a la funcion que es asincronica
  const request = await fetch('api/turno/' + id, {//request=variable
    method: 'DELETE',
    headers: getHeaders()
  });

  location.reload()
 //alert(id);//mensaje en pantalla
}

