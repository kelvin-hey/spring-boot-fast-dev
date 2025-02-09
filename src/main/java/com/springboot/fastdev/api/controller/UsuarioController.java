package com.springboot.fastdev.api.controller;

import com.springboot.fastdev.domain.model.Usuario;
import com.springboot.fastdev.domain.repository.UsuarioRepository;
import com.springboot.fastdev.domain.service.UsuarioService;
import com.springboot.fastdev.domain.exception.EntidadeNaoEncontradaException;
import com.springboot.fastdev.domain.exception.EntidadeEmUsoException;

import org.springframework.http.HttpStatus;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {   
    
    @GetMapping
    public String usuarios(Model model) {
    
    	List<Usuario> listaUsuarios = List.of(
    		new Usuario("Kelvin", "kelvin@email.com"),
    		new Usuario("John", "john@email.com"),
    		new Usuario("Susan", "susan@email.com")
    	);
    	
    	model.addAttribute("titulo", "Controle de Usuários");
    	model.addAttribute("usuarios", listaUsuarios);
    	
    	return "usuarios";
    }
}