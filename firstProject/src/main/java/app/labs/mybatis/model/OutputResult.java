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
	private String name;
	private CarType carType;
	private int distance;
	private int notpaidmoney;
	private int calcpaidmoney;
	private List<Tollgate> tollgatemap;
}
