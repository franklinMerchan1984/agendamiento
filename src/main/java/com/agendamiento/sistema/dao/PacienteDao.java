package com.agendamiento.sistema.dao;

import com.agendamiento.sistema.models.Paciente;
import com.agendamiento.sistema.models.Usuario;

import java.util.List;

public interface PacienteDao {
//las clases que implementen esta interface esta obligada a utilzar esta funcion
    List<Paciente> getPacientes();//funciones

    void eliminar(Long id);

    void registrar(Paciente paciente);

    Paciente optenerPacientePorCredenciales(Paciente paciente);


}
