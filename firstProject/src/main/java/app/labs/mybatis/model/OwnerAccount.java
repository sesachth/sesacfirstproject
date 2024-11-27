package app.labs.mybatis.model;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class OwnerAccount {
	int accountId;
	String accountName;
	int totalCostToPay;
	int remainingBalance;
}
