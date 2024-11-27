package app.labs.mybatis.model;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class OwnerAccount {
	private int accountId;
	private String accountName;
	private int totalCostToPay;
	private int remainingBalance;
}
