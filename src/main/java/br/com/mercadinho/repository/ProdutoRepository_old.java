package br.com.mercadinho.repository;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.mercadinho.model.Produto;
import br.com.mercadinho.model.exception.ResourceNotFoundException;


@Repository
public class ProdutoRepository_old  {
	
	private ArrayList<Produto> produtos = new ArrayList<br.com.mercadinho.model.Produto>();
	private Integer ultimoId = 0;;
	
	/**
	 * Método para retornar uma lista de produtos
	 * @return lista de produtos.
	 * */
	public List<Produto> obterTodos(){
		return produtos;
	};
	
	/**
	 * Método que retorna o produto encontrado pelo seu id.
	 * @param id do produto que será localizado.
	 * @return Retorna um produto caso tenha seja encontrado.
	 */
	public Optional<Produto > obterPorId(Integer id) {
		return produtos.stream()
				.filter(produto -> produto.getId() == id)
				.findFirst();
	}
	
	/**
	 * Método para adicionar produto na lista.
	 * @param produto que será adicionado.
	 * @return Retorna o produto que foi adicionado na lista.
	 */
	public Produto adicionar(Produto produto) {
		
		ultimoId++;
		
		produto.setId(ultimoId);
		produtos.add(produto);
		
		return produto;
	}
	
	/**
	 * Método para deletar o produto por id.
	 * @param id do produto a ser deletado.
	 */
	public void deletar(Integer id) {
		produtos.removeIf(produto -> produto.getId() == id);
	}
	
	/**
	 * Método para atualizar o produto na lista.
	 * @param produto que será atualizado.
	 * @return Retorna o produto após atualizar a lista.
	 */
	public Produto atualizar(Produto produto) {
		
		Optional<Produto> produtoEncontrado = obterPorId(produto.getId());
		
		if(produtoEncontrado.isEmpty() ) {
			throw new ResourceNotFoundException("Produto náo encontrado");
		}
		
		deletar(produto.getId());
		
		produtos.add(produto);
		
		return produto;
	}
}
