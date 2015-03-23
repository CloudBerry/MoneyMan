package moneyman;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class URLDataFetcher implements CurrencyFetcherInterface{

	private URL currencyURL;
	private HashMap<String, BigDecimal> currencies = new HashMap<>();

	public URLDataFetcher() {
		try {
			currencyURL  = new URL("http://finance.yahoo.com/webservice/v1/symbols/allcurrencies/quote");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(currencyURL.openStream()));
			String line;
			while ((line = br.readLine()) != null){

				if (line.equals("</resource><resource classname=\"Quote\">")){
					processQuote(br);
				}

			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	/* Called when a new currency segment is by the reader in the
	 * constructor. CurrencyValues are added to the arraylist ab
	 */
	private void processQuote(BufferedReader br) throws IOException {
		String unprocessedName = br.readLine();
		String unprocessedPrice = br.readLine();
		if (unprocessedName.contains("SILVER") || unprocessedName.contains("GOLD") || unprocessedName.contains("PLATINUM") || unprocessedName.contains("COPPER")){
			return;
		}
		String processedName = unprocessedName.substring(23, 26);
		String processedPrice = unprocessedPrice.substring(unprocessedPrice.indexOf("\">")+2, unprocessedPrice.indexOf("</field"));
		currencies.put(processedName, new BigDecimal(processedPrice));
	}
	

	@Override
	public HashMap<String, BigDecimal> getCurrencyMap() {
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
		return currencies.size();
	}
	
	public static void main(String[] args) {
		CurrencyFetcherInterface fetcher = new URLDataFetcher();
		HashMap<String, BigDecimal> currencies = fetcher.getCurrencyMap();
		currencies.forEach((s, v) -> System.out.println(s+" - "+v.toString()));
		
	}

	

	

}
