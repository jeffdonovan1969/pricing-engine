package engine.service;

import java.util.List;

import engine.domain.Product;
import engine.domain.ProductPricing;

public interface PricingEngine {
	
	List<ProductPricing> priceProducts( List<Product> products );

}
