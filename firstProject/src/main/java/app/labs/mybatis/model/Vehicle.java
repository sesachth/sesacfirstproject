package app.labs.mybatis.model;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Vehicle {
	int vehicleId;
	int accountId;
	String vehicleNumber;
	String vehicleType;
	boolean hasDisability;
}
