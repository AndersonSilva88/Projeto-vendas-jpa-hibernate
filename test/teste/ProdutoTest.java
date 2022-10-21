package teste;

import dao.IProdutoDao;
import dao.ProdutoDao;
import domain.Produto;
import exceptions.DAOException;
import exceptions.MaisDeUmRegistroException;
import exceptions.TableException;
import exceptions.TipoChaveNaoEncontradaException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collection;

import static org.junit.Assert.*;

public class ProdutoTest {

    private IProdutoDao produtoDao;

    public ProdutoTest() {
        this.produtoDao = new ProdutoDao();
    }

    @After
    public void end() throws DAOException {
        Collection<Produto> list = produtoDao.buscarTodos();
        list.forEach(cli -> {
            try {
                produtoDao.excluir(cli);
            } catch (DAOException e) {
                e.printStackTrace();
            }
        });
    }

    @Test
    public void pesquisar() throws MaisDeUmRegistroException, TableException, DAOException, TipoChaveNaoEncontradaException {
        Produto produto = criarProduto("D1");
        assertNotNull(produto);
        Produto produtoDB = this.produtoDao.consultar(produto.getId());
        assertNotNull(produtoDB);
    }

    @Test
    public void salvar() throws TipoChaveNaoEncontradaException, DAOException {
        Produto produto = criarProduto("D2");
        assertNotNull(produto);
    }

    @Test
    public void excluir() throws DAOException, TipoChaveNaoEncontradaException, MaisDeUmRegistroException, TableException {
        Produto produto = criarProduto("D3");
        assertNotNull(produto);
        this.produtoDao.excluir(produto);
        Produto produtoDB = this.produtoDao.consultar(produto.getId());
        assertNull(produtoDB);
    }

    @Test
    public  void alterarProduto() throws TipoChaveNaoEncontradaException, DAOException, MaisDeUmRegistroException, TableException {
        Produto produto = criarProduto("D4");
        produto.setNome("Notebook AlienWare");
        produtoDao.alterar(produto);
        Produto produtoDB = this.produtoDao.consultar(produto.getId());
        assertNotNull(produtoDB);
        Assert.assertEquals("Notebook AlienWare", produtoDB.getNome());
    }

    @Test
    public void buscarTodos() throws DAOException, TipoChaveNaoEncontradaException {
        criarProduto("D5");
        criarProduto("D6");
        Collection<Produto> list = produtoDao.buscarTodos();
        assertTrue(list != null);
        assertTrue(list.size() == 2);

        for (Produto pro : list) {
            this.produtoDao.excluir(pro);
        }

        list = produtoDao.buscarTodos();
        assertTrue(list != null);
        assertTrue(list.size() == 0);
    }

    private Produto criarProduto(String codigo) throws TipoChaveNaoEncontradaException, DAOException {
        Produto produto = new Produto();
        produto.setCodigo(codigo);
        produto.setDescricao("Notebook I9 13900H, RTX 4090M, 32RAM DDR5");
        produto.setNome("Notebook Dell");
        produto.setValor(BigDecimal.TEN);
        produtoDao.cadastrar(produto);
        return produto;
    }
 }
