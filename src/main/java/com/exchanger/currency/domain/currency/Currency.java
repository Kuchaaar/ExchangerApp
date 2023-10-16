package com.exchanger.currency.domain.currency;

import com.exchanger.currency.integration.currency.CurrencyResponse;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "currency")
public class Currency{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "currency_id")
    private Long id;
    private String name;
    private String code;
    private BigDecimal mid;
    private LocalDate date;

    public Currency(String name, String code, BigDecimal mid, LocalDate date){
        this.name = name;
        this.code = code;
        this.mid = mid;
        this.date = date;
    }
    public Currency(Long id, String name, String code, BigDecimal mid, LocalDate date){
        this.id = id;
        this.name = name;
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

    public String getName(){
        return name;
    }

    public void setName(String currency){
        this.name = currency;
    }

    public String getCode(){
        return code;
    }

    public void setCode(String code){
        this.code = code;
    }

    public BigDecimal getMid(){
        return mid;
    }

    public void setMid(BigDecimal mid){
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