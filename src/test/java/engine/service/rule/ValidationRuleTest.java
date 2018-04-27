package engine.service.rule;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;

import engine.domain.Demand;
import engine.domain.Product;
import engine.domain.ProductPricing;
import engine.domain.Supply;
import engine.domain.SurveyData;

public class ValidationRuleTest extends BaseRuleTest {

	ValidationRule	rule = new ValidationRule(null);
	
	@Test
	public void testValidationEmptyProduct()  throws Exception {
		ProductPricing productPricing = buildProductPricing(null, new Date(), null );
		rule.applyRule(productPricing);
		assertProductPricing(productPricing,1,0,null);
	}
	
	@Test
	public void testValidationEmptySupply()  throws Exception {
		Product product = buildProduct("product1", "competitor1", null,Demand.HIGH,new double[]{10.00});
		ProductPricing productPricing = buildProductPricing(product, new Date(), null);
		rule.applyRule(productPricing);
		assertProductPricing(productPricing,1,1,null);
	}
	
	@Test
	public void testValidationEmptyDemand()  throws Exception {
		Product product = buildProduct("product1", "competitor1", Supply.HIGH,null,new double[]{10.00});
		ProductPricing productPricing = buildProductPricing(product, new Date(), null);
		rule.applyRule(productPricing);
		assertProductPricing(productPricing,1,1,null);
	}
	
	@Test
	public void testValidationEmptyProductCode()  throws Exception {
		Product product = buildProduct(null,"competitor1",Supply.HIGH,Demand.HIGH,new double[]{10.00});
		ProductPricing productPricing = buildProductPricing(product, new Date(), null);
		rule.applyRule(productPricing);
		assertProductPricing(productPricing,1,1,null);
	}
	
	@Test
	public void testValidationEmptySurveyData()  throws Exception {
		Product product = buildProduct("product1","competitor1", Supply.HIGH,Demand.HIGH,new double[]{10.00});
		product.setSurveyData(new ArrayList<SurveyData>());
		ProductPricing productPricing = buildProductPricing(product, new Date(), null);
		rule.applyRule(productPricing);
		assertProductPricing(productPricing,1,1,null);
	}
	
	@Test
	public void testValidationDuplicateSurvey()  throws Exception {
		Product product = buildProduct("product1", "competitor1", Supply.HIGH,Demand.HIGH,new double[]{10.00,10.00});
		ProductPricing productPricing = buildProductPricing(product, new Date(), null);
		rule.applyRule(productPricing);
		assertProductPricing(productPricing,1,2,null);
	}
	
	@Test
	public void testValidationSurveyEmptyPrice()  throws Exception {
		Product product = buildProduct("product1", "competitor1", Supply.HIGH,Demand.HIGH,new double[]{10.00});
		product.getSurveyData().get(0).setPrice(null);
		ProductPricing productPricing = buildProductPricing(product, new Date(), null);
		rule.applyRule(productPricing);
		assertProductPricing(productPricing,1,1,null);
	}
	
	@Test
	public void testValidationSurveyNegativePrice()  throws Exception {
		Product product = buildProduct("product1", "competitor1", Supply.HIGH,Demand.HIGH,new double[]{-10.00});
		ProductPricing productPricing = buildProductPricing(product, new Date(), null);
		rule.applyRule(productPricing);
		assertProductPricing(productPricing,1,1,null);
	}
}
