package com.exchanger.currency.domain.currency;

import com.exchanger.currency.integration.currency.CurrencyResponse;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "currency")
public class Currency{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "currency_id")
    private Long id;
    private String currency;
    private String code;
    private Double mid;
    private LocalDate date;

    public Currency(String currency, String code, Double mid, LocalDate date){
        this.currency = currency;
        this.code = code;
        this.mid = mid;
        this.date = date;
    }

    public Currency(){

    }

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

    public static Currency from(CurrencyResponse currencyResponse){
        return new Currency(currencyResponse.currency(),
                currencyResponse.code(),
                currencyResponse.mid(),
                currencyResponse.date());
    }
}