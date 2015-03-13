package moneyman;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class URLDataFetcher implements CurrencyFetcherInterface{

	private URL currencyURL;
	private ArrayList<CurrencyValue> currencyValues = new ArrayList<>();

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
		if (unprocessedName.contains("SILVER") || unprocessedName.contains("GOLD") || unprocessedName.contains("PLATINUM")){
			return;
		}
		String processedName = unprocessedName.substring(23, 26);
		String processedPrice = unprocessedPrice.substring(unprocessedPrice.indexOf("\">")+2, unprocessedPrice.indexOf("</field"));
		currencyValues.add(new CurrencyValue(processedName, new BigDecimal(processedPrice)));
	}
	

	@Override
	public ArrayList<CurrencyValue> getCurrencyList() {
		return currencyValues;
	}
	
	public static void main(String[] args) {
		CurrencyFetcherInterface fetcher = new URLDataFetcher();
		ArrayList<CurrencyValue> values = fetcher.getCurrencyList();
		values.forEach(s -> System.out.println(s));
		
	}

}
