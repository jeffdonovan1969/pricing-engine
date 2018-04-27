package engine.domain;

import java.util.List;

public class Product {
	
	private String      productCode;
	private	Supply		supply;
	private Demand		demand;
	List<SurveyData>		surveyData;
	
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public Supply getSupply() {
		return supply;
	}
	public void setSupply(Supply supply) {
		this.supply = supply;
	}
	public Demand getDemand() {
		return demand;
	}
	public void setDemand(Demand demand) {
		this.demand = demand;
	}
	public List<SurveyData> getSurveyData() {
		return surveyData;
	}
	public void setSurveyData(List<SurveyData> surveyData) {
		this.surveyData = surveyData;
	}
	
}
