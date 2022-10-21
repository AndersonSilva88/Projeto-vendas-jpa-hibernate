package domain;

import dao.Persistence;

import javax.persistence.*;

@Entity
@Table(name = "TB_CLIENTE")
public class Cliente implements Persistence {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cliente_seq")
    @SequenceGenerator(name = "cliente_seq", sequenceName = "sq_cliente", initialValue = 1, allocationSize = 1)
    private Long id;
    @Column(name = "NOME", nullable = false, length = 50)
    private String nome;
    @Column(name = "CPF", nullable = false, unique = true)
    private Long cpf;
    @Column(name = "RG", nullable = false)
    private Long rg;
    @Column(name = "TELEFONE", nullable = false)
    private Long telefone;
    @Column(name = "ENDERECO", nullable = false, length = 100)
    private String endereco;
    @Column(name = "NUMERO", nullable = false)
    private Integer numero;
    @Column(name = "CIDADE", nullable = false, length = 100)
    private String cidade;
    @Column(name = "ESTADO", nullable = false, length = 50)
    private String estado;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getCpf() {
        return cpf;
    }

    public void setCpf(Long cpf) {
        this.cpf = cpf;
    }

    public Long getRg() {
        return rg;
    }

    public void setRg(Long rg) {
        this.rg = rg;
    }

    public Long getTelefone() {
        return telefone;
    }

    public void setTelefone(Long telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
