package com.dughettidanilo.ide.cryptostats.model;

public class CryptoPrice {
	private String name;
	private String USD; 
	private String EUR;
	
	public CryptoPrice() {
		this.name = "";
		this.USD = "";
		this.EUR = "";
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUSD() {
		return USD;
	}
	public void setUSD(String uSD) {
		USD = uSD;
	}
	public String getEUR() {
		return EUR;
	}
	public void setEUR(String eUR) {
		EUR = eUR;
	}

	
	@Override
	public String toString() {
		StringBuilder priceBuilder = new StringBuilder();
		priceBuilder.append("Crypto: ");
		priceBuilder.append(this.name);
		priceBuilder.append("\n");
		priceBuilder.append("EUR: ");
		priceBuilder.append(this.EUR);
		priceBuilder.append("\n");
		priceBuilder.append("USD: ");
		priceBuilder.append(this.USD);
		priceBuilder.append("\n");
		return priceBuilder.toString();
	}
	
}
