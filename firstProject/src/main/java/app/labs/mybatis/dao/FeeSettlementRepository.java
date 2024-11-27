package app.labs.mybatis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import app.labs.mybatis.model.FeeCalculationData;
import app.labs.mybatis.model.FeePolicy;
import app.labs.mybatis.model.OwnerAccount;
import app.labs.mybatis.model.Tollgate;

@Mapper
public interface FeeSettlementRepository {
	List<Tollgate> getTollgateList();
	OwnerAccount getAccount(String accountName);
	List<FeeCalculationData> getFeeCalculationDataList(String accountName);
	
	int updateAccount(@Param("accountName") String accountName,
					  @Param("totalCostToPay") int totalCostToPay,
					  @Param("remainingBalance") int remainingBalance);
	
	int addFeeRecord(@Param("settledFee") int settledFee,
					 @Param("tollgateStartTime") String tollgateStartTime,
					 @Param("tollgateEndTime") String tollgateEndTime,
					 @Param("feeSettlementTime") String feeSettlementTime);
	
	FeePolicy getFeePolicy();
}
