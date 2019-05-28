package com.cbt.util;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 获取当前时间
 * 
 * @since 2013-12-03
 */
public class DateFormat {

	/**
	 * 
	 * @param source
	 * @return
	 */
	public static String format()
	{
		long time = System.currentTimeMillis();
	  	Date date = new Date(time);    	
	  	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	  	String currentTime = sdf.format(date);
		
		
		return currentTime;
	}
	public static String formatDate1(String date) throws ParseException
	{
//		long time = System.currentTimeMillis();
//		Date date = new Date(time);    	
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date time = sdf.parse(date); 
		String currentTime = sdf.format(time);
		
		
		return currentTime;
	}


	public static String formatDate2(String date) throws ParseException
	{
//		long time = System.currentTimeMillis();
//		Date date = new Date(time);    	
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date time = sdf.parse(date); 
		String currentTime = sdf.format(time);
		
		
		return currentTime;
	}
	public static String currentDate() throws ParseException
	{
	    long time = System.currentTimeMillis();
	    Date date = new Date(time);    	
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    String currentTime = sdf.format(date);		
		return currentTime;
	}
	
	public static String currentDateString() throws ParseException
	{
	    long time = System.currentTimeMillis();
	    Date date = new Date(time);    	
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    String currentTime = sdf.format(date);	
	    currentTime = currentTime.replace("-","");
		return currentTime;
	}
	
	
	
	public static String weekLaterDate() throws ParseException
	{
		   Calendar calendar = Calendar.getInstance();  
	       calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + 7);  
	       Date today = calendar.getTime();  
	       SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
	       String result = format.format(today);  
	       return result;
	}
	
	
	public static String calDate(String time,int days)throws ParseException{
		
		   Calendar calendar=Calendar.getInstance();  
		   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		   calendar.setTime(sdf.parse(time)); 
		   calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH)+days);//让日期加1  		  
		    int year = calendar.get(Calendar.YEAR);  
		    int month = calendar.get(Calendar.MONTH) + 1;  
		    int day = calendar.get(Calendar.DAY_OF_MONTH);  
		    String date = year + "-" + month + "-" + day ;
		    
		    
		    return formatDate1(date); 
	   
	}
	
	/**
	 * 比较两个日期大小（DATE1大 1 Date2大 -1  其他情况为0）
	 * @param DATE1
	 * @param DATE2
	 * @return
	 */
	public static int compare_date(String date1, String date2) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dt1 = df.parse(date1);
            Date dt2 = df.parse(date2);
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }
	
	public static int currentDateFormat(){
		Calendar calendar = Calendar.getInstance();
		 int day = calendar.get(Calendar.DATE);
		 int year = calendar.get(Calendar.YEAR);
		 int month = calendar.get(Calendar.MONTH)+1;
		 Integer nowDate = 0;
		 if(month<10){
		 	nowDate = Integer.parseInt(year+"0"+month+""+day);
		 }else{
			 nowDate = Integer.parseInt(year+""+month+""+day); 
		 }
		 
		 return nowDate;
	}
	
	
	
	
}

