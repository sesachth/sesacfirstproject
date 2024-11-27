package app.labs.mybatis.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class Tollgate {
	int tollgateId;
	String tollgateName;
	int latitude;
	int longitude;
	int basicFee;
	boolean isEntry;
}
