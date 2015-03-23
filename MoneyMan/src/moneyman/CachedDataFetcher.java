package moneyman;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CachedDataFetcher implements CurrencyFetcherInterface {
	
	private static String cacheURL = System.getProperty("user.home")+"/.moneymanc";
	private HashMap<String, BigDecimal> currencies = new HashMap<>();
	private LocalDate lastUpdated;
	
	public CachedDataFetcher() {
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(cacheURL));
			String line = br.readLine();
			lastUpdated = LocalDate.parse(line);
			while ((line = br.readLine()) != null){
				String[] linedata = line.split(",");
				currencies.put(linedata[0],	 new BigDecimal(linedata[1]));
			}
			br.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	@Override
	public HashMap<String, BigDecimal> getCurrencyMap() {
		// TODO Auto-generated method stub
		return currencies;
	}

	@Override
	public List<String> getAvailableCurrencies() {
		ArrayList<String> cs = new ArrayList<>();
		cs.addAll(currencies.keySet());
		return cs;
	}

	@Override
	public int getAvailableCurrencyCount() {
		// TODO Auto-generated method stub
		return currencies.size();
	}

	@Override
	public LocalDate getlongestLastUpdate() {
		// TODO Auto-generated method stub
		return lastUpdated;
	}

}
