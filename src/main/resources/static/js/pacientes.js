// Call the dataTables jQuery plugin
//libreria dejs para sleccionar la tabla  y luego le pono la funcionalidad
//de convertirlo en una tabla que tenga paginacion
$(document).ready(function() {//ejecute el codigo
  cargarPacientes();
  $('#pacientes').DataTable();
  actualizarEmailDelPaciente();

});

function actualizarEmailDelPaciente(){
    document.getElementById('txt-email-paciente').outerHTML = localStorage.email;
}

//java scrip puro
async function cargarPacientes(){
//cuando se utiliza await debemos indicar a la funcion que es asincronica
  const request = await fetch('api/pacientes', {//request=variable
    method: 'GET',
    headers: getHeaders()
  });
  const pacientes = await request.json();//RESULTADO SE CONVIERTE EN JSON

    let listadoHtml = '';//por cada Paciente que se recorra se agrega en esta variable
    for (let paciente of pacientes){
        let botonEliminar = '<a href="#" onclick="eliminarPaciente(' + paciente.id + ')" class="btn btn-danger btn-circle btn-sm"><i class="fas fa-trash"></i></a>';

    //mostrar los pacientes
    let telefonoTexto = paciente.telefono == null ? '-' : paciente.telefono;//?para hacer un if en una sola linea
     let pacienteHtml = '<tr><td>' + paciente.id + '</td><td>' + paciente.nombre + ' ' + paciente.apellido + '</td><td>'
                     + paciente.email + '</td><td>' + telefonoTexto
                     +'</td><td>' + botonEliminar + '</td></tr>';
    listadoHtml += pacienteHtml;
    }


  document.querySelector("#pacientes tbody").outerHTML = listadoHtml;
}

function getHeaders(){
    return {
              'Accept': 'application/json',
              'Content-Type': 'application/json',
              'Authorization' : localStorage.token
            };
}

async function eliminarPaciente(id){
if(!confirm('¿Desea eliminar este paciente?')){
return;//corta la funcion si hace click en false
}
//cuando se utiliza await debemos indicar a la funcion que es asincronica
  const request = await fetch('api/pacientes/' + id, {//request=variable
    method: 'DELETE',
    headers: getHeaders()
  });

  location.reload()
 //alert(id);//mensaje en pantalla
}