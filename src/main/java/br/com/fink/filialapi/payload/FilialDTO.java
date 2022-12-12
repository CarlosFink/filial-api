package br.com.fink.filialapi.payload;

import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.fink.filialapi.models.ETipo;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Filial")
public class FilialDTO {
    
    private Integer id;

    @NotEmpty
    private String nome;

    @NotEmpty
    private String cnpj;

    private String cidade;

    @NotEmpty
    private String uf;

    @NotNull
    private ETipo tipo;

    @NotNull
    private Boolean ativo;

    @NotNull
    private Date dataCadastro;

    private Date ultimaAtualizacao;
    
    private String cnpjEditado;

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
        return cnpjEditado;
    }
    public void setCnpjEditado(String cnpjEditado) {
        this.cnpjEditado = cnpjEditado;
    }    
}
