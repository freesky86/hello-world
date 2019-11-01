package com.freesky.rest.json;

import java.io.File;
import java.io.FileReader;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

public class JSONParser {

	public static void main(String[] args) throws Exception {
		
		File file = new File("src/example.json");
		FileReader fr = new FileReader(file);
//		BufferedReader br = new BufferedReader(fr);
		char[] buffer = new char[1000];
		int size = fr.read(buffer);
		System.out.println(size);
		String result = String.valueOf(buffer, 0, size);
		
		System.out.println("-----------------------------");
		System.out.println(result);
		System.out.println("-----------------------------");
		
		JSONObject obj = new JSONObject(result);
		System.out.println(obj.toString());
		System.out.println("-----------------------------");
		String pageName = obj.getJSONObject("pageInfo").getString("pageName");
		System.out.println(pageName);

		JSONArray arr = obj.getJSONArray("posts");
		for (int i = 0; i < arr.length(); i++)
		{
		    String post_id = arr.getJSONObject(i).getString("post_id");
		    System.out.println(post_id);
		}
		
		fr.close();
	}

}
