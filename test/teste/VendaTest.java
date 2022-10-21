package teste;

import dao.*;
import domain.Cliente;
import domain.Produto;
import domain.Venda;
import exceptions.DAOException;
import exceptions.MaisDeUmRegistroException;
import exceptions.TableException;
import exceptions.TipoChaveNaoEncontradaException;
import org.junit.Test;
import teste.dao.VendaExclusaoDao;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collection;
import java.util.Random;

import static org.junit.Assert.*;

public class VendaTest {

    private IVendaDao vendaDao;
    private IVendaDao vendaExclusaoDao;
    private IClienteDao clienteDao;
    private IProdutoDao produtoDao;
    private Random rd;
    private Cliente cliente;
    private Produto produto;

    public VendaTest() {
        this.vendaDao = new VendaDao();
        vendaExclusaoDao = new VendaExclusaoDao();
        this.clienteDao = new ClienteDao();
        this.produtoDao = new ProdutoDao();
        rd = new Random();
    }

    @Test
    public void salvar() throws TipoChaveNaoEncontradaException, DAOException, MaisDeUmRegistroException, TableException {
        Venda venda = criarVenda("A2");
        Venda retorno = vendaDao.cadastrar(venda);
        assertNotNull(retorno);

        assertTrue(venda.getValorTotal().equals(BigDecimal.valueOf(150000)));
        assertTrue(venda.getStatus().equals(Venda.Status.INICIADA));

        Venda vendaConsultada = vendaDao.consultar(venda.getId());
        assertTrue(vendaConsultada.getId() != null);
        assertEquals(venda.getCodigo(), vendaConsultada.getCodigo());

    }

    private void excluirProdutos() throws DAOException {
        Collection<Produto> list = this.produtoDao.buscarTodos();
        list.forEach(prod -> {
            try {
                this.produtoDao.excluir(prod);
            } catch (DAOException e) {
                e.printStackTrace();
            }
        });
    }

    private void excluirVendas() throws DAOException {
        Collection<Venda> list = this.vendaExclusaoDao.buscarTodos();
        list.forEach(prod -> {
            try {
                this.vendaExclusaoDao.excluir(prod);
            } catch (DAOException e) {
                e.printStackTrace();
            }
        });
    }


    private Produto cadastrarProduto(String codigo, BigDecimal valor) throws TipoChaveNaoEncontradaException, MaisDeUmRegistroException, TableException, DAOException {
        Produto produto = new Produto();
        produto.setCodigo(codigo);
        produto.setDescricao("Placa de video rtx 4090");
        produto.setNome("RTX 4090");
        produto.setValor(valor);
        produtoDao.cadastrar(produto);
        return produto;
    }

    private Cliente cadastrarCliente() throws TipoChaveNaoEncontradaException, DAOException {
        Cliente cliente = new Cliente();
        cliente.setCpf(rd.nextLong());
        cliente.setRg(rd.nextLong());
        cliente.setNome("anderson");
        cliente.setCidade("Curitiba");
        cliente.setEndereco("rua maua");
        cliente.setEstado("PR");
        cliente.setNumero(15);
        cliente.setTelefone(999999999L);
        return cliente;
    }

    private Venda criarVenda(String codigo) {
        Venda venda = new Venda();
        venda.setCodigo(codigo);
        venda.setDataVenda(Instant.now());
        venda.setCliente(this.cliente);
        venda.setStatus(Venda.Status.INICIADA);
        venda.adicionarProdutos(this.produto, 2);
        return venda;
    }
}
