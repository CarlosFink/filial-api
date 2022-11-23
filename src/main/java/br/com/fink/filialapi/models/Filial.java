package br.com.fink.filialapi.models;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="Filial")
public class Filial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @NotEmpty
    @Column(name = "Nome", length = 50, nullable = false )
    private String nome;

    @NotEmpty
    @Column(name = "CNPJ", length = 14, nullable = false, unique = true)
    private String cnpj;

    @Column(name = "Cidade", length = 20)
    private String cidade;

    @NotEmpty    
    @Column(name = "UF", length = 2, nullable = false)
    private String uf;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "Tipo", length = 6, nullable = false)
    private ETipo tipo;

    @NotNull
    @Column(name = "Ativo", nullable = false)
    private Boolean ativo;

    @NotNull
    @Column(name = "DataCadastro", nullable = false)
    private Date dataCadastro;

    @Column(name = "UltimaAtualizacao")
    private Date ultimaAtualizacao;

    @Transient
    private String cnpjEditado;

    public Filial() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public ETipo getTipo() {
        return tipo;
    }

    public void setTipo(ETipo tipo) {
        this.tipo = tipo;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Date getUltimaAtualizacao() {
        return ultimaAtualizacao;
    }

    public void setUltimaAtualizacao(Date ultimaAtualizacao) {
        this.ultimaAtualizacao = ultimaAtualizacao;
    }

    public String getCnpjEditado() {
        Pattern pattern = Pattern.compile("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})");
        Matcher matcher = pattern.matcher(this.cnpj);
        if (matcher.find()) {
            cnpjEditado = matcher.replaceAll("$1.$2.$3/$4-$5");
        }
        return cnpjEditado;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Filial other = (Filial) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }      
}
