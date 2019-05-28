package com.cbt.util;

import java.io.IOException;
import java.util.Properties;

public class JdbcReadUtil {  
    private static Properties p = new Properties();  
  
    /** 
     * 读取properties配置文件信息 
     */  
    static{  
        try {  
            p.load(JdbcReadUtil.class.getClassLoader().getResourceAsStream("jdbc.properties"));  
        } catch (IOException e) {  
            e.printStackTrace();   
        }  
    }  
    /** 
     * 根据key得到value的值 
     */  
    public static String getJdbcUrl()  
    {  
        return p.getProperty("url");  
    }  
    /** 
     * 根据key得到value的值 
     */  
    public static String getUserName() 
    {  
    	return p.getProperty("username");  
    }  
    /** 
     * 根据key得到value的值 
     */  
    public static String getPwd()  
    {  
    	return p.getProperty("password");  
    }  
  
    
}  
	
	



