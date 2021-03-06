package com.cbt.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;





/**
 * httpclient获取图片后进行处理
 * @ClassName HttpClientDownloadUtil 
 * @Description TODO
 * @author zj
 * @date 2018年5月5日 上午10:25:35
 */
public class HttpClientDownloadUtil {

	public Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	public static String getImg(String value) throws Exception {
		    String newName = "";
			value = new String(value.getBytes(), "utf-8");
			
//			String file1="http://112.64.174.34:43888/static_img/"+ value; 
			String file1=GetServerPathUtil.getInnerServerPath()+"/static_img/"+ value; 			
			
			String path = UploadAndDownloadPathUtil.getQuotationPath() + "img" + File.separator;
			File file = new File(path);
			if  (!file.exists()  && !file .isDirectory())      
			{         
			   file .mkdir();     
			}  

			try {
					URL url = new URL(file1);
					   // 打开连接
					   URLConnection con = url.openConnection();
					   //设置请求超时为5s
					   con.setConnectTimeout(120*1000);
					   // 输入流
					   InputStream is = con.getInputStream();

					   // 1K的数据缓冲
					   byte[] bs = new byte[1024];
					   // 读取到的数据长度
					   int len;
					   // 输出的文件流
					  File sf=new File(path);
					  if(!sf.exists()){
					  sf.mkdirs();
					  }
//				 	  long time = System.currentTimeMillis();
//			    	  Date date = new Date(time);    	
//			    	  SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
//					  newName = sdf.format(date) + '.' + FilenameUtils.getExtension(value);
					  newName = OperationFileUtil.changeFilenameUUID(value);
//					  int num = 1;
//					  File storefile = new File(path,newName);
//						 
//							//LOG.warn("storefile's path: " + storefile.toString());
//						 String sb3=null;
//							for(int i=0;storefile.exists();i++){//如果存在同名的附件，则在后面添加数字区分
//								 //获取文件名称后面的文件组后一个.的下标（后缀名）
//								num++;
//					            int index = newName.lastIndexOf(".");
//					            //String sb = value.substring(0,index)+i;
//					            sb3 = FilenameUtils.removeExtension(newName)+num;
//					            newName = sb3+newName.substring(index);
//								storefile = new File(path,newName);
//							 }
					  
					  OutputStream os = new FileOutputStream(path+File.separator + newName);
					   // 开始读取
					   while ((len = is.read(bs)) != -1) {
					     os.write(bs, 0, len);
					   }
					   // 完毕，关闭所有链接
					   os.close();
					   is.close();
//					   String compressName =  OperationFileUtil.changeFilenameZip(newName);
//					   ImageCompressUtil.saveMinPhoto(path+newName, path + compressName, 150, 1d);
					   return newName;
					} catch (Exception e) {
	                e.printStackTrace();
	            } finally {
	                
	            }
			return newName;		
	}
	

	
	
	//获取抓取的1688电子产品图片
	public static String getImgs(String file1) throws Exception {
	    String newName = "";					
		String path = UploadAndDownloadPathUtil.getQuotationPath() + "pet" + File.separator;
		File file = new File(path);
		if  (!file.exists()  && !file .isDirectory())      
		{         
		   file .mkdir();     
		}  

		try {
			    newName = FilenameUtils.getName(file1);
				URL url = new URL(file1);
				   // 打开连接
				   URLConnection con = url.openConnection();
				   con.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
				   //设置请求超时为5s
				   con.setConnectTimeout(120*1000);
				   // 输入流
				   InputStream is = con.getInputStream();

				   // 1K的数据缓冲
				   byte[] bs = new byte[1024];
				   // 读取到的数据长度
				   int len;
				   // 输出的文件流
				  File sf=new File(path);
				  if(!sf.exists()){
				  sf.mkdirs();
				  }
				  
				  OutputStream os = new FileOutputStream(path+File.separator + newName);
				   // 开始读取
				   while ((len = is.read(bs)) != -1) {
				     os.write(bs, 0, len);
				   }
				   // 完毕，关闭所有链接
				   os.close();
				   is.close();

				   return newName;
				} catch (Exception e) {
                e.printStackTrace();
            } finally {
                
            }
		return newName;		
}

	
	
	
	
	
	
    public static void main (String[] args){
    	try {
    		String quoteImg = getImg("20180424092717-电池外壳 5600套一年.7z");
    		System.out.println(quoteImg);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
}
    
    
	

