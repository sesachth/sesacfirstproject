package app.labs.mybatis.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.labs.mybatis.dao.FeeSettlementRepository;
import app.labs.mybatis.model.BillResult;
import app.labs.mybatis.model.CarType;
import app.labs.mybatis.model.FeeCalculationData;
import app.labs.mybatis.model.FeePolicy;
import app.labs.mybatis.model.OutputResult;
import app.labs.mybatis.model.OwnerAccount;
import app.labs.mybatis.model.Tollgate;

@Service
public class BasicFeeSettlementService implements FeeSettlementService {

	@Autowired
	FeeSettlementRepository repository;
	
	@Override
	public List<FeeCalculationData> getSelectedDataList(String accountName) {
		List<FeeCalculationData> dataList = repository.getFeeCalculationDataList(accountName);
		
		Map<String, List<FeeCalculationData>> dataByVehicleNumber = dataList.stream()
				.collect(Collectors.groupingBy(FeeCalculationData::getVehicleNumber));
		
		Set<String> vehicleNumberSet = dataByVehicleNumber.keySet();	
		List<FeeCalculationData> selectedDataList = dataByVehicleNumber.get(vehicleNumberSet.iterator().next());
		
		int dataIndex = 0;
		for (FeeCalculationData data : selectedDataList) {
			if (!data.isEntry()) {
				dataIndex = selectedDataList.indexOf(data);
				break;
			}
		}
		
		selectedDataList.subList(dataIndex + 1, selectedDataList.size()).clear();
		return selectedDataList;
	}

	@Override
	public boolean loginAccount(String accountName) {
		OwnerAccount account = repository.getAccount(accountName);
		
        return account != null;
	}
	
	@Override
	public int getTotalCostFee(String accountName) {
		return repository.getAccount(accountName).getTotalCostToPay();
	}

	@Override
	public int getRemainingBalance(String accountName) {
		return repository.getAccount(accountName).getRemainingBalance();
	}
	
	@Override
	public CarType getCarType(List<FeeCalculationData> dataList) {
		FeeCalculationData data = dataList.get(0);
		CarType carType = null;
		
        if (data.getVehicleType().equals("소형")) {
            carType = CarType.소형;
        } else if (data.getVehicleType().equals("중형")) {
            carType = CarType.중형;
        } else {
            carType = CarType.대형;
        }
        
		return carType;
	}
	
	@Override
	public int getTotalDistance(List<FeeCalculationData> dataList) {
		BiFunction<Integer, Integer, Integer> complexOperation = (first, second) -> 
			(int)(Math.sqrt(Math.pow(first, 2) + Math.pow(second, 2)));
		
		List<Integer> distanceResult = IntStream.range(0, dataList.size() - 1)
			 .mapToObj(i -> {
				 int latitudeDifference = dataList.get(i).getLatitude() - dataList.get(i + 1).getLatitude();
				 int longitudeDifference = dataList.get(i).getLongitude() - dataList.get(i + 1).getLongitude();
				 
				 return complexOperation.apply(latitudeDifference, longitudeDifference);
			 })
			 .collect(Collectors.toList());
		
		return distanceResult.stream().mapToInt(Integer::intValue).sum();
	}

	@Override
	public int getSettlementFee(List<FeeCalculationData> dataList) {
		FeePolicy feePolicy = repository.getFeePolicy();
		int totalBasicFee = 0;
		
		// 1. 기본요금 합산
		totalBasicFee = dataList.stream()
								.mapToInt(FeeCalculationData::getBasicFee)
								.sum();

		// 2. 주행거리 비례 요금 계산
		int distanceFee = getTotalDistance(dataList) * feePolicy.getCostPerDistance();
		
		// 3. 최종 합산
		int settlementFee = totalBasicFee + distanceFee;
		
		// 4. 할인율 적용
		double discountRate = getDiscountRate(dataList.get(0).getVehicleType(),
											  dataList.get(0).isHasDisability());
		settlementFee = applyDiscountRate(settlementFee, discountRate);
		
		return settlementFee;
	}

	@Override
	public int applyDiscountRate(int settlementFee, double discountRate) {
		int resultFee = settlementFee;
		
		resultFee = resultFee - (int)(resultFee * discountRate);

		return resultFee;
	}
	
	@Override
	public double getDiscountRate(String vehicleType, boolean hasDisability) {
		FeePolicy feePolicy = repository.getFeePolicy();
		
		double discountRateByVehicleType = 0.0;
		double discountRateByDisability = 0.0;
		
		if (vehicleType.equals("소형")) {
			discountRateByVehicleType = feePolicy.getSmallDiscount();
		} else if (vehicleType.equals("중형")) {
			discountRateByVehicleType = feePolicy.getMediumDiscount();
		} else {
			discountRateByVehicleType = feePolicy.getLargeDiscount();
		}
		
		if (hasDisability) {
			discountRateByDisability = feePolicy.getDisabilityDiscount();
		} else {
			discountRateByDisability = feePolicy.getNotDisabilityDiscount();
		}
		
        return discountRateByVehicleType + discountRateByDisability;
	}
	
	@Override
	public String getSelectedVehicleType(List<FeeCalculationData> dataList) {
		return dataList.get(0).getVehicleType();
	}
	
	@Override
	public String getExitTollgateName(List<FeeCalculationData> dataList) {
		return dataList.get(dataList.size() - 1).getTollgateName();
	}
	
	@Override
	public List<Tollgate> getRouteMap(List<FeeCalculationData> dataList, String accountName) {
		List<Tollgate> tollgateList = new ArrayList<Tollgate>();
		
		for (FeeCalculationData data : dataList) {
			tollgateList.add(Tollgate.builder()
									 .tollgateName(data.getTollgateName())
									 .latitude(data.getLatitude())
									 .longitude(data.getLongitude())
									 .build());
		}
		
		return tollgateList;
	}
	
	@Override
    public String getSelectedVehicleNumber(List<FeeCalculationData> dataList) {
        return dataList.get(0).getVehicleNumber();
    }

    @Override
    public String getSelectedTollgateEntryTime(List<FeeCalculationData> dataList) {
        return dataList.get(0).getTollgateEntryTime();
    }

	@Override
	public OutputResult getOutputResult(String accountName) throws RuntimeException {
		List<FeeCalculationData> dataList = getSelectedDataList(accountName);
				
		return OutputResult.builder()
						   .name(accountName)
						   .carType(getCarType(dataList))
						   .distance(getTotalDistance(dataList))
						   .tollgatemap(getRouteMap(dataList, accountName))
						   .notpaidmoney(getTotalCostFee(accountName))
						   .calcpaidmoney(getSettlementFee(dataList))
						   .build();
	}

	@Override
	public BillResult getBillResult(String accountName) {
		List<FeeCalculationData> dataList = getSelectedDataList(accountName);
		
		return BillResult.builder()
						 .exitTollgateName(getExitTollgateName(dataList))
						 .vehicleNumber(getSelectedVehicleNumber(dataList))
						 .tollgateEntryTime(getSelectedTollgateEntryTime(dataList))
						 .vehicleType(getSelectedVehicleType(dataList))
						 .paymentAmount(Integer.toString(getSettlementFee(dataList)))
						 .remainingBalance(Integer.toString(getRemainingBalance(accountName)))
						 .unpaidAmount(Integer.toString(getTotalCostFee(accountName)))
						 .build();
	}
	
}
