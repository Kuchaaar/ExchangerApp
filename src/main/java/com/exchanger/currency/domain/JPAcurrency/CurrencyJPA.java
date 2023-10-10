package com.exchanger.currency.domain.JPAcurrency;

import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
@Table(name = "Currency")
public class CurrencyJPA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "currency_id")
    private Long id;
    private String currency;
    private String code;
    private Double mid;
    @Column(name = "date")
    private LocalDate date;

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getCurrency(){
        return currency;
    }

    public void setCurrency(String currency){
        this.currency = currency;
    }

    public String getCode(){
        return code;
    }

    public void setCode(String code){
        this.code = code;
    }

    public Double getMid(){
        return mid;
    }

    public void setMid(Double mid){
        this.mid = mid;
    }

    public LocalDate getDate(){
        return date;
    }

    public void setDate(LocalDate date){
        this.date = date;
    }
}

