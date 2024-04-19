package com.example.lab3_20212607.repository;


import com.example.lab3_20212607.entity.Oftalmologo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OftalmologoRepository extends JpaRepository<Oftalmologo,Integer> {
}
