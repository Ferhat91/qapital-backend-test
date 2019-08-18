package qapital.transactions.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

public class Transaction implements Serializable{

    private static final long serialVersionUID = 1L;

    private Double      amount;
    private String      executionTime;
    private String      purchaseDescription;
    private Long        id;
    private Long        userId;

    private Transaction(Builder builder) {
        this.amount = builder.amount;
        this.executionTime = builder.executionTime;
        this.purchaseDescription = builder.purchaseDescription;
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

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getExecutionTime() {
        return executionTime;
    }

    public String getPurchaseDescription() {
        return purchaseDescription;
    }

    public static class Builder implements org.apache.commons.lang3.builder.Builder<Transaction>{

        @JsonProperty       ("amount")
        private Double      amount;

        @JsonProperty       ("executionTime")
        private String      executionTime;

        @JsonProperty       ("purchaseDescription")
        private String      purchaseDescription;

        @JsonProperty       ("id")
        private Long        id;

        @JsonProperty       ("userId")
        private Long        userId;

        public Builder withAmount(Double amount){
            this.amount = amount;
            return this;
        }

        public Builder withPurchaseDescription(String purchaseDescription){
            this.purchaseDescription = purchaseDescription;
            return this;
        }

        public Builder withExecutionTime(String executionTime){
            this.executionTime = executionTime;
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
