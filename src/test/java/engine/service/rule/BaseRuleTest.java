package engine.service.rule;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import engine.domain.Demand;
import engine.domain.Product;
import engine.domain.ProductPricing;
import engine.domain.Supply;
import engine.domain.SurveyData;

public class BaseRuleTest {
	
	protected Product buildProduct( String productCode, String competitor, Supply supply, Demand demand, double[] surveyPrices ){
		Product product = new Product();
		product.setProductCode(productCode);
		product.setSupply(supply);
		product.setDemand(demand);
		List<SurveyData> surveyData = new ArrayList<SurveyData>();
		for ( double price : surveyPrices ){
			surveyData.add( new SurveyData(competitor, new BigDecimal(price).setScale(2, RoundingMode.CEILING)));
		}
		product.setSurveyData(surveyData);
		return product;
	}
	
	protected ProductPricing buildProductPricing( Product product, Date date, BigDecimal recommendedPrice ){
		ProductPricing productPricing = new ProductPricing();
		productPricing.setProduct(product);
		productPricing.setRunDate(date);
		productPricing.setRecommendedPrice(recommendedPrice);
		if ( null != product )
			productPricing.setValidSurveyData(product.getSurveyData());
		return productPricing;
	}
	
	protected void assertProductPricing( ProductPricing productPricing, int numberOfMessages, int numberOfValidSurveyData, BigDecimal recommendedPrice ){
		if ( CollectionUtils.isNotEmpty(productPricing.getMessages() ) )
			assertEquals(productPricing.getMessages().size(),numberOfMessages);
		if ( CollectionUtils.isNotEmpty(productPricing.getValidSurveyData() ) )
			assertEquals(productPricing.getValidSurveyData().size(),numberOfValidSurveyData);
		assertEquals(productPricing.getRecommendedPrice(), recommendedPrice);		
	}

}
