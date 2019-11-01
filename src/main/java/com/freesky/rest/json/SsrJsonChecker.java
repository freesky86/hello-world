package com.freesky.rest.json;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class SsrJsonChecker {
	// 4 April
//	private static final String MAIN_PASSWORD = "0NYIz9u3gPx4";
	// 5 May
//	private static final String MAIN_PASSWORD = "6NYIz9u3gPx5";
	// 6 June
//	private static final String MAIN_PASSWORD = "8NYIz9u3gPx6";
	// 7 July
//	private static final String MAIN_PASSWORD = "6NYIz9u3gPx7";
	// 8 August
	private static final String MAIN_PASSWORD = "6NYIz9u3gPx8";
	// 9 September (refer to the existing password)
//	private static final String MAIN_PASSWORD = "6NYIz9u3gPx9";
	

	public static void main(String[] args) {
		//August
		generateUser(8);
		
//		checkJSON();
		
//		//read mudb.json and check
//		Map<Integer, String> resultMap = readJSON();
//		checkDuplicatePassword(resultMap);

	}
	
	private static void checkDuplicatePassword(Map<Integer, String> resultMap) {
		System.out.println("all user num: " + resultMap.size());
		Collection<String> collection = resultMap.values();
		Set<String> passwordSet = new HashSet<String>();
		passwordSet.addAll(collection);
		System.out.println("all pwd num: " + passwordSet.size());
		
		List<String> pwdList = new ArrayList<String>();
		pwdList.addAll(collection);
		System.out.println("all pwd list: " + pwdList.size());
		
		for (int i = pwdList.size() - 1; i >= 0; i--) {
			String pwd = pwdList.get(i);
			if (passwordSet.contains(pwd)) {
				pwdList.remove(i);
				passwordSet.remove(pwd);
			}
		}
		System.out.println("duplicate pwd list: " + pwdList.size());
		
		Set<Integer> keySet = resultMap.keySet();
		for (Integer key : keySet) {
			String password = resultMap.get(key);
			if (pwdList.contains(password)) {
				System.out.println("------"+ key + " " + password);
			}
		}
		
	}
	
	public static Map<Integer, String> readJSON() {
		String mudb = readToString("./src/mudb.json");
		Map<Integer, String> resultMap = new HashMap<Integer, String>();
		try {
			JSONObject obj = new JSONObject(mudb);
			JSONArray arr = obj.getJSONArray("result");
			for (int i = 0; i < arr.length(); i++) {
				JSONObject json = arr.getJSONObject(i);
				int port = json.getInt("port");
				String password = json.getString("passwd");
				resultMap.put(port, password);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		System.out.println("user number: " + resultMap.size());
		return resultMap;
	}
	
	/**
	 * Check if the password in mudb.json file follow the strategy.
	 */
	public static void checkJSON() {
		String mudb = readToString("./src/mudb.json");
		List<String> userList = new ArrayList<String>();
		try {
			JSONObject obj = new JSONObject(mudb);
			JSONArray arr = obj.getJSONArray("result");
			for (int i = 0; i < arr.length(); i++) {
				JSONObject json = arr.getJSONObject(i);
				int port = json.getInt("port");
				String password = json.getString("passwd");
				if (!checkPassword(port, password)) {
					System.out.println("ERROR: port=" + port + "    password=" + password);
				}
				String user = json.getString("user");
				if (userList.contains(user)) {
					System.out.println("ERROR: user[" + user + "] already exist.");
				} else {
					userList.add(user);
				}
				
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		//user number: 60
		System.out.println("user number: " + userList.size());
	}
	
	/**
	 * Check if the password follow the strategy by port.
	 * 
	 * @param port
	 * @param password
	 * @return
	 */
	public static boolean checkPassword(int port, String password) {
		String header = MAIN_PASSWORD.substring(0, 2);
		String middle = MAIN_PASSWORD.substring(2,9);
		String tail = MAIN_PASSWORD.substring(9);
		//ASCII
		// a 97
		// b 98
		int digest = port % 100 + 1;
		char c = (char) (96 + digest);
		String composed = header + String.valueOf(c) + middle + String.valueOf(digest) + tail;
		
		return composed.equals(password);
	}
	
	/**
	 * Generate user for specific month, then generate JSON string.
	 * 1. generate port password user.
	 * 2. generate JSON string.
	 * 3. delete the last comma(,) for the string in console.
	 * 4. add below string to include above string
	 * {"result": [
	 * 
	 * ]}
	 * 5. open https://www.json.cn/ or http://tool.oschina.net/codeformat/json/ to format JSON
	 * 6. add formatted JSON string to mudb.json. please note to add comma(,) for the existing last one in mudb.json.
	 * 
	 * note: the file path of [mudb.json] is /Root/net/. Upload mudb.json to the server of ifreesky.cn.
	 * 
	 * @param month
	 */
	public static void generateUser(int month) {
		int prefixNumber = 0;
		if (month < 10) {
			prefixNumber = 6000; //  6000   8000
		}
		int begin = month * 100 + prefixNumber;
		String prefix = "ssr" + month;
		
		String header = MAIN_PASSWORD.substring(0, 2);
		String middle = MAIN_PASSWORD.substring(2,9);
		String tail = MAIN_PASSWORD.substring(9);
		//ASCII
		// a 97
		// b 98
		int count = 10;
		for (int port = begin; port < begin + count; port++) {
			int digest = port % 100 + 1;
			char c = (char) (96 + digest);
			String password = header + String.valueOf(c) + middle + String.valueOf(digest) + tail;
			//add random number at beginning 
			//add random char at end
			int number = randomNumber(10);
			char cc = randomChar();
			password = number + password + cc;
			
			String user = prefix + digest;
			
//			System.out.println(port + "    " + password + "      " + user);
			
			generateJSON(port, password, user);
		}
	}
	
	
	private static void generateJSON(int port, String passwd, String user) {
		JSONObject obj = new JSONObject() ; // 定义一个描述json的数据
		try {
			obj.put("port", port) ;
			obj.put("passwd", passwd) ;
			obj.put("user", user) ;
			//addtional
			obj.put("method", "aes-256-cfb") ;
			obj.put("obfs", "tls1.2_ticket_auth_compatible") ;
			obj.put("protocol", "origin") ;
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	
		String str = obj.toString() ; // 将保存的数据变为字符串
		System.out.println(str + ",");
		
	}
	
	public static String readToString(String fileName) {  
        String encoding = "ISO-8859-1";  
        File file = new File(fileName);  
        Long filelength = file.length();  
        byte[] filecontent = new byte[filelength.intValue()];  
        try {  
            FileInputStream in = new FileInputStream(file);  
            in.read(filecontent);  
            in.close();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        try {  
            return new String(filecontent, encoding);  
        } catch (UnsupportedEncodingException e) {  
            System.err.println("The OS does not support " + encoding);  
            e.printStackTrace();  
            return null;  
        }  
    } 

	private static int randomNumber(int max) {
		int number = (int) (Math.random() * max);
		
		return number;
	}
	
	
	private static char randomChar() {
		char[] alphabet = new char[26];
		short start = 65; // 'A'
		for (short i = 0; i < 26; i++) {
			alphabet[i] = (char) (start + i);
		}
				
		int index = randomNumber(26);

		return alphabet[index];
		
	}
}
