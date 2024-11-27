package app.labs.mybatis.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class FeePolicy {
	private int feePolicyId;
	private Double smallDiscount;
	private Double mediumDiscount;
	private Double largeDiscount;
	private Double disabilityDiscount;
	private Double notDisabilityDiscount;
	private int costPerDistance;
}
