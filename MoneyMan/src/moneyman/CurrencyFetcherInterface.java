package moneyman;

import java.math.BigDecimal;
import java.util.HashMap;

public interface CurrencyFetcherInterface {
	public HashMap<String, BigDecimal> getCurrencyMap();
}
