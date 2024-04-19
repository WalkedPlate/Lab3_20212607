package com.example.lab3_20212607.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "paciente")

public class Paciente {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name="id",nullable = false)
    private int id;

    @Column(name="nombre",nullable = true)
    private String nombre;

    @Column(name="edad",nullable = true)
    private Integer edad;

    @Column(name="genero",nullable = true)
    private String genero;

    @Column(name="diagnostico",nullable = true)
    private String diagnostico;

    @Column(name="fecha_cita",nullable = true)
    private String fechaCita;

    @Column(name="numero_habitacion",nullable = true)
    private Integer numeroHabitacion;

    @Column(name="oftalmologo_id",nullable = true)
    private Integer oftalmologoId;

    @Column(name="clinica_id",nullable = true)
    private Integer clinicaId;
}
