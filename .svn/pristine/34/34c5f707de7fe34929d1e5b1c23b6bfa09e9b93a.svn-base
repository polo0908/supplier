package com.cbt.util;

import java.io.IOException;
import java.util.Properties;

public class NbMailAddressUtil {  
    private static Properties p = new Properties();  
  
    /** 
     * 读取properties配置文件信息 
     */  
    static{  
        try {  
            p.load(NbMailAddressUtil.class.getClassLoader().getResourceAsStream("nbMailAddress.properties"));  
        } catch (IOException e) {  
            e.printStackTrace();   
        }  
    }  
    /** 
     * 根据key得到value的值 
     */  
    public static String getSendMailPath()  
    {  
        return p.getProperty("sendMailPath");  
    }  
    
    
    /**
     * 查询nbmail接口地址（发送后保存）
     * @author polo
     * 2017年6月6日
     *
     */
    public static String getSendMailPathSave()  
    {  
    	return p.getProperty("sendMailPath_save");  
    }  
    
    
    
    
    public static String getDomainName()  
    {  
    	return p.getProperty("domainName");  
    }  
    
    
    
    public static String getQuotationAttr()  
    {  
    	return p.getProperty("quotationAttr");  
    }  
    
    
    public static String getInviteLogin()  
    {  
    	return p.getProperty("inviteLogin");  
    }  

}  
	
	



