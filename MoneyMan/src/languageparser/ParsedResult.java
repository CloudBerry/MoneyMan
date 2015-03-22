package languageparser;

import java.math.BigDecimal;

public class ParsedResult {
	
	private String fromCurrency, toCurrency;
	private BigDecimal amount;
	public ParsedResult(String fromCurrency, String toCurrency, BigDecimal amount) {
		this.fromCurrency = fromCurrency;
		this.toCurrency = toCurrency;
		this.amount = amount;
	}
	public String getFromCurrency() {
		return fromCurrency;
	}
	public String getToCurrency() {
		return toCurrency;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setFromCurrency(String fromCurrency) {
		this.fromCurrency = fromCurrency;
	}
	public void setToCurrency(String toCurrency) {
		this.toCurrency = toCurrency;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	@Override 
	public String toString(){
		return "From: "+fromCurrency+" to "+toCurrency+", amount: "+amount.toString();
	}
	
	
}
