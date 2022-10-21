package dao;

import dao.generic.GenericDao;
import domain.Produto;

public class ProdutoDao extends GenericDao<Produto, Long> implements IProdutoDao {

    public ProdutoDao() {
        super(Produto.class);
    }
}
