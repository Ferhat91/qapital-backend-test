package qapital.savings.domain.rule;

import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.ObjectUtils.isEmpty;

/**
 * The core configuration object for a Savings Rule.
 */
public class SavingsRule implements Serializable {

	private static final long serialVersionUID = 1L;

	private Double  		amount;
	private Long 			id;
	private String  		placeDescription;
	private RuleType 		ruleType;
	private List<Long>  	savingsGoalIds;
	private Status 			status;
	private Long 			userId;

	private SavingsRule(Builder builder) {
		this.amount = builder.amount;
		this.id = builder.id;
		this.placeDescription = builder.placeDescription;
		this.ruleType = builder.ruleType;
		this.savingsGoalIds = isNull(builder.savingsGoalIds) ? Lists.newArrayList() : builder.savingsGoalIds;
		this.status = isNull(builder.status) ? Status.ACTIVE : builder.status;
		this.userId = builder.userId;
	}

	private SavingsRule(){
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

	public String getPlaceDescription() {
		return placeDescription;
	}

	public RuleType getRuleType() {
		return ruleType;
	}

	public List<Long> getSavingsGoalIds() {
		return savingsGoalIds;
	}

	public Status getStatus() {
		return status;
	}

	public Long getUserId() {
		return userId;
	}

	public static class Builder implements org.apache.commons.lang3.builder.Builder<SavingsRule>{

		private Double  		amount;
		private Long 				id;
		private String  		placeDescription;
		private RuleType 		ruleType;
		private List<Long>  savingsGoalIds;
		private Status 			status;
		private Long 				userId;

		public Builder withAmount(Double amount){
			this.amount = amount;
			return this;
		}

		public Builder withId(Long id){
			this.id = id;
			return this;
		}

		public Builder withPlaceDescription(String placeDescription){
			this.placeDescription = placeDescription;
			return this;
		}

		public Builder withRuleType(RuleType ruleType){
			this.ruleType = ruleType;
			return this;
		}

		public Builder withSavingGoalIds(Long... savingGoalIds){
			this.savingsGoalIds = Lists.newArrayList(savingGoalIds);
			return this;
		}

		public Builder withStatus(Status status){
			this.status = status;
			return this;
		}

		public Builder withUserId(Long userId){
			this.userId = userId;
			return this;
		}

		public void addSavingsGoal(Long savingsGoalId) {
			if (!savingsGoalIds.contains(savingsGoalId)) {
				savingsGoalIds.add(savingsGoalId);
			}
		}

		@Override
		public SavingsRule build() {
			return new SavingsRule(this);
		}
	}

	public Long consumeSavingsGoal() {
		if(isEmpty(savingsGoalIds)){
			Long consumedId = savingsGoalIds.get(0);
			savingsGoalIds.remove(consumedId);
			return consumedId;
		}
		return null;
	}


	public boolean isActive() {
		return Status.ACTIVE.equals(getStatus());
	}

}
