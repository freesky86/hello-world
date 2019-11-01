package com.freesky.rest.client;

import java.math.BigDecimal;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class BittrexClient {
	public static void main(String[] args) {
		try {

			Client client = Client.create();

			WebResource webResource = client
					.resource("https://bittrex.com/api/v1.1/public/getmarketsummary?market=btc-xwc");

			ClientResponse response = webResource.accept("application/json")
					.get(ClientResponse.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatus());
			}

			String output = response.getEntity(String.class);

			System.out.println("Output from Server .... \n");
			System.out.println(output);

			JSONObject obj = new JSONObject(output);
			String status = obj.getString("success");
			System.out.println("status: " + status);

			JSONArray arr = obj.getJSONArray("result");
			for (int i = 0; i < arr.length(); i++) {
				JSONObject json = arr.getJSONObject(i);
				String marketName = json
						.getString("MarketName");
				System.out.println(marketName);
				String lastprice = json.getString("Last");
				BigDecimal bd = new BigDecimal(lastprice);
				System.out.println(bd);
				String timeStamp = json.getString("TimeStamp");
				//2017-09-08T12:28:36.65
				System.out.println(timeStamp);
			}

		} catch (Exception e) {

			e.printStackTrace();

		}

	}
}
