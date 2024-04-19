package com.example.lab3_20212607.controller;

import com.example.lab3_20212607.entity.Clinica;
import com.example.lab3_20212607.entity.Oftalmologo;
import com.example.lab3_20212607.entity.Paciente;
import com.example.lab3_20212607.repository.ClinicaRepository;
import com.example.lab3_20212607.repository.OftalmologoRepository;
import com.example.lab3_20212607.repository.PacienteRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class ClinicaController {

    final ClinicaRepository clinicaRepository;
    final OftalmologoRepository oftalmologoRepository;
    final PacienteRepository pacienteRepository;

    public ClinicaController(ClinicaRepository clinicaRepository, OftalmologoRepository oftalmologoRepository, PacienteRepository pacienteRepository){
        this.clinicaRepository = clinicaRepository;
        this.oftalmologoRepository = oftalmologoRepository;
        this.pacienteRepository = pacienteRepository;
    }

    @GetMapping(value ={"/clinica","","/"})
    public String listaClinica(Model model){
        List<Clinica> lista =clinicaRepository.findAll();
        model.addAttribute("listaClinica", lista);
        return "Clinica/lista";
    }


    @GetMapping("clinica/oftalmologos")
    public String verOftalmologos(Model model,
                         @RequestParam("idClinica") int id){

        List<Oftalmologo> listaOftalmologos =oftalmologoRepository.findByClinicaId(id);
        model.addAttribute("listaOftalmologos", listaOftalmologos);
        return "Clinica/listaOftal";
    }


    @GetMapping("clinica/pacientes")
    public String verPacientes(Model model,
                                  @RequestParam("idClinica") int id){

        List<Paciente> listaPacientes =pacienteRepository.findByClinicaId(id);
        model.addAttribute("listaPacientes", listaPacientes);
        return "Clinica/listaPacientes";
    }

}
