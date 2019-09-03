package qapital.savings.domain.goal;

import java.io.Serializable;

public class SavingsGoal implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String  description;
    private Long    id;
    private Double  requiredAmount;
    private Long    userId;
    private Long    savingsRuleId;
    private Long    savingsTransferId;

    private SavingsGoal(Builder builder) {
        this.description = builder.description;
        this.id = builder.id;
        this.requiredAmount = builder.requiredAmount;
        this.savingsRuleId = builder.savingsRuleId;
        this.savingsTransferId = builder.savingsTransferId;
        this.userId = builder.userId;
    }

    private SavingsGoal(){
    }

    public static Builder builder(){
        return new Builder();
    }

    public Long getSavingsRuleId() {
        return savingsRuleId;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getId() {
        return id;
    }

    public Long getSavingsTransferId() {
        return savingsTransferId;
    }

    public String getDescription() {
        return description;
    }

    public Double getRequiredAmount() {
        return requiredAmount;
    }

    public void setSavingsRuleId(Long savingsRuleId) {
        this.savingsRuleId = savingsRuleId;
    }

    public static class Builder implements org.apache.commons.lang3.builder.Builder<SavingsGoal> {

        private String  description;
        private Long    id;
        private Double  requiredAmount;
        private Long    userId;
        private Long    savingsRuleId;
        private Long    savingsTransferId;


        public Builder withUserId(Long userId){
            this.userId = userId;
            return this;
        }

        public Builder withId(Long id){
            this.id = id;
            return this;
        }

        public Builder withDescription(String description){
            this.description = description;
            return this;
        }

        public Builder withRequiredAmount(Double requiredAmount){
            this.requiredAmount = requiredAmount;
            return this;
        }

        public Builder withSavingsRuleId(Long savingsRuleId){
            this.savingsRuleId = savingsRuleId;
            return this;
        }

        public Builder withSavingsTransferId(Long savingsTransferId){
            this.savingsTransferId = savingsTransferId;
            return this;
        }

        @Override
        public SavingsGoal build() {
            return new SavingsGoal(this);
        }
    }
}
