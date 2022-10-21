package dao;

import dao.generic.GenericDao;
import domain.Cliente;

public class ClienteDao  extends GenericDao<Cliente, Long> implements IClienteDao {

    public ClienteDao() {
        super(Cliente.class);
    }
}
