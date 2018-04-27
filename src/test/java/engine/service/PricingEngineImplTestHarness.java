package engine.service;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import engine.domain.Demand;
import engine.domain.Product;
import engine.domain.ProductPricing;
import engine.domain.Supply;
import engine.service.rule.BaseRuleTest;
import engine.service.rule.FilterSurveyDataRule;
import engine.service.rule.PricingEngineRule;
import engine.service.rule.SelectFrequentlyOccuringPriceRule;
import engine.service.rule.SupplyDemandAdjustmentRule;
import engine.service.rule.ValidationRule;

public class PricingEngineImplTestHarness extends BaseRuleTest {

	private PricingEngineRule buildRuleChain(){
		SupplyDemandAdjustmentRule supplyDemandRule = new SupplyDemandAdjustmentRule(null);
		SelectFrequentlyOccuringPriceRule pricingRule = new SelectFrequentlyOccuringPriceRule(supplyDemandRule);
		FilterSurveyDataRule filterRule = new FilterSurveyDataRule(pricingRule);
		ValidationRule validationRule = new ValidationRule(filterRule);
		return validationRule;
	}

	// Filter Rule Tests
	@Test
	public void testFilterData(){
		List<Product> products = new ArrayList<Product>();

		products.add(buildProduct("product1", "competitor", Supply.LOW,Demand.LOW,new double[]{1.00,10.00,20.00}));
		
		PricingEngineImpl engine = new PricingEngineImpl();
		engine.setRule(buildRuleChain());
		
		List<ProductPricing> productPricing = engine.priceProducts(products);
		
		assertEquals(productPricing.size(),1);
		assertProductPricing(productPricing.get(0),0,1,new BigDecimal(11.00).setScale(2, RoundingMode.CEILING));
	}
	
	@Test
	public void testFilterDataFilterBoundries(){
		List<Product> products = new ArrayList<Product>();

		products.add(buildProduct("product1", "competitor", Supply.HIGH,Demand.HIGH,new double[]{1.00,2.00,3.00}));
		
		PricingEngineImpl engine = new PricingEngineImpl();
		engine.setRule(buildRuleChain());
		
		List<ProductPricing> productPricing = engine.priceProducts(products);
		
		assertEquals(productPricing.size(),1);
		assertProductPricing(productPricing.get(0),0,3,new BigDecimal(1.00).setScale(2, RoundingMode.CEILING));
	}
	
	// Pricing Rule Tests
	@Test
	public void testPricingWithDuplicates(){
		List<Product> products = new ArrayList<Product>();

		products.add(buildProduct("product1", "competitor", Supply.HIGH,Demand.HIGH,new double[]{1.00,2.00,2.00,3.00}));
		
		PricingEngineImpl engine = new PricingEngineImpl();
		engine.setRule(buildRuleChain());
		
		List<ProductPricing> productPricing = engine.priceProducts(products);
		assertEquals(productPricing.size(),1);
		assertProductPricing(productPricing.get(0),0,4,new BigDecimal(2.00).setScale(2, RoundingMode.CEILING));
	}
	
	@Test
	public void testPricingWithMultipleDuplicates(){
		List<Product> products = new ArrayList<Product>();

		products.add(buildProduct("product1", "competitor", Supply.HIGH,Demand.HIGH,new double[]{10.00,12.00,12.00,13.00,13.00,13.00}));
			
		PricingEngineImpl engine = new PricingEngineImpl();
		engine.setRule(buildRuleChain());
			
		List<ProductPricing> productPricing = engine.priceProducts(products);
		assertEquals(productPricing.size(),1);
		assertProductPricing(productPricing.get(0),0,6,new BigDecimal(13.00).setScale(2, RoundingMode.CEILING));	
	}
	
	@Test
	public void testPricingNoDuplicatePrices(){
		List<Product> products = new ArrayList<Product>();

		products.add(buildProduct("product1", "competitor", Supply.HIGH,Demand.HIGH,new double[]{1.00,2.00,3.00}));
		
		PricingEngineImpl engine = new PricingEngineImpl();
		engine.setRule(buildRuleChain());
		
		List<ProductPricing> productPricing = engine.priceProducts(products);
		assertEquals(productPricing.size(),1);
		assertProductPricing(productPricing.get(0),0,3,new BigDecimal(1.00).setScale(2, RoundingMode.CEILING));
	}
	
	// Supply and Demand Rule Tests
	@Test
	public void testLowSupplyLowDemand(){
		List<Product> products = new ArrayList<Product>();

		products.add(buildProduct("product1", "competitor", Supply.LOW,Demand.LOW,new double[]{10.00}));
		
		PricingEngineImpl engine = new PricingEngineImpl();
		engine.setRule(buildRuleChain());
		
		List<ProductPricing> productPricing = engine.priceProducts(products);
		
		assertEquals(productPricing.size(),1);
		assertProductPricing(productPricing.get(0),0,1,new BigDecimal(11.00).setScale(2, RoundingMode.CEILING));
	}
	
	@Test
	public void testLowSupplyHighDemand(){
		List<Product> products = new ArrayList<Product>();

		products.add(buildProduct("product1", "competitor", Supply.LOW,Demand.HIGH,new double[]{10.00}));
		
		PricingEngineImpl engine = new PricingEngineImpl();
		engine.setRule(buildRuleChain());
		
		List<ProductPricing> productPricing = engine.priceProducts(products);
		
		assertEquals(productPricing.size(),1);
		assertProductPricing(productPricing.get(0),0,1,new BigDecimal(10.50).setScale(2, RoundingMode.CEILING));
	}
	
	@Test
	public void testHighSupplyLowDemand(){
		List<Product> products = new ArrayList<Product>();

		products.add(buildProduct("product1", "competitor", Supply.HIGH,Demand.LOW,new double[]{10.00}));
		
		PricingEngineImpl engine = new PricingEngineImpl();
		engine.setRule(buildRuleChain());
		
		List<ProductPricing> productPricing = engine.priceProducts(products);
		
		assertEquals(productPricing.size(),1);
		assertProductPricing(productPricing.get(0),0,1,new BigDecimal(9.50).setScale(2, RoundingMode.CEILING));
	}
	
	@Test
	public void testHighSupplyHighDemand(){
		List<Product> products = new ArrayList<Product>();

		products.add(buildProduct("product1", "competitor", Supply.HIGH,Demand.HIGH,new double[]{10.00}));
		
		PricingEngineImpl engine = new PricingEngineImpl();
		engine.setRule(buildRuleChain());
		
		List<ProductPricing> productPricing = engine.priceProducts(products);
		
		assertEquals(productPricing.size(),1);
		assertProductPricing(productPricing.get(0),0,1,new BigDecimal(10.00).setScale(2, RoundingMode.CEILING));
	}
}
