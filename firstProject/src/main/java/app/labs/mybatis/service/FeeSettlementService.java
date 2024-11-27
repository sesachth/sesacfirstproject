package app.labs.mybatis.service;

import java.util.List;

import app.labs.mybatis.model.BillResult;
import app.labs.mybatis.model.CarType;
import app.labs.mybatis.model.FeeCalculationData;
import app.labs.mybatis.model.OutputResult;
import app.labs.mybatis.model.Tollgate;

public interface FeeSettlementService {
	public List<FeeCalculationData> getSelectedDataList(String accountName);
	public boolean loginAccount(String accountName);
	public int getTotalCostFee(String accountName);
	public int getRemainingBalance(String accountName);
	
	public CarType getCarType(List<FeeCalculationData> dataList);
	public int getTotalDistance(List<FeeCalculationData> dataList);
	public int getSettlementFee(List<FeeCalculationData> dataList);
	public int applyDiscountRate(int settlementFee, double discountRate);
	public double getDiscountRate(String vehicleType, boolean hasDisability);
	public String getSelectedVehicleType(List<FeeCalculationData> dataList);
	public String getExitTollgateName(List<FeeCalculationData> dataList);
	public List<Tollgate> getRouteMap(List<FeeCalculationData> dataList, String accountName);
    public String getSelectedVehicleNumber(List<FeeCalculationData> dataList);
    public String getSelectedTollgateEntryTime(List<FeeCalculationData> dataList);
    
    public OutputResult getOutputResult(String accountName) throws RuntimeException;
    public BillResult getBillResult(String accountName);
}	
