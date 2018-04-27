package engine.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductPricing {

	// Input Data
	private Product product;
	
	// Generated Data
	private Date runDate;
	private BigDecimal recommendedPrice;
	private List<SurveyData> validSurveyData;
	private List<String> messages = new ArrayList<String>();

	public ProductPricing(Product product) {
		this.product = product;
	}

	public ProductPricing() {
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Date getRunDate() {
		return runDate;
	}

	public void setRunDate(Date runDate) {
		this.runDate = runDate;
	}

	public BigDecimal getRecommendedPrice() {
		return recommendedPrice;
	}

	public void setRecommendedPrice(BigDecimal recommendedPrice) {
		this.recommendedPrice = recommendedPrice;
	}

	public List<SurveyData> getValidSurveyData() {
		return validSurveyData;
	}

	public void setValidSurveyData(List<SurveyData> validSurveyData) {
		this.validSurveyData = validSurveyData;
	}

	public List<String> getMessages() {
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages.addAll(messages);
	}

	public void setMessage(String message) {
		this.messages.add(message);
	}
}
