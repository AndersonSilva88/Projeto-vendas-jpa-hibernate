package teste;

import dao.ClienteDao;
import dao.IClienteDao;
import domain.Cliente;
import exceptions.DAOException;
import exceptions.MaisDeUmRegistroException;
import exceptions.TableException;
import exceptions.TipoChaveNaoEncontradaException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.Table;
import java.util.Collection;
import java.util.Random;

import static org.junit.Assert.assertTrue;

public class ClienteTest {

    private IClienteDao clienteDao;

    private Random rs;

    public ClienteTest() {
        this.clienteDao = new ClienteDao();
        rs = new Random();
    }

    @After
    public void end() throws DAOException {
        Collection<Cliente> list = clienteDao.buscarTodos();
        list.forEach(cli -> {
            try {
                clienteDao.excluir(cli);
            } catch (DAOException e ) {
                e.printStackTrace();
            }
        });
    }

    @Test
    public void pesquisarCliente() throws TipoChaveNaoEncontradaException, DAOException, MaisDeUmRegistroException, TableException {
        Cliente cliente = criarCliente();
        clienteDao.cadastrar(cliente);

        Cliente clienteConsultado = clienteDao.consultar(cliente.getId());
        Assert.assertNotNull(clienteConsultado);
    }

    @Test
    public void salvarCliente() throws TipoChaveNaoEncontradaException, MaisDeUmRegistroException, TableException, DAOException {
        Cliente cliente = criarCliente();
        Cliente retorno = clienteDao.cadastrar(cliente);
        Assert.assertNotNull(retorno);

        Cliente clienteConsultado = clienteDao.consultar(retorno.getId());
        Assert.assertNotNull(clienteConsultado);

        clienteDao.excluir(cliente);

        Cliente clienteConsultado1 = clienteDao.consultar(retorno.getId());
        Assert.assertNull(clienteConsultado1);

    }

    @Test
    public void alterarCliente() throws TipoChaveNaoEncontradaException, MaisDeUmRegistroException, TableException, DAOException {
        Cliente cliente = criarCliente();
        Cliente retorno = clienteDao.cadastrar(cliente);
        Assert.assertNotNull(retorno);

        Cliente clienteConsultado = clienteDao.consultar(cliente.getId());
        Assert.assertNotNull(clienteConsultado);

        clienteConsultado.setNome("Anderson Silva");
        clienteDao.alterar(clienteConsultado);

        Cliente clienteAlterado = clienteDao.consultar(clienteConsultado.getId());
        Assert.assertNotNull(clienteAlterado);
        Assert.assertEquals("Anderson Silva", clienteAlterado.getNome());

        clienteDao.excluir(cliente);
        clienteConsultado = clienteDao.consultar(clienteAlterado.getId());
        Assert.assertNull(clienteConsultado);
    }


    @Test
    public void excluirCliente() throws TipoChaveNaoEncontradaException, MaisDeUmRegistroException, TableException, DAOException {
        Cliente cliente = criarCliente();
        Cliente retorno = clienteDao.cadastrar(cliente);
        Assert.assertNotNull(retorno);

        Cliente clienteConsultado =clienteDao.consultar(cliente.getId());
        Assert.assertNotNull(clienteConsultado);


        clienteDao.excluir(cliente);
        clienteConsultado = clienteDao.consultar(cliente.getId());
        Assert.assertNull(clienteConsultado);
    }

    @Test
    public void buscarTodos() throws TipoChaveNaoEncontradaException, DAOException {
        Cliente cliente = criarCliente();
        Cliente retorno = clienteDao.cadastrar(cliente);
        Assert.assertNotNull(retorno);

        Cliente cliente1 = criarCliente();
        Cliente retorno1 = clienteDao.cadastrar(cliente1);
        Assert.assertNotNull(retorno1);

        Collection<Cliente> list = clienteDao.buscarTodos();
        assertTrue(list != null);
        assertTrue(list.size() == 2);

        list.forEach(cli -> {
            try {
                clienteDao.excluir(cli);
            } catch (DAOException e) {
                e.printStackTrace();
            }
        });

        Collection<Cliente> lista = clienteDao.buscarTodos();
        assertTrue(lista != null);
        assertTrue(lista.size() == 0);
    }



    private Cliente criarCliente() {
        Cliente cliente = new Cliente();
        cliente.setCpf(rs.nextLong());
        cliente.setRg(rs.nextLong());
        cliente.setNome("anderson");
        cliente.setCidade("Curitiba");
        cliente.setEndereco("rua maua");
        cliente.setEstado("PR");
        cliente.setNumero(15);
        cliente.setTelefone(999999999L);
        return cliente;
    }
}
