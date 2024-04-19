package com.example.lab3_20212607.repository;


import com.example.lab3_20212607.entity.Oftalmologo;
import com.example.lab3_20212607.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente,Integer> {

    List<Paciente> findByClinicaId(Integer idClinica);
    @Query(value = "update pacientes set fecha_cita = ?1 where id = ?2", nativeQuery = true)
    void guarfarFecha(String fechaCita, Integer id);
}
