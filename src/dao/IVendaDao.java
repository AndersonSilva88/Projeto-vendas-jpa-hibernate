package dao;

import dao.generic.IGenericDao;
import domain.Venda;
import exceptions.DAOException;
import exceptions.TipoChaveNaoEncontradaException;

public interface IVendaDao extends IGenericDao<Venda, Long> {

    public void finalizarVenda(Venda venda) throws TipoChaveNaoEncontradaException, DAOException;

    public void cancelarVenda(Venda venda) throws TipoChaveNaoEncontradaException, DAOException;

    public Venda consultarComCollection(Long id);
}
