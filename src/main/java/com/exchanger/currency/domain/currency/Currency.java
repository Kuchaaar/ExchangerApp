package com.exchanger.currency.domain.currency;

import com.exchanger.currency.integration.currency.CurrencyResponse;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "currency")
public class Currency{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "currency_id")
    private Long id;
    private String currency;
    private String code;
    private BigDecimal mid;
    private LocalDate date;

    public Currency(String currency, String code, BigDecimal mid, LocalDate date){
        this.currency = currency;
        this.code = code;
        this.mid = mid;
        this.date = date;
    }
    public Currency(Long id, String currency, String code, BigDecimal mid, LocalDate date){
        this.id = id;
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

    @Override
    public boolean equals(Object o){
        if(this == o)
            return true;
        if(! (o instanceof Currency currency1))
            return false;
        return Objects.equals(getId(), currency1.getId()) &&
                Objects.equals(getCurrency(), currency1.getCurrency()) &&
                Objects.equals(getCode(), currency1.getCode()) &&
                Objects.equals(getMid(), currency1.getMid()) &&
                Objects.equals(getDate(), currency1.getDate());
    }

    @Override
    public int hashCode(){
        return Objects.hash(getId(), getCurrency(), getCode(), getMid(), getDate());
    }

    @Override
    public String toString(){
        return "Currency{" +
                "id=" + id +
                ", currency='" + currency + '\'' +
                ", code='" + code + '\'' +
                ", mid=" + mid +
                ", date=" + date +
                '}';
    }
}