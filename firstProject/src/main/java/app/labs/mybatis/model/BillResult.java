package app.labs.mybatis.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class BillResult {
	String exitTollgateName;
	String vehicleNumber;
	String tollgateEntryTime;
	String vehicleType;
	String paymentAmount;
	String remainingBalance;
	String unpaidAmount;
}
