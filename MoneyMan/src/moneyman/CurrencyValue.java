package moneyman;

import java.math.BigDecimal;

public class CurrencyValue {
	
	
	private String name;
	private BigDecimal value;
	
	public CurrencyValue(String name, BigDecimal value) {
		this.name = name;
		this.value = value;
	}
	
	public BigDecimal getValue() {
		return value;
	}
	public String getName() {
		return name;
	}
	
	@Override
	public String toString(){
		return name+" - "+value.toString();
	}
	
}
