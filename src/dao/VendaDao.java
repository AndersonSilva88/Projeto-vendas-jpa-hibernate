package dao;

import dao.generic.GenericDao;
import domain.Cliente;
import domain.Produto;
import domain.Venda;
import exceptions.DAOException;
import exceptions.TipoChaveNaoEncontradaException;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class VendaDao extends GenericDao<Venda, Long> implements IVendaDao {

    public VendaDao() {
        super(Venda.class);
    }

    @Override
    public void finalizarVenda(Venda venda) throws TipoChaveNaoEncontradaException, DAOException {
        super.alterar(venda);
    }

    @Override
    public void cancelarVenda(Venda venda) throws TipoChaveNaoEncontradaException, DAOException {
        super.alterar(venda);
    }

    @Override
    public void excluir(Venda entity) throws DAOException {
        throw new UnsupportedOperationException("OPERACÃO NÃO PERMITIDA");
    }

    @Override
    public Venda cadastrar(Venda entity) throws TipoChaveNaoEncontradaException, DAOException {
        try {
            openConnection();
            entity.getProdutos().forEach(prod -> {
                Produto produto = entityManager.merge(prod.getProduto());
                prod.setProduto(produto);
            });
            Cliente cliente = entityManager.merge(entity.getCliente());
            entity.setCliente(cliente);
            entityManager.persist(entity);
            entityManager.getTransaction().commit();
            closeConnection();
            return entity;
        } catch (Exception e) {
            throw new DAOException("ERRO SALVANDO VENDA", e);
        }
    }

    @Override
    public Venda consultarComCollection(Long id) {
        openConnection();

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Venda> query = builder.createQuery(Venda.class);
        Root<Venda> root = query.from(Venda.class);
        root.fetch("cliente");
        root.fetch("produtos");
        query.select(root).where(builder.equal(root.get("id"), id));
        TypedQuery<Venda> typedQuery =
                entityManager.createQuery(query);
        Venda venda = typedQuery.getSingleResult();
        closeConnection();
        return venda;
    }
}
