package engine.service.rule;

import engine.domain.ProductPricing;

public abstract class PricingEngineRuleBase implements PricingEngineRule {
	
	private PricingEngineRule next;
	
	PricingEngineRuleBase( PricingEngineRule next ){
		this.next = next;
	}
	
	protected void applyNext( ProductPricing productPricing ) throws Exception {
		if ( next != null ){
			next.applyRule(productPricing);
		}
	}
	
}
