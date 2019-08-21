package qapital.savings.domain;

import java.io.Serializable;

public class Transaction implements Serializable{

    private static final long serialVersionUID = 1L;

    private Double      amount;
    private String      executionTime;
    private String      description;
    private Long        id;
    private Long        userId;

    private Transaction(Builder builder) {
        this.amount = builder.amount;
        this.executionTime = builder.executionTime;
        this.description = builder.description;
        this.id = builder.id;
        this.userId = builder.userId;
    }

    private Transaction(){
    }

    public String getDescription() {
        return description;
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

    public static class Builder implements org.apache.commons.lang3.builder.Builder<Transaction>{

        private Double      amount;
        private String      executionTime;
        private String      description;
        private Long        id;
        private Long        userId;

        public Builder withAmount(Double amount){
            this.amount = amount;
            return this;
        }

        public Builder withDescription(String description){
            this.description = description;
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
