package com.example.lab3_20212607.controller;

import com.example.lab3_20212607.entity.Clinica;
import com.example.lab3_20212607.entity.Paciente;
import com.example.lab3_20212607.repository.ClinicaRepository;
import com.example.lab3_20212607.repository.OftalmologoRepository;
import com.example.lab3_20212607.repository.PacienteRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class PacienteController {

    final ClinicaRepository clinicaRepository;
    final OftalmologoRepository oftalmologoRepository;
    final PacienteRepository pacienteRepository;

    public PacienteController(ClinicaRepository clinicaRepository, OftalmologoRepository oftalmologoRepository, PacienteRepository pacienteRepository){
        this.clinicaRepository = clinicaRepository;
        this.oftalmologoRepository = oftalmologoRepository;
        this.pacienteRepository = pacienteRepository;
    }

    @GetMapping(value ={"/paciente"})
    public String listaPacientes(Model model){
        List<Paciente> lista =pacienteRepository.findAll();
        model.addAttribute("listaPaciente", lista);
        return "Paciente/lista";
    }

    @GetMapping("paciente/editar")
    public String editar(Model model,
                         @RequestParam("idPaciente") int id){
        Optional<Paciente> prov = pacienteRepository.findById(id);
        if(prov.isPresent()){
            Paciente paciente = prov.get();
            model.addAttribute("paciente", paciente);
            return "Paciente/editPaciente";
        }
        return "redirect:/paciente";
    }

    @PostMapping(value = "paciente/guardar")
    public String guardarPaciente(@RequestParam("idPaciente") int id , @RequestParam("fecha_cita") String fecha_cita){
        pacienteRepository.guarfarFecha(fecha_cita,id);
        return "redirect:/paciente";
    }
}
