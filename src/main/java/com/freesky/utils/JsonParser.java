package com.freesky.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;

import com.cedarsoftware.util.io.JsonWriter;
import com.freesky.dto.AccountBean;
import com.freesky.dto.V2RayAccountBean;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

public class JsonParser {
	
	private static final Logger logger = Logger.getLogger(JsonParser.class);

//	public static final String V2RAY_PATH = "/usr/local/etc/v2ray/";
	
	private static final Map<Integer, String> prefixMap = new HashMap<>();
	
	static {
		prefixMap.put(10, "love");
		prefixMap.put(15, "g15-");
		prefixMap.put(20, "g20-");
		prefixMap.put(25, "g25-");
		prefixMap.put(30, "g30-");
	}

	public static Map<Integer, AccountBean> readJSON(String ssrFile) {
//		String jsonFile = path + "mudb.json";

		String mudb = readToString(ssrFile);
		Map<Integer, AccountBean> resultMap = new HashMap<Integer, AccountBean>();
		try {
			JSONObject obj = JSONObject.fromObject(mudb);
			JSONArray arr = obj.getJSONArray("result");
			for (int i = 0; i < arr.size(); i++) {
				JSONObject json = arr.getJSONObject(i);
				int port = json.getInt("port");

				String password = json.getString("passwd");
				String method = json.getString("method");
				String obfs = json.getString("obfs");
				String protocol = json.getString("protocol");
				AccountBean bean = new AccountBean();
				bean.setPassword(password);
				bean.setMethod(method);
				bean.setObfs(obfs);
				bean.setProtocol(protocol);

				resultMap.put(port, bean);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		System.out.println("user number: " + resultMap.size());
		return resultMap;
	}

	/**
	 * Read V2Ray config.json to get id list
	 * 
	 * @param path
	 * @return
	 */
	public static Map<String, V2RayAccountBean> readV2RayJSON(String v2rayFile, String port) {
//		String jsonFile = path + "config.json";

		String json = readToString(v2rayFile);
		Map<String, V2RayAccountBean> resultMap = new HashMap<String, V2RayAccountBean>();
		try {
			JSONObject root = JSONObject.fromObject(json);
			// old config.json format
			// JSONObject inbound = root.getJSONObject("inbound");
			// new config.json format
			JSONArray inbounds = root.getJSONArray("inbounds");
			// JSONObject settings = inbound.getJSONObject("settings");
			JSONObject settings = inbounds.getJSONObject(0).getJSONObject("settings");
			JSONArray clients = settings.getJSONArray("clients");
			for (int i = 0; i < clients.size(); i++) {
				JSONObject client = clients.getJSONObject(i);
				String id = client.getString("id");
				String[] idArray = id.split("-");
				String portNumber = idArray[3];

				if (port.contains(portNumber) || portNumber.contains(port)) {
					V2RayAccountBean bean = new V2RayAccountBean();
					bean.setPort(portNumber);
					bean.setId(id);

					resultMap.put(port, bean);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		System.out.println("user number: " + resultMap.size());
		return resultMap;
	}

	/**
	 * read file to String
	 * @param fileName
	 * @return file content as String.
	 */
	private static String readToString(String fileName) {
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

	public static void main(String[] args) {
		int port = 9999;
		//add
		String jsonFile = "./mudb.json";
		boolean isAdded = addSsrJson(jsonFile, port);
		
		
		if (isAdded) {
			System.out.println(port + " is added successfully!");
		} else {
			System.out.println(port + " already exits!!!");
		}
		
//		String jsonFile = "./config.json";
//		addV2rayJson(jsonFile, 8600);
		
//		String fileName= "./all.sh";
//		int port = 8600;
//		int limit = 10; // 10G/month
//		appendShell(fileName, port, limit);
		
		//delete
//		String jsonFile = "./ssr.json";
//		int port = 6804;
//		boolean isDeleted = deleteSsrJson(jsonFile, port);
		
//		String jsonFile = "./v2.json";
//		int port = 8600;
//		boolean isDeleted = deleteV2rayJson(jsonFile, port);
		
//		String fileName= "./all.sh";
//		int port = 6600;
//		boolean isDeleted = deleteAllShell(fileName, port);
//		
//		if (isDeleted) {
//			System.out.println(port +" is deleted successfully!");
//		} else {
//			System.out.println(port + " not found!!!----------");
//		}
	}
	
	public static boolean deleteAllShell(String fileName, int port) {
		File inputFile = new File(fileName);
		StringBuffer content = new StringBuffer();

		try {
			BufferedReader reader = new BufferedReader(new FileReader(inputFile));
			String currentLine = null;

			while ((currentLine = reader.readLine()) != null) {
				// trim newline when comparing with lineToRemove
				String trimmedLine = currentLine.trim();
				if (trimmedLine.contains(port + "@")) {
					continue;
				}
				content.append(currentLine + System.getProperty("line.separator"));
			}
			reader.close();

			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
			writer.write(content.toString());
			writer.close();

			return true;

		} catch (Exception e) {
			System.out.println(e);
		}
		
		return false;
	}
	
	public static void appendShell(String filename, int port, int limit) {
		String separator = System.getProperty("line.separator");  
		// old one
//		String content = "/usr/bin/v2ray/v2ctl api --server=127.0.0.1:54979 StatsService.GetStats 'name: \"user>>>{0}@v2ray.com>>>traffic>>>downlink\" reset: false' | grep value >> /etc/v2ray/log/{1}-down.txt";
//		String message = MessageFormat.format(content, "8600", "8600");
		// new one
		String content = "/usr/local/bin/v2ctl api --server=127.0.0.1:54979 StatsService.GetStats 'name: \"user>>>%s@v2ray.com>>>traffic>>>downlink\" reset: true' | grep value >> /usr/local/etc/v2ray/log/%s-down.txt";
//		String message = String.format(content, "love8600", "love8600");
		String prefix = prefixMap.get(limit);
		String userId = prefix + port;
		String message = String.format(content, userId, userId);
		try
		{
		    FileWriter fw = new FileWriter(filename, true); //the true will append the new data
		    fw.write(message);//appends the string to the file
		    fw.write(separator);
		    fw.close();
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		    System.err.println("IOException: " + ioe.getMessage());
		}
		System.out.println("---done---");
	}
	
	public static boolean deleteV2rayJson(String jsonFile, int port) {
		boolean isDeleted = false;
		
		String mudb = readToString(jsonFile);
		try {
			JSONObject json = JSONObject.fromObject(mudb);
			JSONArray inbounds = json.getJSONArray("inbounds");

			JSONObject first = inbounds.getJSONObject(0);
			JSONObject settings = first.getJSONObject("settings");
			JSONArray clients = settings.getJSONArray("clients");
			for (int i = 0; i < clients.size(); i++) {
				JSONObject child = clients.getJSONObject(i);
				String email = child.getString("email");
				if (email.contains(port + "@")) {
					clients.remove(i);
					isDeleted = true;
					break;
				}
			}
			
			if (isDeleted) {
				String str = json.toString(); // 将保存的数据变为字符串
				String niceFormattedJson = JsonWriter.formatJson(str);
				writeV2rayFile(niceFormattedJson, jsonFile);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
		return isDeleted;
	}
	
	public static void addV2rayJson(String jsonFile, int port, int limit) {
		
		String mudb = readToString(jsonFile);
		try {
			JSONObject json = JSONObject.fromObject(mudb);
			JSONArray inbounds = json.getJSONArray("inbounds");

			JSONObject first = inbounds.getJSONObject(0);
			JSONObject settings = first.getJSONObject("settings");
			JSONArray clients = settings.getJSONArray("clients");
			
			JSONObject child = generateV2rayUser(port, limit);
			clients.add(child);
			String str = json.toString(); // 将保存的数据变为字符串
			String niceFormattedJson = JsonWriter.formatJson(str);
			writeV2rayFile(niceFormattedJson, jsonFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static JSONObject generateV2rayUser(int port, int limit) {

		String prefix = prefixMap.get(limit);
		String suffix = "@v2ray.com";

		String email = prefix + port + suffix;
		// generate uuid
		String uuid = UUID.randomUUID().toString();
		// use port to replace the last 4 digits
		// de8f9d4c-403e-4f73-6411-3d38335fe28b
		String[] arr = uuid.split("-");
		arr[3] = String.valueOf(port);
		StringBuffer buffer = new StringBuffer();
		for (String s : arr) {
			buffer.append(s).append("-");
		}
		buffer.deleteCharAt(buffer.length() - 1);

		JSONObject obj = generateJSON(buffer.toString(), 0, 10, email);

		return obj;
	}

	private static JSONObject generateJSON(String id, int level, int alterId, String email) {
		JSONObject obj = new JSONObject(); // 定义一个描述json的数据
		try {
			obj.put("alterId", alterId);
			obj.put("level", level);
			obj.put("email", email);
			obj.put("id", id);

		} catch (JSONException e) {
			e.printStackTrace();
		}

		String str = obj.toString(); // 将保存的数据变为字符串
		System.out.println(str + ",");

		return obj;
	}
	
	public static boolean deleteSsrJson(String jsonFile, int port) {
		String mudb = readToString(jsonFile);
		boolean isDeleted = false;
		try {
			JSONObject json = JSONObject.fromObject(mudb);
			JSONArray arr = json.getJSONArray("result");
			for (int i = 0; i < arr.size(); i++) {
				JSONObject child = arr.getJSONObject(i);
				int portNumber = child.getInt("port");
				if (portNumber == port) {
					arr.remove(i);
					isDeleted = true;
					break;
				}
			}
			
			if (isDeleted) {
				String str = json.toString(); // 将保存的数据变为字符串
				String niceFormattedJson = JsonWriter.formatJson(str);
				writeSsrFile(niceFormattedJson, jsonFile);
			}
			
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
		
		return isDeleted;
	}

	public static boolean addSsrJson(String jsonFile, int port) {
		String mudb = readToString(jsonFile);
		try {
			JSONObject json = JSONObject.fromObject(mudb);
			JSONArray arr = json.getJSONArray("result");
			for (int i = 0; i < arr.size(); i++) {
				JSONObject child = arr.getJSONObject(i);
				int portNumber = child.getInt("port");
				if (portNumber == port) {
					//already exit.
					return false;
				}
			}

			JSONObject child = generateJSON(port);
			arr.add(child);
			String str = json.toString(); // 将保存的数据变为字符串
			String niceFormattedJson = JsonWriter.formatJson(str);
			writeSsrFile(niceFormattedJson, jsonFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return true;
	}

	private static void writeSsrFile(String str, String jsonFile) {
		try {
			FileWriter fw = new FileWriter(jsonFile);
			fw.write(str);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void writeV2rayFile(String str, String jsonFile) {
		try {
			FileWriter fw = new FileWriter(jsonFile);
			fw.write(str);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * new function
	 * 
	 * @param port
	 * @param passwd
	 * @param user
	 */
	private static JSONObject generateJSON(int port) {
		String user = "ssr" + port;
		String password = RandomStringUtils.randomAlphabetic(10) + port;
		JSONObject obj = new JSONObject(); // 定义一个描述json的数据
		try {
			obj.put("port", port);
			obj.put("passwd", password);
			obj.put("user", user);
			// addtional
			obj.put("method", "aes-256-cfb");
			obj.put("obfs", "tls1.2_ticket_auth_compatible");
			obj.put("protocol", "origin");

		} catch (JSONException e) {
			e.printStackTrace();
		}

		String str = obj.toString(); // 将保存的数据变为字符串
		System.out.println(str + ",");

		return obj;
	}
}
