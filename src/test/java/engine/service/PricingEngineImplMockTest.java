package engine.service;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import engine.domain.Demand;
import engine.domain.Product;
import engine.domain.ProductPricing;
import engine.domain.Supply;
import engine.service.rule.BaseRuleTest;
import engine.service.rule.PricingEngineRule;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(MockitoJUnitRunner.class)
public class PricingEngineImplMockTest extends BaseRuleTest {
	 
	@Mock
	private PricingEngineRule			rule;

	@InjectMocks
	private PricingEngineImpl		pricingEngine;

	@Test
	public void testRunMultipleEmptyProducts()  throws Exception {
		List<Product> products = new ArrayList<Product>();
		
		List<ProductPricing> productPricing = pricingEngine.priceProducts(products);
		
		assertEquals(productPricing.size(),0);
		verify(rule, times(0)).applyRule( any(ProductPricing.class));
	}
	
	@Test
	public void testRunMultipleProducts() throws Exception {
		List<Product> products = new ArrayList<Product>();
		products.add(buildProduct("product1", "competitor", Supply.LOW,Demand.LOW,new double[]{1.00,10.00,20.00}));
		products.add(buildProduct("product2", "competitor2", Supply.LOW,Demand.LOW,new double[]{1.00,10.00,20.00}));
		
		List<ProductPricing> productPricing = pricingEngine.priceProducts(products);
		
		assertEquals(productPricing.size(),2);
		verify(rule, times(2)).applyRule( any(ProductPricing.class));
	}
}
