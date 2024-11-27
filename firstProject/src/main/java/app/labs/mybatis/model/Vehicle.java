package app.labs.mybatis.model;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Vehicle {
	private int vehicleId;
	private int accountId;
	private String vehicleNumber;
	private String vehicleType;
	private boolean hasDisability;
}
