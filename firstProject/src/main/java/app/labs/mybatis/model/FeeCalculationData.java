package app.labs.mybatis.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class FeeCalculationData {
	String tollgateEntryTime;
	String vehicleNumber;
	String vehicleType;
	boolean hasDisability;
	String tollgateName;
	int latitude;
	int longitude;
	int basicFee;
	boolean isEntry;
}
