package moneyman;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public interface CurrencyFetcherInterface {
	public HashMap<String, BigDecimal> getCurrencyMap();
	public List<String> getAvailableCurrencies();
	public int getAvailableCurrencyCount();
	public LocalDate getlongestLastUpdate();
}
