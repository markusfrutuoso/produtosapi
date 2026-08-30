package com.markus.produtosapi.repository;

import com.markus.produtosapi.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, String> {

    List<Produto> findByNomeContaining(String nome);
    List<Produto> findByPrecoContaining(Double preco);
    List<Produto> findByDescricaoContaining(String descricao);
}
