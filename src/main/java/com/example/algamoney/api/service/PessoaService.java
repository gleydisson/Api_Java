package com.example.algamoney.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.algamoney.api.model.Pessoa;
import com.example.algamoney.api.repository.PessoaRepository;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	/*public Pessoa atualizar( Long codigo, Pessoa pessoa) {	
		Pessoa pessoaSalva = buscarPessoaCodigo(codigo);
			  return this.pessoaRepository.save(pessoaSalva);
	}
*/
	public Pessoa atualizar(Long codigo, Pessoa pessoa) {

		  Pessoa pessoaSalva = this.pessoaRepository.findById(codigo)
		      .orElseThrow(() -> new EmptyResultDataAccessException(1));

		  BeanUtils.copyProperties(pessoa, pessoaSalva, "codigo");

		  return this.pessoaRepository.save(pessoaSalva);
		}
		
	public void atualizarPropriedadeAtivo(Long codigo, Boolean ativo) {
		Pessoa pessoaSalva = buscarPessoaCodigo(codigo);
		pessoaSalva.setAtivo(ativo);
		pessoaRepository.save(pessoaSalva);
	}
		
		public Pessoa buscarPessoaCodigo(Long codigo) {
			Pessoa pessoaSalva = this.pessoaRepository.findById(codigo)
				      .orElseThrow(() -> new EmptyResultDataAccessException(1));

				  BeanUtils.copyProperties( pessoaSalva, "codigo");
			return pessoaSalva;
		}

		
	
	
}
