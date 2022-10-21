package services;

import dao.generic.IGenericDao;
import domain.Produto;
import services.generic.GenericService;

public class ProdutoService extends GenericService<Produto, String> implements IProdutoService {

    public ProdutoService(IGenericDao<Produto, String> dao) {
        super(dao);
    }
}
