package app.labs.mybatis.model;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class VehicleDrivingRecord {
	int recordId;
	int tollgateId;
	int vehicleId;
	String tollgateEntryTime;
}
