package qapital.savings.domain.event;

import qapital.savings.domain.rule.RuleType;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

/**
 * A Savings Event represents an qapital.rule.domain.event in the history of a Savings Goal.
 * Events can be either monetary (triggered by the application of Savings Rules,
 * manual transfers, interest payments or incentive payouts), or other events
 * of significance in the history of the goal, such as pausing or unpausing
 * Savings Rules or other users joining or leaving a shared goal.
 */
public class SavingsEvent implements Serializable {

	private static final long serialVersionUID = 1L;

	private Double 			amount;
	private Boolean 		cancellation;
	private String 			created;
	private EventType 		eventType;
	private String 			date;
	private Long 			id;
	private Long 			savingsGoalId;
	private Long 			savingsTransferId;
	private Long 			savingsRuleId;
	private Long 			triggerId;
	private Long 			userId;
	private RuleType 		ruleType;

	private SavingsEvent(Builder builder) {
		this.amount = builder.amount;
		this.cancellation = builder.cancellation;
		this.created = builder.created;
		this.eventType = builder.eventType;
		this.date = builder.date;
		this.id = builder.id;
		this.savingsGoalId = builder.savingsGoalId;
		this.savingsRuleId = builder.savingsRuleId;
		this.triggerId = builder.triggerId;
		this.userId = builder.userId;
		this.ruleType = builder.ruleType;
		this.savingsTransferId = builder.savingsTransferId;
	}

	private SavingsEvent(){
	}

	public static Builder builder(){
		return new Builder();
	}

	public Boolean getCancellation() {
		return cancellation;
	}

	public String getCreated() {
		return created;
	}

	public EventType getEventType() {
		return eventType;
	}

	public String getDate() {
		return date;
	}

	public Long getId() {
		return id;
	}

	public Long getSavingsGoalId() {
		return savingsGoalId;
	}

	public Long getSavingsRuleId() {
		return savingsRuleId;
	}

	public Long getTriggerId() {
		return triggerId;
	}

	public Long getUserId() {
		return userId;
	}

	public RuleType getRuleType() {
		return ruleType;
	}

	public Long getSavingsTransferId() {
		return savingsTransferId;
	}

	public static class Builder implements org.apache.commons.lang3.builder.Builder<SavingsEvent>{

		private Double 		amount;
		private Boolean 	cancellation;
		private String 		created;
		private EventType	eventType;
		private String 		date;
		private Long 		id;
		private Long 		savingsGoalId;
		private Long 		savingsRuleId;
		private Long 		triggerId;
		private Long 		userId;
		private RuleType 	ruleType;
		private Long 		savingsTransferId;

		public Builder withAmount(Double amount){
			this.amount = amount;
			return this;
		}

		public Builder withCancellation(Boolean cancellation){
			this.cancellation = cancellation;
			return this;
		}

		public Builder withCreated(String created){
			this.created = created;
			return this;
		}

		public Builder withEventType(EventType eventType){
			this.eventType = eventType;
			return this;
		}

		public Builder withDate(String date){
			this.date = date;
			return this;
		}

		public Builder withId(Long id){
			this.id = id;
			return this;
		}

		public Builder withSavingsGoaldId(Long savingsGoalId){
			this.savingsGoalId = savingsGoalId;
			return this;
		}

		public Builder withSavingsRuleId(Long savingsRuleId){
			this.savingsRuleId = savingsRuleId;
			return this;
		}

		public Builder withTriggerId(Long triggerId){
			this.triggerId = triggerId;
			return this;
		}

		public Builder withUserId(Long userId){
			this.userId = userId;
			return this;
		}

		public Builder withRuleType(RuleType ruleType){
			this.ruleType = ruleType;
			return this;
		}

		public Builder withSavingTransferId(Long savingsTransferId){
			this.savingsTransferId = savingsTransferId;
			return this;
		}
		@Override
		public SavingsEvent build() {
			return new SavingsEvent(this);
		}
	}
	
	public double getAmount() {
		if (amount == null) {
			return 0d;
		} else {
			return amount;
		}
	}
}
