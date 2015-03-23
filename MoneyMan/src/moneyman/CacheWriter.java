package moneyman;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;

public class CacheWriter {
	
	private static String cacheURL = System.getProperty("user.home")+"/.moneymanc";
	
	/**
	 * Stores currency data to cache
	 * @param currencies Currencies to store
	 * @param date Date they were updated
	 * @throws IOException 
	 */
	public static void cacheCurrencyData(HashMap<String, BigDecimal> currencies, LocalDate date) throws IOException{
		PrintWriter pw = new PrintWriter(cacheURL);
		pw.println(date.toString());
		currencies.forEach((name, price) -> pw.println(name+","+price.toString()));
		pw.close();
	}
	
	
}
