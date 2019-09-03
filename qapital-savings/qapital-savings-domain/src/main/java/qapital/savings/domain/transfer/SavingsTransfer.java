package qapital.savings.domain.transfer;

import java.io.Serializable;

public class SavingsTransfer implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long   id;
    private Long   savingsEventId;
    private String transactionExecutionTime;
    private Long   userId;

    private SavingsTransfer(Builder builder) {
        this.id = builder.id;
        this.userId = builder.userId;
        this.savingsEventId = builder.savingsEventId;
       this.transactionExecutionTime = builder.transactionExecutionTime;
    }

    private SavingsTransfer(){
    }

    public static Builder builder(){
        return new Builder();
    }

    public Long getId() {
        return id;
    }

    public String getTransactionExecutionTime() {
        return transactionExecutionTime;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getSavingsEventId() {
        return savingsEventId;
    }

    public static class Builder implements org.apache.commons.lang3.builder.Builder<SavingsTransfer>{

        private Long id;
        private Long savingsEventId;
        private String transactionExecutionTime;
        private Long userId;

        public Builder withId(Long id){
            this.id = id;
            return this;
        }

        public Builder withUserId(Long userId){
            this.userId = userId;
            return this;
        }

        public Builder withTransactionExecutionTime(String transactionExecutionTime){
            this.transactionExecutionTime = transactionExecutionTime;
            return this;
        }

        public Builder withSavingsEventId(Long savingsEventId){
            this.savingsEventId = savingsEventId;
            return this;
        }

        @Override
        public SavingsTransfer build() {
            return new SavingsTransfer(this);
        }
    }
}
