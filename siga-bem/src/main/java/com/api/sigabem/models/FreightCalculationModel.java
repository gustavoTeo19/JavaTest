package com.api.sigabem.models;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "TB_FREIGHT_CALCULATION")
public class FreightCalculationModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private Float peso;

    @Column(nullable = false)
    private String cepOrigem;

    @Column(nullable = false)
    private String cepDestino;

    @Column(nullable = false)
    private String nomeDestinatario;

    @Column(nullable = false)
    private LocalDateTime dataPrevistaEntrega;

    @Column(nullable = false)
    private LocalDateTime dataConsulta;

    @Column(nullable = false)
    private Float vlTotalFrete;

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

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

    public LocalDateTime getDataPrevistaEntrega() {
        return this.dataPrevistaEntrega;
    }

    public void setDataPrevistaEntrega(LocalDateTime dataPrevistaEntrega) {
        this.dataPrevistaEntrega = dataPrevistaEntrega;
    }

    public LocalDateTime getDataConsulta() {
        return this.dataConsulta;
    }

    public void setDataConsulta(LocalDateTime dataConsulta) {
        this.dataConsulta = dataConsulta;
    }

    public Float getVlTotalFrete() {
        return this.vlTotalFrete;
    }

    public void setVlTotalFrete(Float vlTotalFrete) {
        this.vlTotalFrete = vlTotalFrete;
    }

}
