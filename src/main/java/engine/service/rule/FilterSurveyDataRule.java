package engine.service.rule;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import engine.domain.ProductPricing;
import engine.domain.SurveyData;
import engine.util.CalculationUtil;

/*
 *    FilterSurveyDataRule - filter survey data points based on requirement:
 *	                       Prices less than 50% of average price are treated as promotion and not considered.
 *						   Prices more than 50% of average price are treated as data errors and not considered.
 */
public class FilterSurveyDataRule extends PricingEngineRuleBase {
	
	private static final Logger	 logger = LoggerFactory.getLogger(FilterSurveyDataRule.class);

	public FilterSurveyDataRule( PricingEngineRule next ){
		super(next);
	}
	 
	/**
	 * Wrapper function needed to call method with exception inside stream api
	 */
	private boolean isBetween( BigDecimal price, BigDecimal lower, BigDecimal upper ){
		try {
			return CalculationUtil.isBetween(price.setScale(2, RoundingMode.CEILING), lower, upper);
		}
		catch ( Exception ex ){
			logger.error(ex.getMessage());
		}
		return false;
	}
	
	@Override
	public void applyRule( ProductPricing productPricing ) throws Exception {
		
		logger.debug("applyRule");
		
		List<BigDecimal> surveyPrices = productPricing.getProduct().getSurveyData().stream().map(sc -> sc.getPrice() ).collect(Collectors.toList());
		
		// Calculate Mean/Average From Sample Points
		BigDecimal surveyDataMean = CalculationUtil.calculateMean(surveyPrices, BigDecimal.ROUND_UP);
		
		// Calculate Valid Sample Point Range
		BigDecimal meanLessFiftyPercent = CalculationUtil.multiply(surveyDataMean, new BigDecimal(.5));
		BigDecimal meanPlusFiftyPercent =  CalculationUtil.multiply(surveyDataMean, new BigDecimal(1.5));
		
		// Filter Sample Points
		List<SurveyData> validSurveyData;
			validSurveyData = productPricing.getProduct().getSurveyData().stream()
					.filter(bd -> isBetween( bd.getPrice().setScale(2, RoundingMode.CEILING), meanLessFiftyPercent.setScale(2, RoundingMode.CEILING), meanPlusFiftyPercent.setScale(2, RoundingMode.CEILING)) )
					.collect(Collectors.toList());
		
		// Continue only if all sample prices have not been filtered
		if ( CollectionUtils.isNotEmpty(validSurveyData)){
			productPricing.setValidSurveyData(validSurveyData);
			applyNext(productPricing);
		}
		else {
			productPricing.setMessage("All survey data has been excluded.");
		}
		
	}
	
}
