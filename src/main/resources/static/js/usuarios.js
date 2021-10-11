// Call the dataTables jQuery plugin
//libreria dejs para sleccionar la tabla  y luego le pono la funcionalidad
//de convertirlo en una tabla que tenga paginacion
$(document).ready(function() {//ejecute el codigo
  cargarUsuarios();
  $('#usuarios').DataTable();
  actualizarEmailDelUsuario();

});

function actualizarEmailDelUsuario(){
    document.getElementById('txt-email-usuario').outerHTML = localStorage.email;
}

//java scrip puro
async function cargarUsuarios(){
//cuando se utiliza await debemos indicar a la funcion que es asincronica
  const request = await fetch('api/usuarios', {//request=variable
    method: 'GET',
    headers: getHeaders()
  });
  const usuarios = await request.json();//RESULTADO SE CONVIERTE EN JSON

    let listadoHtml = '';//por cada usuario que se recorra se agrega en esta variable
    for (let usuario of usuarios){
        let botonEliminar = '<a href="#" onclick="eliminarUsuario(' + usuario.id + ')" class="btn btn-danger btn-circle btn-sm"><i class="fas fa-trash"></i></a>';

    //mostrar los usuarios
    let telefonoTexto = usuario.telefono == null ? '-' : usuario.telefono;//?para hacer un if en una sola linea
     let usuarioHtml = '<tr><td>' + usuario.id + '</td><td>' + usuario.nombre + ' ' + usuario.apellido + '</td><td>'
                     + usuario.email + '</td><td>' + telefonoTexto
                     +'</td><td>' + botonEliminar + '</td></tr>';
    listadoHtml += usuarioHtml;
    }


  document.querySelector("#usuarios tbody").outerHTML = listadoHtml;
}

function getHeaders(){
    return {
              'Accept': 'application/json',
              'Content-Type': 'application/json',
              'Authorization' : localStorage.token
            };
}

async function eliminarUsuario(id){
if(!confirm('¿Desea eliminar este usuario?')){
return;//corta la funcion si hace click en false
}
//cuando se utiliza await debemos indicar a la funcion que es asincronica
  const request = await fetch('api/usuarios/' + id, {//request=variable
    method: 'DELETE',
    headers: getHeaders()
  });

  location.reload()
 //alert(id);//mensaje en pantalla
}