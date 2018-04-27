package engine.service.rule;

import java.util.Date;

import org.junit.Test;

import engine.domain.Demand;
import engine.domain.Product;
import engine.domain.ProductPricing;
import engine.domain.Supply;

public class FilterSurveyDataRuleTest extends BaseRuleTest {

	FilterSurveyDataRule	rule = new FilterSurveyDataRule(null);
	
	@Test
	public void testFilterData() throws Exception {
		Product product = buildProduct("product1","competitor", Supply.LOW,Demand.LOW,new double[]{1.00,10.00,20.00});
		ProductPricing productPricing = buildProductPricing(product, new Date(), null);
		rule.applyRule(productPricing);
		assertProductPricing(productPricing,0,1,null);
	}
	
	@Test
	public void testFilterDataFilterBoundries()  throws Exception {
		Product product = buildProduct("product1", "competitor", Supply.HIGH,Demand.HIGH,new double[]{1.00,2.00,3.00});
		ProductPricing productPricing = buildProductPricing(product, new Date(), null);
		rule.applyRule(productPricing);
		assertProductPricing(productPricing,0,3,null);
	}
}
