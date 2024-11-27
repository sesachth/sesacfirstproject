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
    int feePolicyId;
    Double smallDiscount;
    Double mediumDiscount;
    Double largeDiscount;
    Double disabilityDiscount;
    Double notDisabilityDiscount;
    int costPerDistance;
}
