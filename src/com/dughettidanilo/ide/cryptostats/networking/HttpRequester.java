package com.dughettidanilo.ide.cryptostats.networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.dughettidanilo.ide.cryptostats.model.CryptoPrice;
import com.google.gson.Gson;

public class HttpRequester {
	
	public static final CryptoPrice getCryptoPrice(String coinName) throws IOException {
		BufferedReader bufferedReader = null;
		try {
		URL url = new URL("https://min-api.cryptocompare.com/data/price?fsym=" + coinName + "&tsyms=USD,EUR");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		int status = con.getResponseCode();
		bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer content = new StringBuffer();
		while ((inputLine = bufferedReader.readLine()) != null) {
			content.append(inputLine);
		}
				
		Gson gson = new Gson();
		CryptoPrice cryptoPrice = gson.fromJson(content.toString(), CryptoPrice.class);
		cryptoPrice.setName(coinName);
		return cryptoPrice;
		
		
		
		}catch(Exception exception) {
			return new CryptoPrice();
		}finally {
			bufferedReader.close();
		}
	}
}
