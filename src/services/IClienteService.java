package services;

import domain.Cliente;
import exceptions.DAOException;
import services.generic.IGenericService;

public interface IClienteService extends IGenericService<Cliente, Long> {

    public Cliente buscarPorCpf(Long cpf) throws DAOException;

}
