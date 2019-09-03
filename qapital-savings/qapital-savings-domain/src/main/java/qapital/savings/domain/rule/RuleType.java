package qapital.savings.domain.rule;

public enum RuleType {
    GUILTY_PLEASURE("GUILTY_PLEASURE"),
    ROUND_UP("ROUND_UP");

    private String ruleTypeValue;

    RuleType(String ruleTypeValue) {
        this.ruleTypeValue = ruleTypeValue;
    }

    public String getRuleTypeValue() {
        return ruleTypeValue;
    }


}
