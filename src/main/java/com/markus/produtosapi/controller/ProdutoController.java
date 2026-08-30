package com.markus.produtosapi.controller;


import com.markus.produtosapi.model.Produto;
import com.markus.produtosapi.repository.ProdutoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private ProdutoRepository produtoRepository;

    public ProdutoController(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @PostMapping
    public Produto salvar(@RequestBody Produto produto){
        System.out.println("Produto recebido: " + produto);
        var id = UUID.randomUUID().toString();
        produto.setId(id);
        produtoRepository.save(produto);
        return produto;
    }

    @GetMapping("/{id}")
    public Produto obterPorId(@PathVariable("id") String id){

        return produtoRepository.findById(id).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void deletarProduto(@PathVariable("id") String id){
        produtoRepository.deleteById(id);
    }

    @GetMapping
    public List<Produto> buscarTodos(){
        return produtoRepository.findAll();
    }

    @PutMapping("/{id}")
    public void atualizar(@PathVariable("id") String id, @RequestBody Produto produto){
        produto.setId(id);
        produtoRepository.save(produto);
    }

    @GetMapping("/filtro-nome")
    public List<Produto> filtroNome(@RequestParam("nome") String nome){
        return produtoRepository.findByNomeContaining(nome);
    }
    @GetMapping("/filtro-preco")
    public List<Produto> filtroPreco(@RequestParam("preco") Double preco){
        return produtoRepository.findByPrecoContaining(preco);
    }
    @GetMapping("/filtro-descricao")
    public List<Produto> filtroDescricao(@RequestParam("descricao") String descricao){
        return produtoRepository.findByDescricaoContaining(descricao);
    }
}
