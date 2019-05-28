package com.cbt.util;

import com.alibaba.fastjson.JSON;
import com.cbt.entity.PicGps;
import com.cbt.entity.ReadSmartWatchExcelVO;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.json.Json;
import javax.json.JsonArray;

/**
 * 读取Excel
 * 
 * @author polo
 */
public class ReadAlibabaExcelUtils {
	private Logger logger = LoggerFactory.getLogger(ReadAlibabaExcelUtils.class);
	private Workbook wb;
	private Sheet sheet;
	private Row row;
	private String gen;

	public ReadAlibabaExcelUtils(String filepath) {
		if (filepath == null) {
			return;
		}
		String ext = filepath.substring(filepath.lastIndexOf("."));
		try {
			InputStream is = new FileInputStream(filepath);
			if (".xls".equals(ext)) {
				wb = new HSSFWorkbook(is);
				gen = "xls";
			} else if (".xlsx".equals(ext)) {
				wb = new XSSFWorkbook(is);
				gen = "xlsx";
			} else {
				wb = null;
			}
		} catch (FileNotFoundException e) {
			logger.error("FileNotFoundException", e);
		} catch (IOException e) {
			logger.error("IOException", e);
		}
	}

	/**
	 * 读取Excel表格表头的内容
	 * 
	 * @param
	 * @return String 表头内容的数组
	 * @author polo
	 */
	public String[] readExcelTitle() throws Exception {
		if (wb == null) {
			throw new Exception("Workbook对象为空！");
		}
		sheet = wb.getSheetAt(0);
		row = sheet.getRow(0);
		// 标题总列数
		int colNum = row.getPhysicalNumberOfCells();
		String[] title = new String[colNum];
		for (int i = 0; i < colNum; i++) {
			// title[i] = getStringCellValue(row.getCell((short) i));
			title[i] = row.getCell(i).getCellFormula();
		}
		return title;
	}

	/**
	 * 读取Excel数据内容 (读取电子手表excel)
	 * 
	 * @param
	 * @return Map 包含单元格数据内容的Map对象
	 * @author polo
	 */
	public void readExcelPlasticContent(List<ReadSmartWatchExcelVO> list)
			throws Exception {
		if (wb == null) {
			throw new Exception("Workbook对象为空！");
		}
		int tl = list.size();

		sheet = wb.getSheetAt(0);
		// 得到总行数
//		int rowNum = sheet.getLastRowNum();
		row = sheet.getRow(0);
		int colNum = row.getPhysicalNumberOfCells();
		// 获取产品编号所在列		
		int col_pid = 0;
		// 获取标题所在列	
		int col_title = 0;
		//获取关键词所在列
		int col_keyword = 0;
		//获取功能所在列
		int col_function = 0;
		//获取表壳材质所在列
		int col_watchcaseMaterial = 0;
		//获取表带材质所在列
		int col_strapMaterial = 0;
		//获取操作系统所在列
		int col_system = 0;
		//获取特性所在列
		int col_speciality = 0;
		//获取品牌所在列
		int col_brand = 0;
		//获取主图所在列
		int col_pic = 0;
		//获取产品类型所在列
		int col_type = 0;

		for (int i = 0; i < 9+tl; i++) {
			row = sheet.getRow(9+i);
			
			//获取各类型所在列
			for (int j = 0; j < colNum+1; j++) {
				Object obj = null;
				try {
					obj = getCellFormatValue(row.getCell(j));
					System.out.println(obj);
					if (obj != null) {
						String str = obj.toString();
						if (str.contains("产品编号")) {
							col_pid = j;
						}
						if (str.contains("产品标题")) {
							col_title = j;
						}
						if (str.contains("主关键词")) {
							col_keyword = j;
						}
						if (str.contains("功能")) {
							col_function = j;
						}
						if (str.contains("表壳材质")) {
							col_watchcaseMaterial = j;
						}
						if (str.contains("表带材质")) {
							col_strapMaterial = j;
						}
						if (str.contains("操作系统")) {
							col_system = j;
						}
						if (str.contains("特性")) {
							col_speciality = j;
						}
						if (str.contains("品牌")) {
							col_brand = j;
						}
						if (str.contains("产品主图1")) {
							col_pic = j;
						}
						if (str.contains("产品类型")) {
							col_type = j;
						}
						
						
					}
				} catch (Exception ex) {
					 System.out.println("数据存在错误 ；定位:行"+9+i+" 列:"+j);
					 ex.printStackTrace();
				}
			}
			
			
			// 找出当前产品单价所在列数，取值
			if (col_pid != 0) {
				Cell nCell = row.getCell(col_pid);
				nCell.setCellValue(list.get(i).getPid());
			}
			if (col_title != 0) {
				Cell nCell = row.getCell(col_title);
				nCell.setCellValue(list.get(i).getTitle());
			}
			if (col_keyword != 0) {
				Cell nCell = row.getCell(col_keyword);
				nCell.setCellValue(list.get(i).getKeyword());
			}
			if (col_function != 0) {
				Cell nCell = row.getCell(col_function);
				nCell.setCellValue(list.get(i).getFunction());
			}
			if (col_title != 0) {
				Cell nCell = row.getCell(col_title);
				nCell.setCellValue(list.get(i).getTitle());
			}
			if (col_watchcaseMaterial != 0) {
				Cell nCell = row.getCell(col_watchcaseMaterial);
				nCell.setCellValue(list.get(i).getWatchcaseMaterial());
			}
			if (col_strapMaterial != 0) {
				Cell nCell = row.getCell(col_strapMaterial);
				nCell.setCellValue(list.get(i).getStrapMaterial());
			}
			if (col_system != 0) {
				Cell nCell = row.getCell(col_system);
				nCell.setCellValue(list.get(i).getSystem());
			}
			if (col_speciality != 0) {
				Cell nCell = row.getCell(col_speciality);
				nCell.setCellValue(list.get(i).getSpeciality());
			}
			if (col_brand != 0) {
				Cell nCell = row.getCell(col_brand);
				nCell.setCellValue(list.get(i).getBrand());
			}
			if (col_pic != 0) {
				Cell nCell = row.getCell(col_pic);
				nCell.setCellValue(list.get(i).getPic());
			}
			if (col_type != 0) {
				Cell nCell = row.getCell(col_type);
				nCell.setCellValue(list.get(i).getType());
			}			
		
		}
		
		FileOutputStream fs = new FileOutputStream(UploadAndDownloadPathUtil.getQuotationPath() + File.separator + DateFormat.currentDate().replace("-", ".") + ".xls", false);
		wb.write(fs);
		fs.close();
	}

	
	
	/**
	 * 读取数据库抓取类型导入Excel（电子类产品）
	 * 
	 * @param
	 * @return Map 包含单元格数据内容的Map对象
	 * @author polo
	 */
	public void readExcelContent(List<ReadSmartWatchExcelVO> list)
			throws Exception {
		try {
			if (wb == null) {
				throw new Exception("Workbook对象为空！");
			}
			int tl = list.size();

			sheet = wb.getSheetAt(0);
			wb.cloneSheet(0);						            //复制工作簿
			wb.setSheetName(1, "product");		//设置工作簿名称
			Sheet sheetAt = wb.getSheetAt(1);					
			// 得到总行数
//		int rowNum = sheet.getLastRowNum();
//		row = sheet.getRow(0);

			// 获取产品编号所在列		
			int col_pid = 0;
			// 获取标题所在列	
			int col_title = 1;
			//获取关键词所在列
			int col_keyword = 2;
			//获取操作系统所在列
			int col_system = 3;
			//获取主图所在列
			int col_pic = 4;
			//获取类别所在列
			int col_type = 5;
			Row nRow = null;
			Cell nCell = null;
//			nRow = sheet.getRow(1);
//			nCell = nRow.getCell(0);
//			nCell.setCellValue(11111);

			
			for (int i = 0; i < tl; i++) {
//			Row row2 = sheet.getRow(1+i);		
				    nRow = sheet.getRow(i+1);
				   // 找出当前产品单价所在列数，取值
				    nCell = nRow.getCell(col_pid);	
				    if(nCell == null){
				    	nCell = nRow.createCell(col_pid);
				    }
					nCell.setCellValue(list.get(i).getPid());

					nCell = nRow.getCell(col_title);
				    if(nCell == null){
				    	nCell = nRow.createCell(col_title);
				    }
					nCell.setCellValue(list.get(i).getTitle());

					nCell = nRow.getCell(col_keyword);
				    if(nCell == null){
				    	nCell = nRow.createCell(col_keyword);
				    }
					nCell.setCellValue(list.get(i).getKeyword());

					nCell = nRow.getCell(col_system);
				    if(nCell == null){
				    	nCell = nRow.createCell(col_system);
				    }
					nCell.setCellValue(list.get(i).getSystem());

					nCell = nRow.getCell(col_pic);
				    if(nCell == null){
				    	nCell = nRow.createCell(col_pic);
				    }
					nCell.setCellValue(list.get(i).getPic());

					nCell = nRow.getCell(col_type);
				    if(nCell == null){
				    	nCell = nRow.createCell(col_type);
				    }
					nCell.setCellValue(list.get(i).getType());
			
			}
			
			FileOutputStream fs = new FileOutputStream(UploadAndDownloadPathUtil.getQuotationPath() + File.separator + DateFormat.currentDate().replace("-", ".") + ".xlsx", false);
			wb.write(fs);
			fs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	/**
	 * 读取数据库抓取类型导入Excel（宠物类产品）
	 * 
	 * @param
	 * @return Map 包含单元格数据内容的Map对象
	 * @author polo
	 */
	public void readPetExcelContent(List<ReadSmartWatchExcelVO> list)
			throws Exception {
		try {
			if (wb == null) {
				throw new Exception("Workbook对象为空！");
			}
			int tl = list.size();

			sheet = wb.getSheetAt(0);				
			// 得到总行数
//		int rowNum = sheet.getLastRowNum();
//		row = sheet.getRow(0);

			// 获取产品编号所在列		
			int col_pid = 0;
			// 获取标题所在列	
			int col_title = 1;
			//获取关键词所在列
			int col_keyword = 2;
			//获取操作系统所在列
			int col_system = 3;
			//获取主图所在列
			int col_pic = 4;
			//获取类别所在列
			int col_type = 5;
			//获取价格所在列
			int col_price = 6;
			//获取图二所在列
			int col_pic2 = 7;
			
			Row nRow = null;
			Cell nCell = null;
//			nRow = sheet.getRow(1);
//			nCell = nRow.getCell(0);
//			nCell.setCellValue(11111);

			
			for (int i = 0; i < tl; i++) {
//			Row row2 = sheet.getRow(1+i);		
				    nRow = sheet.getRow(i+1);
				   //获取pid
				    nCell = nRow.getCell(col_pid);	
				    if(nCell == null){
				    	nCell = nRow.createCell(col_pid);
				    }
					nCell.setCellValue(list.get(i).getPid());

					
					//获取标题
					nCell = nRow.getCell(col_title);
				    if(nCell == null){
				    	nCell = nRow.createCell(col_title);
				    }
					nCell.setCellValue(list.get(i).getTitle());

					
					//获取关键词
					nCell = nRow.getCell(col_keyword);
				    if(nCell == null){
				    	nCell = nRow.createCell(col_keyword);
				    }
					nCell.setCellValue(list.get(i).getKeyword());

					
					//获取商品详情
					nCell = nRow.getCell(col_system);
				    if(nCell == null){
				    	nCell = nRow.createCell(col_system);
				    }
					nCell.setCellValue(list.get(i).getSystem());

					//获取主图
					nCell = nRow.getCell(col_pic);
				    if(nCell == null){
				    	nCell = nRow.createCell(col_pic);
				    }
					nCell.setCellValue(list.get(i).getPic());

					//获取类型
					nCell = nRow.getCell(col_type);
				    if(nCell == null){
				    	nCell = nRow.createCell(col_type);
				    }
					nCell.setCellValue(list.get(i).getType());
					
					//获取价格
					nCell = nRow.getCell(col_price);
				    if(nCell == null){
				    	nCell = nRow.createCell(col_price);
				    }
					nCell.setCellValue(list.get(i).getPrice());
					
					
					//获取其他图片
					String[] otherPic = list.get(i).getOtherPic();
					if(otherPic != null){
						for (int j = 0; j < otherPic.length; j++) {
							nCell = nRow.getCell(col_pic2+j);
							if(nCell == null){
						    	nCell = nRow.createCell(col_pic2+j);
						    }
							nCell.setCellValue(otherPic[j]);
						}
					}
                   
					
			
			}
			
			FileOutputStream fs = new FileOutputStream(UploadAndDownloadPathUtil.getQuotationPath() + File.separator + DateFormat.currentDate().replace("-", ".") + ".xlsx", false);
			wb.write(fs);
			fs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	/**
	 * 
	 * 根据Cell类型设置数据
	 * 
	 * @param cell
	 * @return
	 * @author polo
	 */
	private Object getCellFormatValue(Cell cell) {
		Object cellvalue = "";
		if (cell != null) {
			// 判断当前Cell的Type
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_NUMERIC:// 如果当前Cell的Type为NUMERIC
			case Cell.CELL_TYPE_FORMULA: {
				// 判断当前的cell是否为Date
				if (DateUtil.isCellDateFormatted(cell)) {
					// 如果是Date类型则，转化为Data格式
					// data格式是带时分秒的：2013-7-10 0:00:00
					// cellvalue = cell.getDateCellValue().toLocaleString();
					// data格式是不带带时分秒的：2013-7-10
					Date date = cell.getDateCellValue();
					cellvalue = date;
				} else {// 如果是纯数字

					// 取得当前Cell的数值
					cellvalue = String.valueOf(cell.getNumericCellValue());
				}
				break;
			}
			case Cell.CELL_TYPE_STRING:// 如果当前Cell的Type为STRING
				// 取得当前的Cell字符串
				cellvalue = cell.getRichStringCellValue().getString();
				break;
			default:// 默认的Cell值
				cellvalue = "";
			}
		} else {
			cellvalue = "";
		}
		return cellvalue;
	}

	/**  
	 * 判断字符串是否为数字  
	 *   
	 * @param str  
	 * @return  
	 */  
	public static boolean isNumeric(String str) {  
	    Pattern pattern = Pattern.compile("-?[0-9]+.?[0-9]+");  
	    Matcher isNum = pattern.matcher(str);  
	    if (!isNum.matches()) {  
	        return false;  
	    }  
	    return true;  
	}  

	
	/** 
     * 判断对象或对象数组中每一个对象是否为空: 对象为null，字符序列长度为0，集合类、Map为empty 
     *  
     * @param obj 
     * @return 
     */  
    public static boolean isNullOrEmpty(Object obj) {  
        if (obj == null)  
            return true;  
  
        if (obj instanceof CharSequence)  
            return ((CharSequence) obj).length() == 0;  
  
        if (obj instanceof Collection)  
            return ((Collection) obj).isEmpty();  
  
        if (obj instanceof Map)  
            return ((Map) obj).isEmpty();  
  
        if (obj instanceof Object[]) {  
            Object[] object = (Object[]) obj;  
            if (object.length == 0) {  
                return true;  
            }  
            boolean empty = true;  
            for (int i = 0; i < object.length; i++) {  
                if (!isNullOrEmpty(object[i])) {  
                    empty = false;  
                    break;  
                }  
            }  
            return empty;  
        }  
        return false;  
    } 
	
	
	public static void setPictures(String pic, HSSFSheet sheet, PicGps picGps)
			throws IOException {
		File imageFile = new File(pic);
		if (imageFile.exists()) {
			InputStream is = new FileInputStream(new File(pic));
			byte[] bytes = IOUtils.toByteArray(is);
			int pictureIdx = sheet.getWorkbook().addPicture(bytes,
					Workbook.PICTURE_TYPE_JPEG); // 扩展名可为.jpg/.jpeg/.png
			is.close();

			HSSFPatriarch drawing = sheet.createDrawingPatriarch(); 
			// 前面四个参数是图片偏移量
			HSSFClientAnchor anchor = new HSSFClientAnchor(picGps.getStartX(),
					picGps.getStartY(), picGps.getStopX(), picGps.getStopY(),
					(short) picGps.getStartCol(), picGps.getStartRow(),
					(short) picGps.getStopCol(), picGps.getStopRow());
			// add a picture shape
			anchor.setRow1(picGps.getStartRow()); // set position corner of the
													// picture
			anchor.setCol1(picGps.getStartCol());
			anchor.setRow2(picGps.getStopRow());
			anchor.setCol2(picGps.getStopCol());

			drawing.createPicture(anchor, pictureIdx);
		}

	}
	
    //查询产品数据
 	public static List<ReadSmartWatchExcelVO> getSmartWatch(){
 		Connection conn = DbPoolUtil.getConnection();
 		ResultSet rs = null;
 		String sql = "select a.pid,a.endetail, a.enname, a.keyword, a.eninfo, "+
			 				" CONCAT('https://img.import-express.com/importcsvimg/coreimg1/', a.custom_main_image) as main_pic,"+
			 				" b.name as category "+
			 				"from geekbuying_goods_ready a, 1688_category b "+
			 				"where a.catid1 = b.category_id "+
			 				" and a.eninfo not like '<img%' "+
			 				"order by b.name"; 
 		try{
 			 
	 			PreparedStatement psmt = conn.prepareStatement(sql);
	 			rs = psmt.executeQuery();
	 			List<ReadSmartWatchExcelVO> list = new ArrayList<ReadSmartWatchExcelVO>();
	 			while(rs.next()) {
	 			ReadSmartWatchExcelVO readSmartWatchExcelVO = new ReadSmartWatchExcelVO();
	 			//商品编号
	 			String pid = rs.getString("pid");
	 			readSmartWatchExcelVO.setPid(pid);
	 			//商品标题
	 			String title = rs.getString("enname");
	 			readSmartWatchExcelVO.setTitle(title);
	 			//商品关键词
	 			String keyword = rs.getString("keyword");
	 			readSmartWatchExcelVO.setKeyword(keyword);
	 			
	 			//整个详情页
	 			String content = rs.getString("eninfo");
	 			//类别
	 			String type = rs.getString("category");
	 			
	 			if(StringUtils.isNotBlank(content)){	 				
	 				content=content.replaceAll("<.*?>","");
	 				readSmartWatchExcelVO.setSystem(content);	
 			   }
	 			
	 			//获取品牌
	 			//获取标题第一个串为品牌
//		 		if(StringUtils.isNotBlank(title)){
//		 			String brand = "";
//		 			String[] split = title.split(" ");
//		 			if(split != null && split.length != 0){
//		 				brand = split[0];
//		 				readSmartWatchExcelVO.setBrand(brand);
//		 			}
//		 		}
	 			
		 		readSmartWatchExcelVO.setType(type);
	 			//商品主图
	 			try {
					String mainPic = rs.getString("main_pic");
					String pic = HttpClientDownloadUtil.getImgs(mainPic);
					readSmartWatchExcelVO.setPic(pic);
				} catch (Exception e) {
				   continue;
				}
                list.add(readSmartWatchExcelVO);
 		        }
 	
	 		return list;	
	 			

 		} catch (SQLException e) {
 			e.printStackTrace();
 			return null;
 		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}	finally {
 			DbPoolUtil.returnConnection(conn);
 		}
 	}
	
 	
 	
 	
 	
 	
 	
 	
 	
 	  //查询产品数据
 	 	public static List<ReadSmartWatchExcelVO> getPets(){
 	 		Connection conn = DbPoolUtil.getConnection();
 	 		ResultSet rs = null;
 	 		String sql = "select pid,price,custom_main_image,img,enname,endetail,keyword,type,remotpath from upload_ali_pet "; 
 	 		try{
 	 			 
 		 			PreparedStatement psmt = conn.prepareStatement(sql);
 		 			rs = psmt.executeQuery();
 		 			List<ReadSmartWatchExcelVO> list = new ArrayList<ReadSmartWatchExcelVO>();
 		 			while(rs.next()) {
 		 			ReadSmartWatchExcelVO readSmartWatchExcelVO = new ReadSmartWatchExcelVO();
 		 			//商品编号
 		 			String pid = rs.getString("pid");
 		 			readSmartWatchExcelVO.setPid(pid);
 		 			//商品标题
 		 			String title = rs.getString("enname");
 		 			readSmartWatchExcelVO.setTitle(title);
 		 			//商品关键词
 		 			String keyword = rs.getString("keyword");
 		 			if(StringUtils.isBlank(keyword)){
 		 				readSmartWatchExcelVO.setKeyword(title);
 		 			}else{
 	 		 			readSmartWatchExcelVO.setKeyword(keyword);
 		 			}	
 		 			
 		 			//获取价格
 		 			String price = rs.getString("price");
 		 			readSmartWatchExcelVO.setPrice(price);
 		 			//整个详情页
 		 			String content = rs.getString("endetail");
 		 			//类别
 		 			String type = rs.getString("type");
 		 			
 		 			if(StringUtils.isNotBlank(content)){	 				
 		 				content=content.replaceAll("<.*?>","");
 		 				readSmartWatchExcelVO.setSystem(content);	
 	 			   }
 		 			
 		 			//获取品牌
 		 			//获取标题第一个串为品牌
// 			 		if(StringUtils.isNotBlank(title)){
// 			 			String brand = "";
// 			 			String[] split = title.split(" ");
// 			 			if(split != null && split.length != 0){
// 			 				brand = split[0];
// 			 				readSmartWatchExcelVO.setBrand(brand);
// 			 			}
// 			 		}
 		 			
 			 		//图片路径
 			 		String path = rs.getString("remotpath");			 			
 		 			
 		 			//获取其他图片
 		 			String imgs = rs.getString("img");
 		 			if(StringUtils.isNotBlank(imgs)){
 		 				 imgs = imgs.replace("[", "").replace("]", "");		 				 
 		 				 String[] split = imgs.split(",");
                         for (String string : split) {
                        	 HttpClientDownloadUtil.getImgs(path + string);
						}		 				 
 	                    readSmartWatchExcelVO.setOtherPic(split); 
 		 			} 		 					 			
 			 		readSmartWatchExcelVO.setType(type);
 			 					 		
		 		
 		 			//商品主图
 		 			try {
 						String mainPic = rs.getString("custom_main_image");
 						String pic = HttpClientDownloadUtil.getImgs(path + mainPic);
 						readSmartWatchExcelVO.setPic(pic);
 					} catch (Exception e) {
 					   continue;
 					}
 	                list.add(readSmartWatchExcelVO);
 	 		        }
 	 	
 		 		return list;	
 		 			

 	 		} catch (SQLException e) {
 	 			e.printStackTrace();
 	 			return null;
 	 		} catch (Exception e) {
 				e.printStackTrace();
 				return null;
 			}	finally {
 	 			DbPoolUtil.returnConnection(conn);
 	 		}
 	 	}
 	
 	
 	
 	
 	
 	
 	
 	
 	
 	
	
	public static void main(String[] args) throws InvalidFormatException,
			Exception {

//		ReadAlibabaExcelUtils excelReader = new ReadAlibabaExcelUtils("D:\\台州轩祥报价单SHS19436.xlsx");

		try {
			List<ReadSmartWatchExcelVO> pets = getPets();
			ReadAlibabaExcelUtils excelUtils = new ReadAlibabaExcelUtils("G:\\apache-tomcat-7.0.57\\wtpwebapps\\quotation\\pet\\pet.xlsx");
			excelUtils.readPetExcelContent(pets);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
