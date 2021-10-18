package com.agendamiento.sistema.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table (name ="turnos")//para ir a la tabla pacientes en mysql
public class Turnos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "cedula")
    private String cedula;

    @Column(name = "especialidad", nullable = false)
    private String especialidad;

    @Column(name = "fecha", nullable = false)
    private String fecha;

    @Column(name = "hora", nullable = false)
    private String hora;

}
