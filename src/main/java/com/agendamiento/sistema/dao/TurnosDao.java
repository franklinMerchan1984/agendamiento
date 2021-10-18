package com.agendamiento.sistema.dao;

import com.agendamiento.sistema.models.Turnos;

import java.util.List;

public interface TurnosDao {
//las clases que implementen esta interface esta obligada a utilzar esta funcion
    List<Turnos> getTurnos();//funciones

    void eliminar(Long id);

    void registrar(Turnos turno);

    Turnos optenerTurnoPorCedula(Turnos turno);


}
