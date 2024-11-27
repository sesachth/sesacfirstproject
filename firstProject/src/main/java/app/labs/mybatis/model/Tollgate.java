package app.labs.mybatis.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class Tollgate {
	private int tollgateId;
	private String tollgateName;
	private int latitude;
	private int longitude;
	private int basicFee;
	private boolean isEntry;
}
