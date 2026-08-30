package com.markus.produtosapi.repository;

import com.markus.produtosapi.model.Produto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Testes de integração do ProdutoRepository, utilizando @DataJpaTest.
 * Sobe apenas a camada de persistência (JPA) com o banco H2 em memória
 * configurado no projeto, validando as consultas derivadas do Spring Data JPA.
 */
@DataJpaTest
class ProdutoRepositoryTest {

    @Autowired
    private ProdutoRepository produtoRepository;

    private Produto notebook;
    private Produto mouse;

    @BeforeEach
    void setUp() {
        produtoRepository.deleteAll();

        notebook = new Produto();
        notebook.setId(UUID.randomUUID().toString());
        notebook.setNome("Notebook Gamer");
        notebook.setDescricao("Notebook com placa de vídeo dedicada e SSD");
        notebook.setPreco(6500.00);

        mouse = new Produto();
        mouse.setId(UUID.randomUUID().toString());
        mouse.setNome("Mouse sem fio");
        mouse.setDescricao("Mouse ergonômico com bateria recarregável");
        mouse.setPreco(150.00);

        produtoRepository.save(notebook);
        produtoRepository.save(mouse);
    }

    @Test
    void deveSalvarERecuperarProdutoPorId() {
        Optional<Produto> encontrado = produtoRepository.findById(notebook.getId());

        assertTrue(encontrado.isPresent());
        assertEquals("Notebook Gamer", encontrado.get().getNome());
        assertEquals(6500.00, encontrado.get().getPreco());
    }

    @Test
    void deveListarTodosOsProdutosCadastrados() {
        List<Produto> produtos = produtoRepository.findAll();

        assertEquals(2, produtos.size());
    }

    @Test
    void deveBuscarProdutosPorNomeContendoTermo() {
        List<Produto> resultado = produtoRepository.findByNomeContaining("Notebook");

        assertEquals(1, resultado.size());
        assertEquals(notebook.getId(), resultado.get(0).getId());
    }

    @Test
    void deveBuscarProdutosPorDescricaoContendoTermo() {
        List<Produto> resultado = produtoRepository.findByDescricaoContaining("bateria");

        assertEquals(1, resultado.size());
        assertEquals(mouse.getId(), resultado.get(0).getId());
    }

    @Test
    void naoDeveEncontrarProdutosQuandoTermoDeBuscaNaoCorresponder() {
        List<Produto> resultado = produtoRepository.findByNomeContaining("Teclado");

        assertTrue(resultado.isEmpty());
    }

    @Test
    void deveAtualizarUmProdutoExistente() {
        notebook.setPreco(5999.00);
        produtoRepository.save(notebook);

        Optional<Produto> atualizado = produtoRepository.findById(notebook.getId());

        assertTrue(atualizado.isPresent());
        assertEquals(5999.00, atualizado.get().getPreco());
    }

    @Test
    void deveRemoverUmProdutoPorId() {
        produtoRepository.deleteById(mouse.getId());

        Optional<Produto> removido = produtoRepository.findById(mouse.getId());

        assertFalse(removido.isPresent());
        assertEquals(1, produtoRepository.findAll().size());
    }
}