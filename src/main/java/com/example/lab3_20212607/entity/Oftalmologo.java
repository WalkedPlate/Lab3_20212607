package com.example.lab3_20212607.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "oftalmologo")
public class Oftalmologo {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name="id",nullable = false)
    private int id;

    @Column(name="nombre",nullable = true)
    private String nombre;

    @Column(name="correo",nullable = true)
    private String correo;

    @Column(name="clinica_id",nullable = true)
    private Integer clinicaId;
}
