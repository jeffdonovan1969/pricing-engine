package engine.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import engine.domain.Product;
import engine.domain.ProductPricing;
import engine.service.rule.PricingEngineRule;

public class PricingEngineImpl implements PricingEngine {

	private PricingEngineRule	rule;
	
	@Override
	public List<ProductPricing> priceProducts( List<Product> products ){
		
		Date now = new Date();
		
		List<ProductPricing> pricing = new ArrayList<ProductPricing>();
		if (CollectionUtils.isNotEmpty(products) ){
			products.forEach( product -> {
				ProductPricing productPricing = new ProductPricing(product);
				productPricing.setRunDate(now);
				pricing.add(productPricing);
				try {
					rule.applyRule(productPricing);
				}
				catch ( Exception ex ){
					productPricing.setMessage("Exception while processing: " + ex.getMessage());
				}
			});
		}
		return pricing;

	}

	public PricingEngineRule getRule() {
		return rule;
	}

	public void setRule(PricingEngineRule rule) {
		this.rule = rule;
	}
	
}
