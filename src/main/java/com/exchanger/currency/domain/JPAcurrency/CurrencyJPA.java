package com.exchanger.currency.domain.JPAcurrency;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;


@Entity
@Table(name = "Currency")
public class CurrencyJPA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "currency_id")
    private Long id;
    @NotNull
    @Column(name = "currency")
    private String currencyName;
    @NotNull
    @Column(name = "code")
    private String currencyCode;
    @NotNull
    @Column(name = "mid")
    private Double exchangeRate;

    @Column(name = "date")
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public Double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(Double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }
}

