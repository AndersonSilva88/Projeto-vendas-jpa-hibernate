package teste.dao;

import dao.IVendaDao;
import dao.generic.GenericDao;
import domain.Venda;
import exceptions.DAOException;
import exceptions.TipoChaveNaoEncontradaException;

public class VendaExclusaoDao extends GenericDao<Venda, Long> implements IVendaDao {

    public VendaExclusaoDao() {
        super(Venda.class);
    }

    @Override
    public void finalizarVenda(Venda venda) throws TipoChaveNaoEncontradaException, DAOException {
        throw new UnsupportedOperationException("OPERAÇÃO NÃO PERMITE");
    }

    @Override
    public void cancelarVenda(Venda venda) throws TipoChaveNaoEncontradaException, DAOException {
        throw new UnsupportedOperationException("OPERAÇÃO NÃO PERMITE");
    }

    @Override
    public Venda consularComCollection(Long id) {
        throw new UnsupportedOperationException("OPERAÇÃO NÃO PERMITE");
    }
}
