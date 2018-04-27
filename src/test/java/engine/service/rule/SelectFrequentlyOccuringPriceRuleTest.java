package engine.service.rule;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import org.junit.Test;

import engine.domain.Demand;
import engine.domain.Product;
import engine.domain.ProductPricing;
import engine.domain.Supply;

public class SelectFrequentlyOccuringPriceRuleTest extends BaseRuleTest {

	SelectFrequentlyOccuringPriceRule rule = new SelectFrequentlyOccuringPriceRule(null);
	
	@Test
	public void testPricingWithDuplicates()  throws Exception {
		Product product = buildProduct("product1", "competitor", Supply.HIGH,Demand.HIGH,new double[]{1.00,2.00,2.00,3.00});
		ProductPricing productPricing = buildProductPricing(product, new Date(), null);
		rule.applyRule(productPricing);
		assertProductPricing(productPricing,0,4,new BigDecimal(2.00).setScale(2, RoundingMode.CEILING));
	}
	
	@Test
	public void testPricingWithMultipleDuplicatesDifferentPrices()  throws Exception {
		Product product = buildProduct("product1", "competitor", Supply.HIGH,Demand.HIGH,new double[]{10.00,12.00,12.00,13.00,13.00,13.00});
		ProductPricing productPricing = buildProductPricing(product, new Date(), null);
		rule.applyRule(productPricing);
		assertProductPricing(productPricing,0,6,new BigDecimal(13.00).setScale(2, RoundingMode.CEILING));	
	}
	
	@Test
	public void testPricingWithMultipleDuplicatesSamePrices()  throws Exception {
		Product product = buildProduct("product1", "competitor", Supply.HIGH,Demand.HIGH,new double[]{10.00,12.00,12.00,13.00,13.00});
		ProductPricing productPricing = buildProductPricing(product, new Date(), null);
		rule.applyRule(productPricing);
		assertProductPricing(productPricing,0,5,new BigDecimal(12.00).setScale(2, RoundingMode.CEILING));	
	}
	
	@Test
	public void testPricingWithMultipleDuplicatesSamePricesUnOrdered()  throws Exception {
		Product product = buildProduct("product1", "competitor", Supply.HIGH,Demand.HIGH,new double[]{10.00,9.00,13.00,12.00,13.00,12.00,15.00,15.00,9.00});
		ProductPricing productPricing = buildProductPricing(product, new Date(), null);
		rule.applyRule(productPricing);
		assertProductPricing(productPricing,0,9,new BigDecimal(9.00).setScale(2, RoundingMode.CEILING));	
	}
	
	@Test
	public void testPricingNoDuplicatePrices()  throws Exception {
		Product product = buildProduct("product1", "competitor", Supply.HIGH,Demand.HIGH,new double[]{1.00,2.00,3.00});
		ProductPricing productPricing = buildProductPricing(product, new Date(), null);
		rule.applyRule(productPricing);
		assertProductPricing(productPricing,0,3,new BigDecimal(1.00).setScale(2, RoundingMode.CEILING));
	}
}
