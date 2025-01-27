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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
	
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public List<Usuario> listar() {
        return usuarioService.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioService.pesquisar(id);
        
        if (usuario.isPresent()) {
        	return ResponseEntity.ok(usuario.get());
        }
        
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario updateUsuario(@RequestBody Usuario usuario) {
        return usuarioService.salvar(usuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizar(@PathVariable Long id, @RequestBody Usuario usuario) {
        Optional<Usuario> usuarioAtual = usuarioService.pesquisar(id);
        
        if (usuarioAtual.isPresent()) {
        	BeanUtils.copyProperties(usuario, usuarioAtual, "id");
        	
        	Usuario usuarioSalvo = usuarioService.salvar(usuarioAtual.get());
        	return ResponseEntity.ok(usuarioSalvo);
        }
        
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remover(@PathVariable Long id) {
		try {
			usuarioService.excluir(id);	
			return ResponseEntity.noContent().build();
			
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
			
		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
    }
}