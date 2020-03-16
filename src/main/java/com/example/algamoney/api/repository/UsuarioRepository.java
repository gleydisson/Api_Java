package com.example.algamoney.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.algamoney.api.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

	public Optional<Usuario> findByEmail(String email);
	
	// Metodo para pesquisar os usuarios que tem permissao para receber email com os lancamentos vencidos.
	public List<Usuario> findByPermissoesDescricao(String permissaoDescricao);
	
}
