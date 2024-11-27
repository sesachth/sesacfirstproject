package app.labs;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import app.labs.mybatis.dao.FeeSettlementRepository;
import app.labs.mybatis.model.FeeCalculationData;
import lombok.AllArgsConstructor;
import lombok.Getter;

@SpringBootTest
public class FeeSettlementRepositoryTest {
	
	@Autowired
	FeeSettlementRepository repository;

	@Test
	void testCase() {
		// given
		/*
		List<Tollgate> tollgateList = null;
		List<String> vehicleNumberList = null;
		OwnerAccount selectedAccount = null;
		int totalCost = -1;
		List<String> vehicleTypeList = null;
		List<FeeCalculationData> dataList = null;
		int updateCount = 0;
		int insertCount = 0;
		*/
		
		// when
		/*
		tollgateList = repository.getTollgateList();
		vehicleNumberList = repository.getVehicleNumberList("김민수");
		selectedAccount = repository.getAccount("박보영");
		totalCost = repository.getTotalCost("권지용");
		vehicleTypeList = repository.getVehicleType("김민수");
		dataList = repository.getFeeCalculationDataList("김민수");
		updateCount = repository.updateAccount("김민수", 1000, 1000);
		insertCount = repository.addFeeRecord(10000, "1212-12-12 12:12:12", "3434-11-11 11:34:34", "5656-10-10 10:56:56");
		*/
		
		// then
		/*
		assertThat(tollgateList).isNotNull().isNotEmpty();
		assertThat(vehicleNumberList).isNotNull().isNotEmpty();
		assertThat(selectedAccount).isNotNull();
		assertThat(totalCost).isEqualTo(0);
		assertThat(vehicleTypeList).isNotNull().isNotEmpty();
		assertThat(dataList).isNotNull().isNotEmpty();
		
		System.out.println(tollgateList);
		System.out.println(vehicleNumberList);
		System.out.println(selectedAccount);
		System.out.println(totalCost);
		System.out.println(vehicleTypeList);
		
		dataList.forEach((data) -> {	System.out.println(data);	});
		
		assertThat(updateCount).isEqualTo(1);
		assertThat(insertCount).isEqualTo(1);
		*/
	}
	
	@Test
	void testService() {
		/*
		// given
		@Getter
		@AllArgsConstructor
		class testPos {
			public int a;
			public int b;
		};
		
		List<testPos> posList = new ArrayList<>();
		posList.add(new testPos(0, 0));
		posList.add(new testPos(3, 4));
		
		BiFunction<Integer, Integer, Integer> complexOperation = (first, second) -> 
			(int)((Math.round(Math.sqrt(Math.pow(first, 2) + Math.pow(second, 2)))));
		
		// when
		int differA = posList.get(1).a - posList.get(0).a;
		int differB = posList.get(1).b - posList.get(0).b;
			
		int result = complexOperation.apply(differA, differB);
		
		// then
		assertThat(result).isEqualTo(5);
		*/
		
		// given
		BiFunction<Integer, Integer, Integer> complexOperation = (first, second) -> 
			(int)((Math.round(Math.sqrt(Math.pow(first, 2) + Math.pow(second, 2)))));
		
		List<FeeCalculationData> dataList = repository.getFeeCalculationDataList("박보영");
		
		// when
		Map<String, List<FeeCalculationData>> dataByVehicleNumber = dataList.stream()
				.collect(Collectors.groupingBy(FeeCalculationData::getVehicleNumber));
		Set<String> vehicleNumberSet = dataByVehicleNumber.keySet();	
		List<FeeCalculationData> tempList = dataByVehicleNumber.get(vehicleNumberSet.iterator().next());
		
		List<Integer> distanceResult = IntStream.range(0, 2)
				 .mapToObj(i -> {
					 int latitudeDifference = tempList.get(i).getLatitude() - tempList.get(i + 1).getLatitude();
					 int longitudeDifference = tempList.get(i).getLongitude() - tempList.get(i + 1).getLongitude();
					 
					 return complexOperation.apply(latitudeDifference, longitudeDifference);
				 })
				 .collect(Collectors.toList());
		
		System.out.println("소유차량 리스트: " + vehicleNumberSet +
				", 최종 주행거리: " + distanceResult.stream().mapToInt(Integer::intValue).sum());
		
		// then
	}
}
