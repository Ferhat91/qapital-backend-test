package qapital.savings.domain;

public enum TransactionType {
    MANUAL("MANUAL"),
    STARTED("STARTED"),
    STOPPED("STOPPED"),
    RULE_APPLICATION("RULE_APPLICATION"),
    IFTTT_TRANSFER("IFTTT_TRANSFER"),
    JOINED("JOINED"),
    WITHDRAWL("WITHDRAWL"),
    INTERNAL_TRANSFER("INTERNAL_TRANSFER"),
    CANCELLATION("CANCELLATION"),
    INCENTIVE_PAYOUT("INCENTIVE_PAYOUT"),
    INTEREST("INTEREST"),
    UNKNOWN("UNKNOWN");

    public String getTransactionTypeValue() {
        return transactionTypeValue;
    }

    private String transactionTypeValue;

    TransactionType(String transactionTypeValue) {
        this.transactionTypeValue = transactionTypeValue;
    }
}
