package qapital.savings.domain.rule;

import java.io.Serializable;
import static java.util.Objects.isNull;

/**
 * The core configuration object for a Savings Rule.
 */
public class SavingsRule implements Serializable {

    private static final long serialVersionUID = 1L;

    private Double      amount;
    private Long        id;
    private String      description;
    private RuleType    ruleType;
    private Long        savingsGoalId;
    private Status      status;
    private Long        userId;

    private SavingsRule(Builder builder) {
        this.amount = builder.amount;
        this.id = builder.id;
        this.description = builder.description;
        this.ruleType = builder.ruleType;
        this.savingsGoalId = builder.savingsGoalId;
        this.status = isNull(builder.status) ? Status.ACTIVE : builder.status;
        this.userId = builder.userId;
    }

    private SavingsRule() {
    }

    public static Builder builder() {
        return new Builder();
    }

    public Double getAmount() {
        return amount;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public RuleType getRuleType() {
        return ruleType;
    }

    public Long getSavingsGoalId() {
        return savingsGoalId;
    }

    public Status getStatus() {
        return status;
    }

    public Long getUserId() {
        return userId;
    }

    public static class Builder implements org.apache.commons.lang3.builder.Builder<SavingsRule> {

        private Double amount;
        private Long id;
        private String description;
        private RuleType ruleType;
        private Long  savingsGoalId;
        private Status status;
        private Long userId;

        public Builder withAmount(Double amount) {
            this.amount = amount;
            return this;
        }

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withRuleType(RuleType ruleType) {
            this.ruleType = ruleType;
            return this;
        }

        public Builder withSavingGoalId(Long savingGoalId) {
            this.savingsGoalId = savingGoalId;
            return this;
        }

        public Builder withStatus(Status status) {
            this.status = status;
            return this;
        }

        public Builder withUserId(Long userId) {
            this.userId = userId;
            return this;
        }

        @Override
        public SavingsRule build() {
            return new SavingsRule(this);
        }
    }
}
