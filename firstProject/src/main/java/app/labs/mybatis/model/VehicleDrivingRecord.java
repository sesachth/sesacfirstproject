package app.labs.mybatis.model;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class VehicleDrivingRecord {
	private int recordId;
	private int tollgateId;
	private int vehicleId;
	private String tollgateEntryTime;
}
