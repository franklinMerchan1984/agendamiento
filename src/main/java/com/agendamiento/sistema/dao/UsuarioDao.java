package com.agendamiento.sistema.dao;

import com.agendamiento.sistema.models.Usuario;

import java.util.List;

public interface UsuarioDao {
//las clases que implementen esta interface esta obligada a utilzar esta funcion
    List<Usuario> getUsuarios();//funciones

    void eliminar(Long id);

    void registrar(Usuario usuario);

    Usuario optenerUsuarioPorCredenciales(Usuario usuario);
}
