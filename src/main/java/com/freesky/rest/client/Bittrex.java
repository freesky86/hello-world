package com.freesky.rest.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Bittrex {

	public static void main(String[] args) throws IOException {
		URL url = new URL("https://bittrex.com/api/v1.1/public/getmarketsummary?market=btc-xwc");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Accept", "application/xml");
		if (connection.getResponseCode() != 200) {
			throw new RuntimeException("Operation failed: "
					+ connection.getResponseCode());
		}
		System.out.println("Content-Type: " + connection.getContentType());
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				connection.getInputStream()));
		String line = reader.readLine();
		while (line != null) {
			System.out.println(line);
			line = reader.readLine();
		}
		connection.disconnect();
	}

}
