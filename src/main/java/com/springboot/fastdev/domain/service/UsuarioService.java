package com.springboot.fastdev.domain.service;

import com.springboot.fastdev.domain.exception.EntidadeNaoEncontradaException;
import com.springboot.fastdev.domain.exception.EntidadeEmUsoException;
import com.springboot.fastdev.domain.model.Usuario;
import com.springboot.fastdev.domain.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> pesquisar(Long id) {
        return usuarioRepository.findById(id);
    }

    public Usuario salvar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public void excluir(Long id) {
    	try {
    		usuarioRepository.deleteById(id);    		
    	} catch(EmptyResultDataAccessException erro) {
    		throw new EntidadeNaoEncontradaException(
    			String.format("Não existe um usuário com o id %d", id)
    		);    	
    	} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
				String.format("Usuario com o id %d não pode ser removido, pois está em uso", id)
			);
		}
    }
}