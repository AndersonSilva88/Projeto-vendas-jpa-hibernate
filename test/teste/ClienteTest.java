package teste;

import dao.ClienteDao;
import dao.IClienteDao;
import domain.Cliente;
import exceptions.DAOException;
import exceptions.MaisDeUmRegistroException;
import exceptions.TableException;
import exceptions.TipoChaveNaoEncontradaException;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.Table;
import java.util.Random;

public class ClienteTest {

    private IClienteDao clienteDao;

    private Random rs;

    public ClienteTest() {
        this.clienteDao = new ClienteDao();
        rs = new Random();
    }

    @Test
    public void salvarCliente() throws TipoChaveNaoEncontradaException, MaisDeUmRegistroException, TableException, DAOException {
        Cliente cliente = criarCliente();
        Cliente retorno = clienteDao.cadastrar(cliente);

        Cliente clienteConsultado = clienteDao.consultar(retorno.getId());
        Assert.assertNotNull(clienteConsultado);

        clienteDao.excluir(cliente);

        Cliente clienteConsultado1 = clienteDao.consultar(retorno.getId());
        Assert.assertNull(clienteConsultado1);
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
