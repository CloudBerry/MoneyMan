package languageparser;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import moneyman.CurrencyFetcherInterface;
import moneyman.URLDataFetcher;

public class LanguageParser {
	private static String sideSeperator;
	private static String toCurrency, fromCurrency;
	private static BigDecimal amount;
	
	public static ParsedResult parseString(String s, List<String> availableCurrencies, boolean showErrorReport){
		System.out.println(availableCurrencies.size());
		s = s.replaceAll(" +", " ");
		if (s.contains(" in ")){
			sideSeperator = "in";
		} else if (s.contains(" to ")){
			sideSeperator = "to";
		} else {
			if (showErrorReport) System.err.println("Unable to parse string (no separator).");
			return null;
		}
		String fromside = s.split(sideSeperator)[0];
		String toside = s.split(sideSeperator)[1];
		if (availableCurrencies.contains(toside.trim().toUpperCase())) {
			toCurrency = toside.trim().toUpperCase();
		} else {
			if (showErrorReport) System.err.println("Unable to parse string (to side)");
			return null;
		}
		fromside.replace(',', '.');
		String[] fromSideSplit = fromside.split(" ");
		try {
			if (fromSideSplit.length != 2) {
				if (showErrorReport) System.err.println("Unable to parse string (from side");
				return null;
			}
			if (fromSideSplit[0].matches("(?:\\d*\\.)?\\d+")) {
				amount = new BigDecimal(fromSideSplit[0]);
				if (availableCurrencies.contains(fromSideSplit[1].trim().toUpperCase())) {
					fromCurrency = fromSideSplit[1].trim().toUpperCase();
				} else {
					if (showErrorReport) System.err.println("Unable to parse string (from side)");
					return null;
				}
				
			} else if (fromSideSplit[1].matches("(?:\\d*\\.)?\\d+")){
				amount = new BigDecimal(fromSideSplit[1]);
				if (availableCurrencies.contains(fromSideSplit[0].trim().toUpperCase())) {
					fromCurrency = fromSideSplit[0].trim().toUpperCase();
				} else {
					if (showErrorReport) System.err.println("Unable to parse string (from side)");
					return null;
				}
				
			} else {
				if (showErrorReport) System.err.println("Unable to parse string (from side");
				return null;
			}
			
		} catch (Exception e) {
			System.err.println("CRASG");
			if (showErrorReport) System.err.println("Unable to parse string (from side");
			return null;
		}
		
		return new ParsedResult(fromCurrency, toCurrency, amount);
	}
	
	
	
	public static void main(String[] args) {
		CurrencyFetcherInterface fetcher = new URLDataFetcher();
		
		System.out.println(parseString("100 usd to nok", fetcher.getAvailableCurrencies(), true));
	}
	
}
