/**
 * 
 */
package com.freesky.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

import com.freesky.dto.Statistics;
import com.freesky.dto.Stats;

/**
 * @author Freesky
 *
 */
public class FileUtils {
	
	private static final Logger logger = Logger.getLogger(FileUtils.class);
	
    // "/etc/v2ray/daily/"
    // "C:\\Temp\\"
//    private static final String FILE_FOLDER = "C:\\Temp\\";
	private static final String FILE_FOLDER = "/usr/local/etc/v2ray/daily/";

    private static final String FILE_NAME = "latest.txt";
    
    private static final String WHITESPACE =  "&nbsp;";
	
    public static void main(String[] args) {
//         File file = new File(FILE_FOLDER);
//         System.out.println(file.getAbsolutePath());

        System.out.println(readFile2Table());
    }
    
    public static void readFile2Bean(Statistics bean) {
    	String filePath = FILE_FOLDER + FILE_NAME;

        readFile2Bean(filePath, bean);
    }
    
    public static void readFile2Bean(String filePath, Statistics bean) {
        try {
            InputStream is = new FileInputStream(filePath);
            String line;
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            line = reader.readLine();
            while (line != null) {
            	if (line.contains("EST")) {
            		bean.setTitle(line);
            	} else if (line.contains("consumes")) {
            		int pos = line.indexOf("consumes");
            		Stats obj = new Stats();
            		obj.setName(line.substring(0, pos + "consumes".length() + 1));
            		obj.setValue(line.substring(pos + "consumes".length() + 1));
            		bean.getList().add(obj);
            	} else if (line.contains("is")) {
            		int pos = line.indexOf("is");
            		Stats obj = new Stats();
            		obj.setName(line.substring(0, pos + "is".length() + 1));
            		obj.setValue(line.substring(pos + "is".length() + 1));
            		bean.getList().add(obj);
            	}               
                
                line = reader.readLine();
            }
            
            reader.close();
            is.close();
        } catch (IOException e) {
        	logger.error(e.getMessage());
            System.err.println(e.getMessage());
            
            bean.setTitle(e.getMessage());
        }

    }
    
    

    public static String readFile2Table() {    	
        String filePath = FILE_FOLDER + FILE_NAME;

        return readFile2Table(filePath);

    }

    public static String readFile2Table(String filePath) {
        StringBuffer content = new StringBuffer();
        content.append("<table border=\"0\"> \n");
        
        try {
            InputStream is = new FileInputStream(filePath);
            String line;
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            line = reader.readLine();
            while (line != null) {
            	content.append("<tr>");
            	if (line.contains("EST")) {
            		content.append("<th>");
            		content.append(line);
            		content.append("</th>");
            	} else if (line.contains("consumes")) {
            		content.append("<td align=\"right\">");
            		int pos = line.indexOf("consumes");
            		content.append(line.substring(0, pos + "consumes".length() + 1)).append(WHITESPACE);
            		content.append("</td>");
            		
            		content.append("<td align=\"left\">");
            		content.append(line.substring(pos + "consumes".length() + 1));
            		content.append("</td>");
            	} else if (line.contains("is")) {
            		content.append("<td align=\"right\">");
            		int pos = line.indexOf("is");
            		content.append(line.substring(0, pos + "is".length() + 1)).append(WHITESPACE);
            		content.append("</td>");
            		
            		content.append("<td align=\"left\">");
            		content.append(line.substring(pos + "is".length() + 1));
            		content.append("</td>");
            	} else {
            		content.append("<td>");
            		content.append(line);
            		content.append("</td>");
            	}
            	
                content.append("</tr>");
              
                
                line = reader.readLine();
            }
            content.append("</table>");
            
            reader.close();
            is.close();
        } catch (IOException e) {
        	logger.error(e.getMessage());
            System.err.println(e.getMessage());
        }

        return content.toString();

    }

}
