package com.api.sigabem.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class FreightCalculationDto {

    @NotNull
    private Float peso;

    @NotBlank
    private String cepOrigem;

    @NotBlank
    private String cepDestino;

    @NotBlank
    private String nomeDestinatario;

    public Float getPeso() {
        return this.peso;
    }

    public void setPeso(Float peso) {
        this.peso = peso;
    }

    public String getCepOrigem() {
        return this.cepOrigem;
    }

    public void setCepOrigem(String cepOrigem) {
        this.cepOrigem = cepOrigem;
    }

    public String getCepDestino() {
        return this.cepDestino;
    }

    public void setCepDestino(String cepDestino) {
        this.cepDestino = cepDestino;
    }

    public String getNomeDestinatario() {
        return this.nomeDestinatario;
    }

    public void setNomeDestinatario(String nomeDestinatario) {
        this.nomeDestinatario = nomeDestinatario;
    }

}
