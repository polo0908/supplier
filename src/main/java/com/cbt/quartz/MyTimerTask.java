package com.cbt.quartz;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.cbt.util.DbPoolUtil;
import com.cbt.util.SerializeUtil;

import net.sf.json.JSONObject;

public class MyTimerTask extends TimerTask{
    
	
	public static Logger LOG = Logger.getLogger(TimerTask.class);
	
	@Override
	public void run(){
		 System.out.println("=========开始获取汇率=========");

		 String[]  countries = {"USD","CNY","GBP","EUR"}; 
	     for(String str:countries){
	     	//scur  源币种
	     	//tcur  目标币种
		    	URL u = null;
				try {
					u = new URL("http://api.k780.com:88/?app=finance.rate&scur=USD&tcur="+str+"&appkey=34406&sign=771c84f0d61636ff7bbc1a30db6d1881&format=json");
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
		        InputStream in = null;
				try {
					in = u.openStream();
				} catch (IOException e) {
					e.printStackTrace();
				}
		        ByteArrayOutputStream out=new ByteArrayOutputStream();
		        try {
		            byte buf[]=new byte[1024];
		            int read = 0;
		            try {
						while ((read = in.read(buf)) > 0) {
						    out.write(buf, 0, read);
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        }  finally {
		            if (in != null) {
		                try {
							in.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		            }
		        }
		        byte b[]=out.toByteArray( );
		        JSONObject json = null;
				try {
					json = JSONObject.fromObject(new String(b,"utf-8"));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
		        Object obj = json.get("result");
		        System.out.println(obj);
		        Map<Object, Object> map = SerializeUtil.JsonToMap(obj.toString());
		        String currencyShorthand = map.get("tcur").toString();
		        Double rate = Double.parseDouble(map.get("rate").toString());
					updateRate(currencyShorthand,rate);
		        
	       }
	         System.out.println("=========更新汇率结束=========");
	}  
     
     
   //更新已有数据。
 	public static void updateRate(String currencyShorthand,Double rate){
 		Connection conn = DbPoolUtil.getConnection();
 		String updateSql = "update amount_unit"
 				+ " set exchange_rate= ?,update_date=now()"    
 				+ " where currency_shorthand = ?;";
 		try{
 			PreparedStatement psmt = conn.prepareStatement(updateSql);
 			
 			psmt.setDouble(1, rate);
 			psmt.setString(2, currencyShorthand);
 			
 			psmt.execute();

 		} catch (SQLException e) {
 			e.printStackTrace();
 			LOG.error("<<<<<<<<<<<<<<<<<<MyTimerTask>>>>>>>>>>>>>>>>>>>更新汇率失败"+e.getMessage());
 		}	finally {
 			DbPoolUtil.returnConnection(conn);
 		}
 	} 
	 
   
//   public void updateRate(String currencyShorthand,Double rate){
//		try {
//			AmountUnit amountUnit = amountUnitService.queryByCurrencyShorthand(currencyShorthand);
//			amountUnit.setExchangeRate(rate);
//			amountUnitService.updateRate(amountUnit);
//		} catch (Exception e) {
//			e.printStackTrace();
//			LOG.error("<<<<<<<<<<<<<<<<<<MyTimerTask>>>>>>>>>>>>>>>>>>>更新汇率失败"+e.getMessage());
//		}
//   }

}
