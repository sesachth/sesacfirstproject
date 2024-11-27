package app.labs.mybatis.model;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class OutputResult {
	String name;
	CarType carType;
	int distance;
	int notpaidmoney;
	int calcpaidmoney;
	List<Tollgate> tollgatemap;
}
