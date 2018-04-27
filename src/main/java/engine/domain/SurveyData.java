package engine.domain;

import java.math.BigDecimal;

public class SurveyData {

	private String		competitor;
	private	BigDecimal	price;
	
	public SurveyData(String competitor, BigDecimal price) {
		super();
		this.competitor = competitor;
		this.price = price;
	}
	
	public int compareTo(SurveyData obj) {
		return this.getPrice().compareTo(obj.getPrice());
	}

	public String getCompetitor() {
		return competitor;
	}

	public void setCompetitor(String competitor) {
		this.competitor = competitor;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
}
