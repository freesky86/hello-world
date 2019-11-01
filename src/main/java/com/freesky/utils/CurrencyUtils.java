package com.freesky.utils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONObject;

public class CurrencyUtils {
	
	private static final String OPEN_EXCHANGE_URL = "http://openexchangerates.org/api/latest.json?app_id=73bd1a567f5f4144859805b563ea42f8&base=USD";
	
	private static final Logger logger = Logger.getLogger(CurrencyUtils.class);
	
	public static void main(String[] args) {
		double usd2cny = getUSD2CNY();
		if (usd2cny > 1) {
			System.out.println("----------");
			System.out.println(usd2cny);
		}
	}

	public static double getUSD2CNY() {
		double usd2cny = 0D;
		String cny = null;
		
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		try {
			HttpGet httpGetRequest = new HttpGet(OPEN_EXCHANGE_URL);
			HttpResponse httpResponse = httpClient.execute(httpGetRequest);
			HttpEntity entity = httpResponse.getEntity();

			byte[] buffer = new byte[4096];
			if (entity != null) {
				InputStream inputStream = entity.getContent();
				try {
					int bytesRead = 0;
					BufferedInputStream bis = new BufferedInputStream(inputStream);
					while ((bytesRead = bis.read(buffer)) != -1) {
						String chunk = new String(buffer, 0, bytesRead);

						JSONObject obj = new JSONObject(chunk);
						JSONObject rates = obj.getJSONObject("rates");
						cny = rates.getString("CNY");
					} 
				} catch (Exception e) {
						logger.error("Error when parse USD-CNY exchange REST JSON: " + e.getMessage());
					} finally {
						try {
							inputStream.close();
						} catch (Exception e1) {
							logger.error("Error when close inputstream: " + e1.getMessage());
						}
					}
				}
			} catch (Exception e) {
				logger.error("Error when call USD-CNY exchange REST API: " + e.getMessage());
			} finally {
				try {
					if (null != null) {
						httpClient.close();
					}
				} catch (IOException ignore) {
					logger.error("--ignore: " + ignore);
				}
			}
		
		if (null == cny) {
			return usd2cny;
		}
		
		try {
			usd2cny = Double.parseDouble(cny);
		} catch (Exception e) {
			logger.error("The USD-CNY exchange [" + cny + "] is not a double value.");
		}
		
		return usd2cny;
	}
}
