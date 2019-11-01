package com.freesky.rest.client;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class OKCoinClient {
	public static void main(String[] args) {
		try {

			Client client = Client.create();

			WebResource webResource = client
					.resource("https://www.okcoin.cn/api/v1/ticker.do?symbol=btc_cny");

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
			String date = obj.getString("date");
			Long longTime = Long.parseLong(date);
			Date dd = new Date(longTime);
			System.out.println(dd);

			JSONObject ticker = obj.getJSONObject("ticker");
			String lastprice =  ticker.getString("last");
			BigDecimal bd = new BigDecimal(lastprice);
			System.out.println(bd);

		} catch (Exception e) {

			e.printStackTrace();

		}

	}
}
