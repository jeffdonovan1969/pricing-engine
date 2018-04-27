package engine.service.rule;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import org.junit.Test;

import engine.domain.Demand;
import engine.domain.Product;
import engine.domain.ProductPricing;
import engine.domain.Supply;

public class SupplyAndDemandAdjustmentRuleTest extends BaseRuleTest {
	
	SupplyDemandAdjustmentRule	rule = new SupplyDemandAdjustmentRule(null);

	@Test
	public void testLowSupplyLowDemand()  throws Exception {
		Product product = buildProduct("product1", "competitor1", Supply.LOW,Demand.LOW,new double[]{10.00});
		ProductPricing productPricing = buildProductPricing(product, new Date(), new BigDecimal(10.00).setScale(2, RoundingMode.CEILING) );
		rule.applyRule(productPricing);
		assertProductPricing(productPricing,0,1,new BigDecimal(11.00).setScale(2, RoundingMode.CEILING));
	}
	
	@Test
	public void testLowSupplyHighDemand()  throws Exception {
		Product product = buildProduct("product1", "competitor1", Supply.LOW,Demand.HIGH,new double[]{10.00});
		ProductPricing productPricing = buildProductPricing(product, new Date(), new BigDecimal(10.00).setScale(2, RoundingMode.CEILING) );
		rule.applyRule(productPricing);
		assertProductPricing(productPricing,0,1,new BigDecimal(10.50).setScale(2, RoundingMode.CEILING));
	}
	
	@Test
	public void testHighSupplyLowDemand()  throws Exception {
		Product product = buildProduct("product1", "competitor1", Supply.HIGH,Demand.LOW,new double[]{10.00});
		ProductPricing productPricing = buildProductPricing(product, new Date(), new BigDecimal(10.00).setScale(2, RoundingMode.CEILING) );
		rule.applyRule(productPricing);
		assertProductPricing(productPricing,0,1,new BigDecimal(9.50).setScale(2, RoundingMode.CEILING));
	}
	
	@Test
	public void testHighSupplyHighDemand()  throws Exception {
		Product product = buildProduct("product1", "competitor1", Supply.HIGH,Demand.HIGH,new double[]{10.00});
		ProductPricing productPricing = buildProductPricing(product, new Date(), new BigDecimal(10.00).setScale(2, RoundingMode.CEILING) );
		rule.applyRule(productPricing);
		assertProductPricing(productPricing,0,1,new BigDecimal(10.00).setScale(2, RoundingMode.CEILING));
	}
}
