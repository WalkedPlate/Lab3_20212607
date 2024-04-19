package com.example.lab3_20212607.controller;

import com.example.lab3_20212607.entity.Clinica;
import com.example.lab3_20212607.entity.Oftalmologo;
import com.example.lab3_20212607.entity.Paciente;
import com.example.lab3_20212607.repository.ClinicaRepository;
import com.example.lab3_20212607.repository.OftalmologoRepository;
import com.example.lab3_20212607.repository.PacienteRepository;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class OftalmologoController {

    final ClinicaRepository clinicaRepository;
    final OftalmologoRepository oftalmologoRepository;
    final PacienteRepository pacienteRepository;

    public OftalmologoController(ClinicaRepository clinicaRepository, OftalmologoRepository oftalmologoRepository, PacienteRepository pacienteRepository){
        this.clinicaRepository = clinicaRepository;
        this.oftalmologoRepository = oftalmologoRepository;
        this.pacienteRepository = pacienteRepository;
    }

    @GetMapping(value ={"/oftalmologo"})
    public String listaOftalmologo(Model model){
        List<Oftalmologo> lista =oftalmologoRepository.findAll();
        List<Clinica> listaClinicas =clinicaRepository.findAll();
        model.addAttribute("listaClinicas", lista);
        model.addAttribute("lista", lista);
        return "Oftalmologo/listaOftalmologo";
    }
}
