package engine.service.rule;

import engine.domain.ProductPricing;

public interface PricingEngineRule {

	public void applyRule( ProductPricing product ) throws Exception;
	
}
