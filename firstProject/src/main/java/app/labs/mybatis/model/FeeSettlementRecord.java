package app.labs.mybatis.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class FeeSettlementRecord {
	private int feeRecordId;
	private int vehicleId;
	private int settledFee;
	private String tollgateStartTime;
	private String tollgateEndTime;
	private String feeSettlementTime;
}
