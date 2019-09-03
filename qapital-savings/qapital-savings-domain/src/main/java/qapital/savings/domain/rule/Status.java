package qapital.savings.domain.rule;

public enum  Status {
    ACTIVE("ACTIVE"),
    DELETED("DELETED"),
    PAUSED("PAUSED");

    private String statusValue;

    Status(String statusValue) {
        this.statusValue = statusValue;
    }

    public String getStatusValue() {
        return statusValue;
    }
}
