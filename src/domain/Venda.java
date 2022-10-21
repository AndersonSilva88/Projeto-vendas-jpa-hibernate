package domain;

import dao.Persistence;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
@Table(name = "TB_VENDA")
public class Venda implements Persistence {

    public enum Status {
        INICIADA, CONCLUIDA, CANCELADA;

        public static Status getByName(String value) {
            for (Status status : Status.values()) {
                if (status.name().equals(value)) {
                    return status;
                }
            }
            return null;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "venda_seq")
    @SequenceGenerator(name = "venda_seq", sequenceName = "sq_venda", initialValue = 1, allocationSize = 1)
    private Long id;
    @Column(name = "CODIGO", nullable = false, unique = true)
    private String codigo;
    @ManyToOne
    @JoinColumn(name = "id_cliente_fk", foreignKey = @ForeignKey(name = "fk_venda_cliente"), referencedColumnName = "id", nullable = false)
    private Cliente cliente;
    @OneToMany(mappedBy = "venda", cascade = CascadeType.ALL)
    private Set<ProdutoQuantidade> produtos;
    @Column(name = "VALOR_TOTAL", nullable = false)
    private BigDecimal valorTotal;
    @Column(name = "DATA_VENDA", nullable = false)
    private Instant dataVenda;
    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS_VENDA", nullable = false)
    private Status status;

    public Venda() {
        produtos = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Set<ProdutoQuantidade> getProdutos() {
        return produtos;
    }

    public void adicionarProdutos(Produto produto, Integer quantidade) {
        validarStatus();
        Optional<ProdutoQuantidade> op =
                produtos.stream().filter(filter -> filter.getProduto().getCodigo()
                        .equals(produto.getCodigo())).findAny();
        if (op.isPresent()) {
            ProdutoQuantidade produtoQuantidade  = op.get();
            produtoQuantidade.adicionar(quantidade);
        } else {
            ProdutoQuantidade produtoQuantidade = new ProdutoQuantidade();
            produtoQuantidade.setVenda(this);
            produtoQuantidade.setProduto(produto);
            produtoQuantidade.adicionar(quantidade);
            produtos.add(produtoQuantidade);
        }
        recalcularValorTotalVenda();

    }

    public void validarStatus() {
        if (this.status == Status.CONCLUIDA) {
            throw new UnsupportedOperationException("IMPOSSIVEL ALTERAR VENDA FINALIZADA");
        }
    }

    public void removerProduto(Produto produto, Integer quantidade) {
        validarStatus();
        Optional<ProdutoQuantidade> op =
                produtos.stream().filter(filter -> filter.getProduto().getId().equals(produto.getCodigo())).findAny();
        if (op.isPresent()) {
            ProdutoQuantidade produtoQuantidade = op.get();
            if (produtoQuantidade.getQuantidade()>quantidade) {
                produtoQuantidade.remover(quantidade);
                recalcularValorTotalVenda();
            } else  {
                produtos.remove(op.get());
                recalcularValorTotalVenda();
            }
        }
    }

    public void removerTodosProdutos() {
        validarStatus();
        produtos.clear();
        valorTotal = BigDecimal.ZERO;

    }

    public Integer getQuantidadeTotalProdutos() {
        int result = produtos.stream().reduce(0, (partialCountResult, produtos) -> partialCountResult + produtos.getQuantidade(), Integer::sum);
        return result;
    }

    public void recalcularValorTotalVenda() {
        BigDecimal valorTotal = BigDecimal.ZERO;
        for (ProdutoQuantidade produtoQuantidade : this.produtos) {
            valorTotal = valorTotal.add(produtoQuantidade.getValorTotal());
        }
        this.valorTotal = valorTotal;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Instant getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(Instant dataVenda) {
        this.dataVenda = dataVenda;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setProdutos(Set<ProdutoQuantidade> produtos) {
        this.produtos = produtos;
    }
}
