package engine.service.rule;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import engine.domain.ProductPricing;
import engine.domain.SurveyData;

/*
 *  ValidationRule - validation of data with strict "stop processing" if any data problem is found
 */
public class ValidationRule extends PricingEngineRuleBase {

	private static final Logger logger = LoggerFactory.getLogger(ValidationRule.class);

	private String noProductErrorMessage;
	private String noSupplyErrorMessage;
	private String noDemandErrorMessage;
	private String noProductCodeErrorMessage;
	private String surveyDataRequiredErrorMessage;
	private String surveyDataContainsDuplicatesErrorMessage;
	private String invaidPriceErrorMessage;
	private String invalidCompetitorErrorMessage;

	public ValidationRule(PricingEngineRule next) {
		super(next);
	}

	@Override
	public void applyRule(ProductPricing productPricing) throws Exception {

		logger.debug("applyRule");

		if (null == productPricing.getProduct()) {
			productPricing.setMessage(noProductErrorMessage);
		} else {
			if (null == productPricing.getProduct().getSupply()) {
				productPricing.setMessage(noSupplyErrorMessage);
			}
			if (null == productPricing.getProduct().getDemand()) {
				productPricing.setMessage(noDemandErrorMessage);
			}
			if (StringUtils.isEmpty(productPricing.getProduct().getProductCode())) {
				productPricing.setMessage(noProductCodeErrorMessage);
			}
			if (CollectionUtils.isEmpty(productPricing.getProduct().getSurveyData())) {
				productPricing.setMessage(surveyDataRequiredErrorMessage);
			} else {
				// Ensure survey data has no duplicates
				Set<SurveyData> surveyDataSet = new HashSet<SurveyData>(productPricing.getProduct().getSurveyData());
				if (surveyDataSet.size() != productPricing.getProduct().getSurveyData().size()) {
					productPricing.setMessage(surveyDataContainsDuplicatesErrorMessage);
				} else {
					for (SurveyData data : productPricing.getProduct().getSurveyData()) {
						// Ensure price is not less than zero
						if (null == data.getPrice() || data.getPrice().compareTo(BigDecimal.ZERO) < 0) {
							productPricing.setMessage(invaidPriceErrorMessage);
						}
						// Ensure competitor has value
						if (StringUtils.isEmpty(data.getCompetitor())) {
							productPricing.setMessage(invalidCompetitorErrorMessage);
						}
					}
				}
			}
		}

		// No messages than data is valid proceed to next rule
		if (CollectionUtils.isEmpty(productPricing.getMessages())) {
			applyNext(productPricing);
		}

	}

	public String getNoProductErrorMessage() {
		return noProductErrorMessage;
	}

	public void setNoProductErrorMessage(String noProductErrorMessage) {
		this.noProductErrorMessage = noProductErrorMessage;
	}

	public String getNoSupplyErrorMessage() {
		return noSupplyErrorMessage;
	}

	public void setNoSupplyErrorMessage(String noSupplyErrorMessage) {
		this.noSupplyErrorMessage = noSupplyErrorMessage;
	}

	public String getNoDemandErrorMessage() {
		return noDemandErrorMessage;
	}

	public void setNoDemandErrorMessage(String noDemandErrorMessage) {
		this.noDemandErrorMessage = noDemandErrorMessage;
	}

	public String getNoProductCodeErrorMessage() {
		return noProductCodeErrorMessage;
	}

	public void setNoProductCodeErrorMessage(String noProductCodeErrorMessage) {
		this.noProductCodeErrorMessage = noProductCodeErrorMessage;
	}

	public String getSurveyDataRequiredErrorMessage() {
		return surveyDataRequiredErrorMessage;
	}

	public void setSurveyDataRequiredErrorMessage(String surveyDataRequiredErrorMessage) {
		this.surveyDataRequiredErrorMessage = surveyDataRequiredErrorMessage;
	}

	public String getSurveyDataContainsDuplicatesErrorMessage() {
		return surveyDataContainsDuplicatesErrorMessage;
	}

	public void setSurveyDataContainsDuplicatesErrorMessage(String surveyDataContainsDuplicatesErrorMessage) {
		this.surveyDataContainsDuplicatesErrorMessage = surveyDataContainsDuplicatesErrorMessage;
	}

	public String getInvaidPriceErrorMessage() {
		return invaidPriceErrorMessage;
	}

	public void setInvaidPriceErrorMessage(String invaidPriceErrorMessage) {
		this.invaidPriceErrorMessage = invaidPriceErrorMessage;
	}

	public String getInvalidCompetitorErrorMessage() {
		return invalidCompetitorErrorMessage;
	}

	public void setInvalidCompetitorErrorMessage(String invalidCompetitorErrorMessage) {
		this.invalidCompetitorErrorMessage = invalidCompetitorErrorMessage;
	}

}
