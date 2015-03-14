package moneyman;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;

public class CurrencyConverter {
	
	
	public static BigDecimal convert(HashMap<String , BigDecimal> currencies, String from, String to, BigDecimal amount) {
		BigDecimal usdValue = currencies.get(to).multiply(amount);
		return usdValue.divide(currencies.get(from), RoundingMode.HALF_UP);
	}
	
	
}
