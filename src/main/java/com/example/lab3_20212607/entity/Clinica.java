package com.example.lab3_20212607.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "clinica")
public class Clinica {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name="id",nullable = false)
    private int id;

    @Column(name="nombre",nullable = true)
    private String nombre;

    @Column(name="direccion",nullable = true)
    private String direccion;

    @Column(name="telefono",nullable = true)
    private String telefono;
}
