package engine.util;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

public class CalculationUtilTest {

	@Test
	public void testIsBetween() throws Exception {
		assertEquals(CalculationUtil.isBetween(new BigDecimal(3.00), new BigDecimal(2.00), new BigDecimal(5.00)), true);
	}
	
	@Test
	public void testIsBetweenOnLowerBoundry() throws Exception {
		assertEquals(CalculationUtil.isBetween(new BigDecimal(2.00), new BigDecimal(2.00), new BigDecimal(5.00)), true);
	}
	
	@Test
	public void testIsBetweenOnUpperBoundry() throws Exception {
		assertEquals(CalculationUtil.isBetween(new BigDecimal(5.00), new BigDecimal(2.00), new BigDecimal(5.00)), true);
	}
	
	@Test
	public void testIsBetweenOverUpperBoundry() throws Exception {
		assertEquals(CalculationUtil.isBetween(new BigDecimal(6.00), new BigDecimal(2.00), new BigDecimal(5.00)), false);
	}
	
	@Test
	public void testIsBetweenBelowLowerBoundry() throws Exception {
		assertEquals(CalculationUtil.isBetween(new BigDecimal(1.00), new BigDecimal(2.00), new BigDecimal(5.00)), false);
	}
	
	@Test (expected=Exception.class)
	public void testIsBetweenBelowNullFirstArg() throws Exception {
		CalculationUtil.isBetween(null, new BigDecimal(2.00), new BigDecimal(5.00));
	}
	
	@Test (expected=Exception.class)
	public void testIsBetweenBelowNullSecondArg() throws Exception {
		CalculationUtil.isBetween(new BigDecimal(2.00), null, new BigDecimal(5.00));
	}
	
	@Test (expected=Exception.class)
	public void testIsBetweenBelowNullThirdArg() throws Exception {
		CalculationUtil.isBetween(new BigDecimal(5.00), new BigDecimal(2.00), null);
	}
	
	@Test (expected=Exception.class)
	public void testIsBetweenLowerBoundryGreaterThanUpper() throws Exception {
		assertEquals(CalculationUtil.isBetween(new BigDecimal(1.00), new BigDecimal(5.00), new BigDecimal(2.00)), false);
	}
	
	@Test
	public void testCalculateMeanOneNumber() throws Exception {
		List<BigDecimal> sampleList = new ArrayList<BigDecimal>();
		sampleList.add( new BigDecimal(1.00).setScale(2, RoundingMode.CEILING) );
		BigDecimal meanValue = CalculationUtil.calculateMean(sampleList, 2);
		assertEquals(meanValue,new BigDecimal(1.00).setScale(2, RoundingMode.CEILING));
	}
	
	@Test
	public void testCalculateMeanZero() throws Exception {
		List<BigDecimal> sampleList = new ArrayList<BigDecimal>();
		sampleList.add( new BigDecimal(0.00).setScale(2, RoundingMode.CEILING) );
		BigDecimal meanValue = CalculationUtil.calculateMean(sampleList, 2);
		assertEquals(meanValue,new BigDecimal(0.00).setScale(2, RoundingMode.CEILING));
	}
	
	@Test
	public void testCalculateMeanMultipleNumbers() throws Exception {
		List<BigDecimal> sampleList = new ArrayList<BigDecimal>();
		sampleList.add( new BigDecimal(1.00).setScale(2, RoundingMode.CEILING) );
		sampleList.add( new BigDecimal(2.00).setScale(2, RoundingMode.CEILING) );
		BigDecimal meanValue = CalculationUtil.calculateMean(sampleList, 2);
		assertEquals(meanValue,new BigDecimal(1.50).setScale(2, RoundingMode.CEILING));
	}
	
	@Test
	public void testCalculateMeanMultipleNumbersNegative() throws Exception {
		List<BigDecimal> sampleList = new ArrayList<BigDecimal>();
		sampleList.add( new BigDecimal(-1.00).setScale(2, RoundingMode.CEILING) );
		sampleList.add( new BigDecimal(-2.00).setScale(2, RoundingMode.CEILING) );
		BigDecimal meanValue = CalculationUtil.calculateMean(sampleList, 2);
		assertEquals(meanValue,new BigDecimal(-1.50).setScale(2, RoundingMode.CEILING));
	}
	
	@Test
	public void testCalculateMeanMultipleNumbersMixed() throws Exception {
		List<BigDecimal> sampleList = new ArrayList<BigDecimal>();
		sampleList.add( new BigDecimal(-1.00).setScale(2, RoundingMode.CEILING) );
		sampleList.add( new BigDecimal(1.00).setScale(2, RoundingMode.CEILING) );
		BigDecimal meanValue = CalculationUtil.calculateMean(sampleList, 2);
		assertEquals(meanValue,new BigDecimal(0.00).setScale(2, RoundingMode.CEILING));
	}
	
	@Test (expected=Exception.class)
	public void testCalculateMeanNullArgs() throws Exception {
		CalculationUtil.calculateMean(null, 2);
	}
	
	@Test (expected=Exception.class)
	public void testCalculateMeanEmptyArgs() throws Exception {
		CalculationUtil.calculateMean(new ArrayList<BigDecimal>(), 2);
	}
	
	@Test (expected=Exception.class)
	public void testMostCommonElementEmptyArgs() throws Exception {
		List<BigDecimal> commonList = new ArrayList<BigDecimal>();
		CalculationUtil.mostCommonElement(commonList);
	}
	
	@Test (expected=Exception.class)
	public void testMostCommonElementNullArgs() throws Exception {
		CalculationUtil.mostCommonElement(null);
	}
	
	@Test 
	public void testMostCommonElementOneElement() throws Exception {
		List<Integer> sortList = new ArrayList<Integer>();
		sortList.add(0);
		Collections.sort(sortList);
		Integer mostCommon = (Integer) CalculationUtil.mostCommonElement(sortList);
		assertEquals(mostCommon, new Integer(0) );
	}
	
	@Test 
	public void testMostCommonElement() throws Exception {
		List<Integer> sortList = new ArrayList<Integer>();
		sortList.add(-1);
		sortList.add(0);
		sortList.add(1);
		Collections.sort(sortList);
		Integer mostCommon = (Integer) CalculationUtil.mostCommonElement(sortList);
		assertEquals(mostCommon, new Integer(-1) );
	}
	
	@Test 
	public void testMostCommonElementDuplicate() throws Exception {
		List<Integer> sortList = new ArrayList<Integer>();
		sortList.add(-1);
		sortList.add(0);
		sortList.add(1);
		sortList.add(1);
		Collections.sort(sortList);
		Integer mostCommon = (Integer) CalculationUtil.mostCommonElement(sortList);
		assertEquals(mostCommon, new Integer(1) );
	}
	
	@Test 
	public void testMostCommonElementDuplicateMultiple() throws Exception {
		List<Integer> sortList = new ArrayList<Integer>();
		sortList.add(-1);
		sortList.add(0);
		sortList.add(0);
		sortList.add(1);
		sortList.add(1);
		Collections.sort(sortList);
		Integer mostCommon = (Integer) CalculationUtil.mostCommonElement(sortList);
		assertEquals(mostCommon, new Integer(0) );
	}
}
