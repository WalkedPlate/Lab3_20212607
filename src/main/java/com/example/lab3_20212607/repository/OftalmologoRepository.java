package com.example.lab3_20212607.repository;


import com.example.lab3_20212607.entity.Oftalmologo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OftalmologoRepository extends JpaRepository<Oftalmologo,Integer> {

    List<Oftalmologo> findByClinicaId(Integer idClinica);
}
