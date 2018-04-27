package engine.service.rule;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import engine.domain.ProductPricing;
import engine.util.CalculationUtil;

/*
 * SelectFrequentlyOccuringPriceRule - select recommended price based on frequency.
 *						   The retail company uses a Pricing engine which recommends most frequently occurring price. 
 *						   If multiple prices occur frequently, the least amongst them is chosen.
 */
public class SelectFrequentlyOccuringPriceRule extends PricingEngineRuleBase {
	
	private static final Logger	 logger = LoggerFactory.getLogger(SelectFrequentlyOccuringPriceRule.class);
	
	public SelectFrequentlyOccuringPriceRule( PricingEngineRule next ){
		super(next);
	}
	
	@Override
	public void applyRule( ProductPricing productPricing ) throws Exception {

		logger.debug("applyRule");
		
		// Collect and sort valid survey data
		List<BigDecimal> validPrices = productPricing.getValidSurveyData().stream().map(sd -> sd.getPrice() ).collect(Collectors.toList());
		Collections.sort(validPrices);
		
		// Find the most frequently occurring price. If multiple prices occur the same number of times, take the smallest price
		BigDecimal freqOccuringPrice = CalculationUtil.mostCommonElement(validPrices);
		productPricing.setRecommendedPrice(freqOccuringPrice.setScale(2, RoundingMode.CEILING));
		
		applyNext(productPricing);
	}
	
}
