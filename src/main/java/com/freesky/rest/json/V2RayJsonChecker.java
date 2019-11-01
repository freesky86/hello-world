package com.freesky.rest.json;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class V2RayJsonChecker {

	public static void main(String[] args) {
//		readJSON();
		//August
		generateUUID(10, 6800);
	}

	public static void readJSON() {
		String json = readToString("./src/config.json");
		Set<String> ids = new HashSet<String>();
		Set<String> emails = new HashSet<String>();
		try {
			JSONObject root = new JSONObject(json);
			//old config.json format
//			JSONObject inbound = root.getJSONObject("inbound");
			//new config.json format
			JSONArray inbounds = root.getJSONArray("inbounds");
//			JSONObject settings = inbound.getJSONObject("settings");
			JSONObject settings = inbounds.getJSONObject(0).getJSONObject("settings");
			JSONArray clients = settings.getJSONArray("clients");

			for (int i = 0; i < clients.length(); i++) {
				JSONObject client = clients.getJSONObject(i);
				String id = client.getString("id");
				String email = client.getString("email");
				ids.add(id);
				emails.add(email);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		System.out.println("id number: " + ids.size());
		System.out.println("email number: " + emails.size());
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

	/**
	 * 1. 生成UUID并产出JSON字符串
	 * 2. 为控制台产生的字符串删除最后一个逗号(,)，并包裹在下面的字符串中
	 * {"clients":[ 
	 * 
	 * ]}
	 * 3. 打开 https://www.json.cn/ 或  http://tool.oschina.net/codeformat/json/ 把上面的字符串copy到左边，右边就会产生格式化后的JSON。
	 * 4. copy格式化后的JSON，添加到原来的v2ray配置文件 config.json 的"clients"后面，注意用逗号分割。
	 * note: 把修改好的 config.json 覆盖 /ROOT/net/ 目录下的同名文件，并上传到 ifreesky.cn 服务器。
	 * 
	 * @param count
	 * @param port
	 */
	public static void generateUUID(int count, int port) {
		int i = 0;
		String prefix = "love";
		String suffix = "@v2ray.com";
		while (i < count) {
			String email = prefix + port + suffix;
			//generate uuid
			String uuid = UUID.randomUUID().toString();
			//use port to replace the last 4 digits
			//de8f9d4c-403e-4f73-6411-3d38335fe28b
			String[] arr = uuid.split("-");
			arr[3] = String.valueOf(port);
			StringBuffer buffer = new StringBuffer();
			for (String s : arr) {
				buffer.append(s).append("-");
			}
			buffer.deleteCharAt(buffer.length() - 1);
						
//			System.out.println(port + "  " + uuid + "  " + email);
//			System.out.println("    " + "  " + buffer.toString());
			
//			System.out.println(port + "  " + buffer.toString() + "  " + email);
			
			generateJSON(buffer.toString(), 0, 10, email);
			
			port ++;			
			i++;
		}

	}
	
	private static void generateJSON(String id, int level, int alterId, String email) {
		JSONObject obj = new JSONObject() ; // 定义一个描述json的数据
		try {
			obj.put("alterId", alterId) ;
			obj.put("level", level) ;
			obj.put("email", email) ;
			obj.put("id", id) ;
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	
		String str = obj.toString() ; // 将保存的数据变为字符串
		System.out.println(str + ",");
		
	}

}
