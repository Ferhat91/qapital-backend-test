package qapital.transactions.domain;

import java.io.Serializable;
import java.time.LocalDate;

public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;

    private Double      amount;
    private LocalDate   date;
    private String      placeDescription;
    private Long        id;
    private Long        userId;

    private Transaction(Builder builder) {
        this.amount = builder.amount;
        this.date = builder.date;
        this.placeDescription = builder.placeDescription;
        this.id = builder.id;
        this.userId = builder.userId;
    }

    private Transaction(){
    }

    public static Builder builder(){
        return new Builder();
    }

    public Double getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getPlaceDescription() {
        return placeDescription;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public static class Builder implements org.apache.commons.lang3.builder.Builder<Transaction>{

        private Double      amount;
        private LocalDate   date;
        private String      placeDescription;
        private Long        id;
        private Long        userId;


        public Builder withAmount(Double amount){
            this.amount = amount;
            return this;
        }

        public Builder withDate(LocalDate date){
            this.date = date;
            return this;
        }

        public Builder withPlaceDescription(String placeDescription){
            this.placeDescription = placeDescription;
            return this;
        }

        public Builder withId(Long id){
            this.id = id;
            return this;
        }

        public Builder withUserId(Long userId){
            this.userId = userId;
            return this;
        }

        @Override
        public Transaction build() {
            return new Transaction(this);
        }
    }

}
