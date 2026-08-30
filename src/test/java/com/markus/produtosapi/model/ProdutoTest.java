package com.markus.produtosapi.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Testes unitários "puros" da entidade Produto (sem contexto Spring),
 * cobrindo getters, setters e o método toString().
 */
class ProdutoTest {

    @Test
    void deveArmazenarERecuperarOsAtributosCorretamente() {
        Produto produto = new Produto();

        produto.setId("abc-123");
        produto.setNome("Notebook");
        produto.setDescricao("Notebook 16GB RAM, SSD 512GB");
        produto.setPreco(4500.00);

        assertEquals("abc-123", produto.getId());
        assertEquals("Notebook", produto.getNome());
        assertEquals("Notebook 16GB RAM, SSD 512GB", produto.getDescricao());
        assertEquals(4500.00, produto.getPreco());
    }

    @Test
    void toStringDeveConterOsDadosDoProduto() {
        Produto produto = new Produto();
        produto.setId("abc-123");
        produto.setNome("Notebook");
        produto.setDescricao("Notebook 16GB RAM, SSD 512GB");
        produto.setPreco(4500.00);

        String resultado = produto.toString();

        assertTrue(resultado.contains("abc-123"));
        assertTrue(resultado.contains("Notebook"));
        assertTrue(resultado.contains("4500.0"));
    }
}
