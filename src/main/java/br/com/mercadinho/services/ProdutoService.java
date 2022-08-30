package br.com.mercadinho.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.mercadinho.model.Produto;
import br.com.mercadinho.model.exception.ResourceNotFoundException;
import br.com.mercadinho.repository.ProdutoRepository;
import br.com.mercadinho.shared.ProdutoDTO;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	/**
	 * Método para retornar uma lista de produtos
	 * @return lista de produtos.
	 * */
	public List<ProdutoDTO> obterTodos() {
		
		List<Produto> produtos =  produtoRepository.findAll();
		
		return produtos.stream()
				.map(produto -> new ModelMapper().map( produto, ProdutoDTO.class ) )
				.collect(Collectors.toList());
	}
	
	/**
	 * Método que retorna o produto encontrado pelo seu id.
	 * @param id do produto que será localizado.
	 * @return Retorna um produto caso tenha seja encontrado.
	 */
	public Optional<ProdutoDTO> obterPorId(Integer id) {
		Optional<Produto> produto = produtoRepository.findById(id) ;
		if(produto.isEmpty()) {
			throw new ResourceNotFoundException("Produto com id: " + id + " não encontrado");
		}
		
		ProdutoDTO produtoDto = new ModelMapper().map(produto.get(), ProdutoDTO.class);
		
		return Optional.of(produtoDto);
	}
	
	/**
	 * Método para adicionar produto na lista.
	 * @param produto que será adicionado.
	 * @return Retorna o produto que foi adicionado na lista.
	 */
	public ProdutoDTO adicionar(ProdutoDTO produtoDto) {
		
		produtoDto.setId(null);
		
		ModelMapper mapper = new ModelMapper();
		
		Produto produto = mapper.map(produtoDto, Produto.class);
		
		produto = produtoRepository.save(produto);
		
		produtoDto.setId(produto.getId());
		
		return produtoDto;
	}
	
	public void deletar(Integer id) {
		
		Optional<Produto> produto = produtoRepository.findById(id);
		
		if(produto.isEmpty()) {
			throw new ResourceNotFoundException("Não foi possível deletar o produto com o id: " + id + " - Produto não existe ");
		}
		
		produtoRepository.deleteById(id);
	}
	
	/**
	 * Método para atualizar o produto na lista.
	 * @param id do produto.
	 * @param produto que será atualizado;
	 * @return Retorna o produto após tualizar a lista;
	 */
	public ProdutoDTO atualizar( ProdutoDTO produtoDto) {
		
		
		
		return produtoRepository.saveAndFlush(produtoDto);
	}
}
