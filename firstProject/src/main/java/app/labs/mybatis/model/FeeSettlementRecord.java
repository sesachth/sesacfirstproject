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
	int feeRecordId;
	int vehicleId;
	int settledFee;
	String tollgateStartTime;
	String tollgateEndTime;
	String feeSettlementTime;
}
