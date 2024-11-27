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
	private String exitTollgateName;
	private String vehicleNumber;
	private String tollgateEntryTime;
	private String vehicleType;
	private String paymentAmount;
	private String remainingBalance;
	private String unpaidAmount;
}
