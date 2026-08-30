package com.markus.produtosapi.controller;

import com.markus.produtosapi.model.Produto;
import com.markus.produtosapi.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Testes de unidade da camada web (Controller), utilizando @WebMvcTest para
 * subir apenas o contexto MVC e MockMvc para simular as requisições HTTP.
 * O ProdutoRepository é substituído por um mock (@MockitoBean), garantindo
 * que apenas o comportamento do controller esteja sendo testado.
 *
 * Os corpos de requisição são enviados como JSON "cru" (String), evitando
 * depender diretamente de uma implementação específica do Jackson (2 ou 3).
 */
@WebMvcTest(ProdutoController.class)
class ProdutoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProdutoRepository produtoRepository;

    private Produto produto;

    @BeforeEach
    void setUp() {
        produto = new Produto();
        produto.setId("id-1");
        produto.setNome("Notebook");
        produto.setDescricao("Notebook 16GB RAM, SSD 512GB");
        produto.setPreco(4500.00);
    }

    @Test
    void deveCadastrarUmProduto() throws Exception {
        when(produtoRepository.save(any(Produto.class))).thenReturn(produto);

        String jsonRequisicao = """
                {
                    "nome": "Notebook",
                    "descricao": "Notebook 16GB RAM, SSD 512GB",
                    "preco": 4500.00
                }
                """;

        mockMvc.perform(post("/produtos")
                        .contentType("application/json")
                        .content(jsonRequisicao))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Notebook"))
                .andExpect(jsonPath("$.id").isNotEmpty());

        verify(produtoRepository, times(1)).save(any(Produto.class));
    }

    @Test
    void deveListarTodosOsProdutos() throws Exception {
        Produto outroProduto = new Produto();
        outroProduto.setId("id-2");
        outroProduto.setNome("Mouse");
        outroProduto.setDescricao("Mouse sem fio");
        outroProduto.setPreco(120.00);

        when(produtoRepository.findAll()).thenReturn(Arrays.asList(produto, outroProduto));

        mockMvc.perform(get("/produtos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].nome").value("Notebook"))
                .andExpect(jsonPath("$[1].nome").value("Mouse"));
    }

    @Test
    void deveBuscarProdutoPorIdQuandoExistir() throws Exception {
        when(produtoRepository.findById("id-1")).thenReturn(Optional.of(produto));

        mockMvc.perform(get("/produtos/id-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("id-1"))
                .andExpect(jsonPath("$.nome").value("Notebook"));
    }

    @Test
    void deveRetornarCorpoVazioQuandoProdutoNaoExistir() throws Exception {
        when(produtoRepository.findById("id-inexistente")).thenReturn(Optional.empty());

        mockMvc.perform(get("/produtos/id-inexistente"))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    void deveAtualizarUmProduto() throws Exception {
        String jsonRequisicao = """
                {
                    "nome": "Notebook",
                    "descricao": "Notebook 32GB RAM, SSD 1TB",
                    "preco": 5200.00
                }
                """;

        mockMvc.perform(put("/produtos/id-1")
                        .contentType("application/json")
                        .content(jsonRequisicao))
                .andExpect(status().isOk());

        verify(produtoRepository, times(1)).save(any(Produto.class));
    }

    @Test
    void deveDeletarUmProduto() throws Exception {
        mockMvc.perform(delete("/produtos/id-1"))
                .andExpect(status().isOk());

        verify(produtoRepository, times(1)).deleteById("id-1");
        verify(produtoRepository, never()).deleteById("outro-id");
    }

    @Test
    void deveFiltrarProdutosPorNome() throws Exception {
        when(produtoRepository.findByNomeContaining("Note")).thenReturn(List.of(produto));

        mockMvc.perform(get("/produtos/filtro-nome").param("nome", "Note"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].nome").value("Notebook"));
    }

    @Test
    void deveFiltrarProdutosPorDescricao() throws Exception {
        when(produtoRepository.findByDescricaoContaining("SSD")).thenReturn(List.of(produto));

        mockMvc.perform(get("/produtos/filtro-descricao").param("descricao", "SSD"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].descricao").value("Notebook 16GB RAM, SSD 512GB"));
    }

    @Test
    void deveFiltrarProdutosPorPreco() throws Exception {
        when(produtoRepository.findByPrecoContaining(4500.00)).thenReturn(List.of(produto));

        mockMvc.perform(get("/produtos/filtro-preco").param("preco", "4500.00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].preco").value(4500.00));
    }

    @Test
    void deveRetornarListaVaziaQuandoFiltroNaoEncontrarProdutos() throws Exception {
        when(produtoRepository.findByNomeContaining(anyString())).thenReturn(List.of());

        mockMvc.perform(get("/produtos/filtro-nome").param("nome", "Inexistente"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }
}