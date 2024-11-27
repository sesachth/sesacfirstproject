package app.labs.mybatis.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class FeeCalculationData {
	private String tollgateEntryTime;
	private String vehicleNumber;
	private String vehicleType;
	private boolean hasDisability;
	private String tollgateName;
	private int latitude;
	private int longitude;
	private int basicFee;
	private boolean isEntry;
}
