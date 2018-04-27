package engine.service.rule;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import engine.domain.Demand;
import engine.domain.ProductPricing;
import engine.domain.Supply;
import engine.util.CalculationUtil;
/*
 *  SupplyAndDemandAdjustmentRule - adjust price based on given supply and demand parameters
 *						   Products are classified based on parameters like Supply, Demand. Possible values are Low (L), High (H)
 *						   If Supply is High and Demand is High, Product is sold at same price as chosen price.
 *						   If Supply is Low and Demand is Low, Product is sold at 10 % more than chosen price.
 *						   If Supply is Low and Demand is High, Product is sold at 5 % more than chosen price.
 *						   If Supply is High and Demand is Low, Product is sold at 5 % less than chosen price.
 */
public class SupplyDemandAdjustmentRule extends PricingEngineRuleBase {
	
	private static final Logger	 logger = LoggerFactory.getLogger(SupplyDemandAdjustmentRule.class);
	
	private static BigDecimal   FIVE_PERCENT = new BigDecimal(.05);
	private static BigDecimal   TEN_PERCENT = new BigDecimal(.10);
	
	public SupplyDemandAdjustmentRule( PricingEngineRule next ){
		super(next);
	}
	
	@Override
	public void applyRule( ProductPricing productPricing ) throws Exception {
	
		logger.debug("applyRule");
		
		if ( null != productPricing.getProduct() && null != productPricing.getProduct().getSupply() && null != productPricing.getProduct().getDemand() ){
			if ( productPricing.getProduct().getDemand() == Demand.HIGH && productPricing.getProduct().getSupply() == Supply.LOW ){
				// Sell at 5% More
				productPricing.setRecommendedPrice( productPricing.getRecommendedPrice().add(CalculationUtil.multiply(productPricing.getRecommendedPrice(), FIVE_PERCENT)));
			}
			else if ( productPricing.getProduct().getDemand() == Demand.LOW && productPricing.getProduct().getSupply() == Supply.HIGH ){
				// Sell at 5% Less
				productPricing.setRecommendedPrice( productPricing.getRecommendedPrice().subtract(CalculationUtil.multiply(productPricing.getRecommendedPrice(), FIVE_PERCENT)));
			}
			else if ( productPricing.getProduct().getDemand() == Demand.LOW && productPricing.getProduct().getSupply() == Supply.LOW ){
				// Sell at 10% More
				productPricing.setRecommendedPrice( productPricing.getRecommendedPrice().add(CalculationUtil.multiply(productPricing.getRecommendedPrice(), TEN_PERCENT)));
			}
		}
		applyNext(productPricing);

	}
	
}
