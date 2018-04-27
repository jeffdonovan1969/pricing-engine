package engine.util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.CollectionUtils;

public class CalculationUtil {

	/*
	 * TODO - should verify that start < end and throw exception
	 */
	public static boolean isBetween(BigDecimal price, BigDecimal start, BigDecimal end) throws Exception {
		
		  if ( null == price || null == start || null == end || end.compareTo(start) < 1 ){
			  throw new Exception("Invalid arguements.");
		  }
		  
		  // Lower Boundry
		  if (  price.compareTo(start) == 0 ){
			  return true;
		  }
		  // Upper Boundry
		  else if ( price.compareTo(end) == 0 ){
			  return true;
		  }
		 
		  return price.compareTo(start) > 0 && price.compareTo(end) < 0;
	}
	
	public static BigDecimal multiply( BigDecimal base , BigDecimal factor ) throws Exception {
		
		if ( null == base || null == factor ){
			throw new Exception("Invalid arguements.");
		}
		
		return base.multiply(factor, new MathContext(2));
	}
	
	/*
	 * TODO - should verify that list is not empty otherwise throw exception
	 */
	public static BigDecimal calculateMean( List<BigDecimal> bigDecimals, int roundingMode ) throws Exception {
		
		if ( CollectionUtils.isEmpty(bigDecimals) ){
			throw new Exception("List must comtain at least one element.");
		}
		
		BigDecimal[] totalWithCount
        = bigDecimals.stream()
        .filter(bd -> bd != null)
        .map(bd -> new BigDecimal[]{bd, BigDecimal.ONE})
        .reduce((a, b) -> new BigDecimal[]{a[0].add(b[0]), a[1].add(BigDecimal.ONE)})
        .get();
		BigDecimal mean = totalWithCount[0].divide(totalWithCount[1], roundingMode);
		return mean;
	}
	
	/*
	 *  Select most common element- if multiple elements occur the same number of times take the first in the sorted list
	 */
	public static <T> T mostCommonElement(List<T> list) throws Exception {
		
		if ( CollectionUtils.isEmpty(list) ){
			throw new Exception("List must contain at least one element.");
		}
		
	    Map<T, Integer> map = new LinkedHashMap<>();

	    for (T t : list) {
	        Integer val = map.get(t);
	        map.put(t, val == null ? 1 : val + 1);
	    }

	    
	    Entry<T, Integer> max = null;

	    for (Entry<T, Integer> e : map.entrySet()) {
	        if (max == null || e.getValue() > max.getValue())
	            max = e;
	    }

	    return max.getKey();
	}
	
}
