package com.cbt.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import com.cbt.entity.PicGps;
import com.cbt.entity.QuotationBean;
import com.cbt.entity.QuotationProcessInfo;
import com.cbt.entity.QuotationProductionBean;
import com.cbt.enums.QuotePdfTypeEnum;
import com.cbt.print.QuotationNormalPrintTemplate;

public class PicturePdfUtil {
	
	
	private final static int PROCESS = 0;
	private final static int WEIGHT = 1;
	
//	private static String openOfficePath = "C:\\Program Files (x86)\\OpenOffice 4";
	private static String openOfficePath = "/opt/openoffice4";
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
	
	
	public static int office2PDF(String sourceFile, String destFile)
			throws Exception {
		try {
			File inputFile = new File(sourceFile);
			if (!inputFile.exists()) {
				return -1;// 找不到源文件, 则返回-1
			}

			// 如果目标路径不存在, 则新建该路径
			File outputFile = new File(destFile);
			if (!outputFile.getParentFile().exists()) {
				outputFile.getParentFile().mkdirs();
			}

			String OpenOffice_HOME = openOfficePath;// 这里是OpenOffice的安装目录
			// 如果从文件中读取的URL地址最后一个字符不是 '\'，则添加'\'
			if (OpenOffice_HOME.charAt(OpenOffice_HOME.length() - 1) != '/') {
				OpenOffice_HOME += "/";
			}

			// // 启动OpenOffice的服务
			 String command = OpenOffice_HOME
			 +
			 "program/soffice -headless -accept=\"socket,host=67.198.209.91,port=8100;urp;\" -nofirststartwizard";
			 Process pro = Runtime.getRuntime().exec(command);
			 // connect to an OpenOffice.org instance running on port 8100
			 OpenOfficeConnection connection = new SocketOpenOfficeConnection("67.198.209.91", 8100);

			// // 启动OpenOffice的服务 (window启动)
//			String command = OpenOffice_HOME
//					+ "program\\soffice -headless -accept=\"socket,host=192.168.1.151,port=8100;urp;\" -nofirststartwizard";
//			Process pro = Runtime.getRuntime().exec(command);
//			// connect to an OpenOffice.org instance running on port 8100
//			OpenOfficeConnection connection = new SocketOpenOfficeConnection(
//					"192.168.1.151", 8100);
			connection.connect();

			// convert
			DocumentConverter converter = new OpenOfficeDocumentConverter(
					connection);
			converter.convert(inputFile, outputFile);
			// close the connection
			connection.disconnect();
			// 关闭OpenOffice服务的进程
			pro.destroy();
			return 0;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return -1;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 1;
	}


	
	
	
	
	
	
	/**
	 * 添加产品数据
	 * @Title addProduct 
	 * @Description TODO
	 * @param sheet
	 * @param rowNo
	 * @param quotationBean
	 * @param list
	 * @param currency
	 * @param product_tl
	 * @param nRow
	 * @param nCell
	 * @param wb
	 * @throws Exception
	 * @return void
	 */
	public static void addProduct(HSSFSheet sheet,int rowNo,QuotationBean quotationBean,
			List<QuotationProductionBean> list,String currency,int product_tl,Row nRow,Cell nCell,HSSFWorkbook wb) throws Exception{   
		
	
		//判断当前选择工艺还是重量生成文件
		Integer processStatus = quotationBean.getProcessStatus();
		Integer weightStatus = quotationBean.getWeightStatus();
		if(processStatus == 0 && weightStatus == 0){
			addProductNoWeightProcess(sheet, rowNo, quotationBean, list, currency, product_tl, nRow, nCell, wb);
		}else if(processStatus == 0 && weightStatus == 1){
			addProductNoWeightOrNoProcess(sheet, rowNo, quotationBean,WEIGHT, list, currency, product_tl, nRow, nCell, wb);
		}else if(processStatus == 1 && weightStatus == 0){
			addProductNoWeightOrNoProcess(sheet, rowNo, quotationBean,PROCESS, list, currency, product_tl, nRow, nCell, wb);
		}else{
			addProductAll(sheet, rowNo, quotationBean, list, currency, product_tl, nRow, nCell, wb);
		}
		
		
	}
	
	
	/**
	 * 工艺和重量都不显示的时候，填入数据
	 * @Title addProductNoWeightProcess 
	 * @Description 
	 * @param sheet
	 * @param rowNo
	 * @param list
	 * @param currency
	 * @param product_tl
	 * @param nRow
	 * @param nCell
	 * @param wb
	 * @throws Exception
	 * @return void
	 */
	private static void addProductNoWeightProcess(HSSFSheet sheet,int rowNo,QuotationBean quotationBean,List<QuotationProductionBean> list,String currency,
			                                           int product_tl,Row nRow,Cell nCell,HSSFWorkbook wb) throws Exception{ 
		if(product_tl == 1){
			nRow = sheet.getRow(rowNo++);	
			nCell = nRow.getCell(0);
			nCell.setCellValue(list.get(0).getProductName());

			if(StringUtils.isNotBlank(list.get(0).getProductImgCompress())){
				nCell = nRow.getCell(1);
//				PicGps pic = new PicGps(rowNo-1, 1, rowNo, 2, 50, 20,0, 0); 
				PicGps pic = new PicGps(rowNo-1, 1 , rowNo-1, 1, 20, 20, 900, 235); 
				setPictures(UploadAndDownloadPathUtil.getQuotationPath() + "img" + File.separator + list.get(0).getProductImgCompress(), sheet,pic);			
			}
			
			
			nCell = nRow.getCell(2);
			nCell.setCellValue(currency+list.get(0).getMoldPrice());
			nCell = nRow.getCell(4);
			nCell.setCellValue(list.get(0).getQuotationProductionPriceBeanList().get(0).getQuantity().toString());
			nCell = nRow.getCell(5);
			nCell.setCellValue(currency+list.get(0).getQuotationProductionPriceBeanList().get(0).getProductPrice());
			nCell = nRow.getCell(6);
			nCell.setCellValue(list.get(0).getProductNotes());
		}else{
			for(int k=0;k<product_tl-1;k++){			   	
			   QuotationNormalPrintTemplate.insertRow(wb, sheet, rowNo , 1);				
			}
			int rowNo1 = rowNo;
			int production_tl = list.size();
			for (int i = 0;i < production_tl; i++) {	
				int price_tl = 0;
				nRow = sheet.getRow(rowNo);
				price_tl = list.get(i).getQuotationProductionPriceBeanList().size();

				nCell = nRow.getCell(0);
				nCell.setCellValue(list.get(i).getProductName());
				sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo+price_tl-1,(short)0,(short)0));
				
				if(StringUtils.isNotBlank(list.get(i).getProductImgCompress())){
					nCell = nRow.getCell(1);
//					PicGps pic = new PicGps(rowNo, 1, rowNo+price_tl, 2, 50, 20,0, 0); 
					PicGps pic = new PicGps(rowNo1, 1 , rowNo1, 1, 20, 20, 900, 235); 
					setPictures(UploadAndDownloadPathUtil.getQuotationPath() + "img" + File.separator + list.get(i).getProductImgCompress(), sheet,pic);
					sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo+price_tl-1,(short)1,(short)1));
				}

				

				nCell = nRow.getCell(2);
				nCell.setCellValue(currency+list.get(i).getMoldPrice());
				sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo+price_tl-1,(short)2,(short)3));
				rowNo = rowNo + price_tl;
				for(int j = 0;j < price_tl; j++){					
					nRow = sheet.getRow(rowNo1+j);
					nCell = nRow.getCell(4);
					nCell.setCellValue(list.get(i).getQuotationProductionPriceBeanList().get(j).getQuantity().toString());
					
					nCell = nRow.getCell(5);
					nCell.setCellValue(currency+list.get(i).getQuotationProductionPriceBeanList().get(j).getProductPrice());
										
				}
				nCell = nRow.getCell(6);
				nCell.setCellValue(list.get(i).getProductNotes());
				sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo+price_tl-1,(short)6,(short)7));
				rowNo1 = rowNo1 + price_tl;
			}

		}
	
	}
	
	
	
	
	
	/**
	 * 当存在工艺或者重量其一
	 * @Title addProductNoWeight 
	 * @Description
	 * @param sheet
	 * @param rowNo
	 * @param quotationBean
	 * @param list
	 * @param currency
	 * @param product_tl
	 * @param nRow
	 * @param nCell
	 * @param wb
	 * @throws Exception
	 * @return void
	 */
	private static void addProductNoWeightOrNoProcess(HSSFSheet sheet,int rowNo,QuotationBean quotationBean,int status,
			List<QuotationProductionBean> list,String currency,int product_tl,Row nRow,Cell nCell,HSSFWorkbook wb) throws Exception{   
		
	    //判断是工艺还是重量
	    if(status == PROCESS){
	    	nRow = sheet.getRow(rowNo-1); 	
			nCell = nRow.getCell(2);
			nCell.setCellValue("Process");
	    }
	    if(status == WEIGHT){
	    	nRow = sheet.getRow(rowNo-1); 	
	    	nCell = nRow.getCell(2);
	    	nCell.setCellValue("Weight"+"("+list.get(0).getQuotationProductionPriceBeanList().get(0).getWeightUnit()+")");
	    }
		
		
		if(product_tl == 1){
			nRow = sheet.getRow(rowNo++);	
			nCell = nRow.getCell(0);
			nCell.setCellValue(list.get(0).getProductName());
			
			if(StringUtils.isNotBlank(list.get(0).getProductImgCompress())){
				nCell = nRow.getCell(1);
//				PicGps pic = new PicGps(rowNo-1, 1, rowNo, 2, 50, 20,0, 0); 
				PicGps pic = new PicGps(rowNo-1, 1 , rowNo-1, 1, 20, 20, 900, 235); 
				setPictures(UploadAndDownloadPathUtil.getQuotationPath() + "img" + File.separator + list.get(0).getProductImgCompress(), sheet,pic);
			}

			if(status == PROCESS){
					List<QuotationProcessInfo> processes = list.get(0).getProcessInfos();
					nCell = nRow.getCell(2);
					if(processes != null && processes.size() != 0){	
						String processList = "";
						for (int i = 0;i < processes.size(); i++) {						
							if(i==0){
								//自动换行
								processList = processes.get(i).getProcessName();							
							}else{
								processList +=","+processes.get(i).getProcessName();
							}
						}
						nCell.setCellValue(processList);
					}
			}
			
			
			if(status == WEIGHT){
					nCell = nRow.getCell(2);
					nCell.setCellValue(list.get(0).getQuotationProductionPriceBeanList().get(0).getMaterialWeight());
					if(StringUtils.isNotBlank(list.get(0).getQuotationProductionPriceBeanList().get(0).getWeightUnit())){
						
						if("g".equals(list.get(0).getQuotationProductionPriceBeanList().get(0).getWeightUnit())){
							nCell.setCellValue(list.get(0).getQuotationProductionPriceBeanList().get(0).getMaterialWeight());
						}
						if("kg".equals(list.get(0).getQuotationProductionPriceBeanList().get(0).getWeightUnit())){
							nCell.setCellValue(list.get(0).getQuotationProductionPriceBeanList().get(0).getMaterialWeight());
						}
				    }
			}	
			nCell = nRow.getCell(4);
			nCell.setCellValue(currency+list.get(0).getMoldPrice());
			nCell = nRow.getCell(5);
			nCell.setCellValue(list.get(0).getQuotationProductionPriceBeanList().get(0).getQuantity().toString());
			nCell = nRow.getCell(6);
			nCell.setCellValue(currency+list.get(0).getQuotationProductionPriceBeanList().get(0).getProductPrice());
			nCell = nRow.getCell(7);
			nCell.setCellValue(list.get(0).getProductNotes());
		}else{
			for(int k=0;k<product_tl-1;k++){
			QuotationNormalPrintTemplate.insertRow(wb, sheet, rowNo , 1);				
			}
			int rowNo1 = rowNo;
			int production_tl = list.size();
			for (int i = 0;i < production_tl; i++) {	
				int price_tl = 0;
				nRow = sheet.getRow(rowNo);
				price_tl = list.get(i).getQuotationProductionPriceBeanList().size();

				nCell = nRow.getCell(0);
				nCell.setCellValue(list.get(i).getProductName());
				sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo+price_tl-1,(short)0,(short)0));

				
				if(StringUtils.isNotBlank(list.get(i).getProductImgCompress())){
					nCell = nRow.getCell(1);
//					PicGps pic = new PicGps(rowNo, 1, rowNo+price_tl, 2, 50, 20,0, 0); 
					PicGps pic = new PicGps(rowNo1, 1 , rowNo1, 1, 20, 20, 900, 235); 
					setPictures(UploadAndDownloadPathUtil.getQuotationPath() + "img" + File.separator + list.get(i).getProductImgCompress(), sheet,pic);
					sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo+price_tl-1,(short)1,(short)1));
				}
				
				if(status == PROCESS){
						List<QuotationProcessInfo> processes = list.get(i).getProcessInfos();
						if(processes != null && processes.size() != 0){
							nCell = nRow.getCell(2);	
							String processList = "";
							for (int j = 0;j < processes.size(); j++) {						
								if(j==0){
									processList = processes.get(j).getProcessName();							
								}else{
									processList +=","+processes.get(j).getProcessName();
								}
							}
							nCell.setCellValue(processList);
							sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo+price_tl-1,(short)2,(short)3));
						}
				}
						
				
				if(status == WEIGHT){
						nCell = nRow.getCell(2);
						nCell.setCellValue(list.get(i).getQuotationProductionPriceBeanList().get(0).getMaterialWeight());
						if(StringUtils.isNotBlank(list.get(i).getQuotationProductionPriceBeanList().get(0).getWeightUnit())){
							
							if("g".equals(list.get(i).getQuotationProductionPriceBeanList().get(0).getWeightUnit())){
								nCell.setCellValue(list.get(i).getQuotationProductionPriceBeanList().get(0).getMaterialWeight());
							}
							if("kg".equals(list.get(i).getQuotationProductionPriceBeanList().get(0).getWeightUnit())){
								nCell.setCellValue(list.get(i).getQuotationProductionPriceBeanList().get(0).getMaterialWeight());
							}
						}
				}	
				
				
				sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo+price_tl-1,(short)3,(short)3));
				nCell = nRow.getCell(4);
				nCell.setCellValue(currency+list.get(i).getMoldPrice());
				sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo+price_tl-1,(short)4,(short)4));
				rowNo = rowNo + price_tl;
				for(int j = 0;j < price_tl; j++){					
					nRow = sheet.getRow(rowNo1+j);
					nCell = nRow.getCell(5);
					nCell.setCellValue(list.get(i).getQuotationProductionPriceBeanList().get(j).getQuantity().toString());
					
					nCell = nRow.getCell(6);
					nCell.setCellValue(currency+list.get(i).getQuotationProductionPriceBeanList().get(j).getProductPrice());
										
				}
				nCell = nRow.getCell(7);
				nCell.setCellValue(list.get(i).getProductNotes());
				sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo+price_tl-1,(short)7,(short)7));
				rowNo1 = rowNo1 + price_tl;
			}

		}
	}
	
	
	
	/**
	 * 既选择工艺也选择了重量
	 * @Title addProductNoWeight 
	 * @Description
	 * @param sheet
	 * @param rowNo
	 * @param quotationBean
	 * @param list
	 * @param currency
	 * @param product_tl
	 * @param nRow
	 * @param nCell
	 * @param wb
	 * @throws Exception
	 * @return void
	 */
	private static void addProductAll(HSSFSheet sheet,int rowNo,QuotationBean quotationBean,
			List<QuotationProductionBean> list,String currency,int product_tl,Row nRow,Cell nCell,HSSFWorkbook wb) throws Exception{   
		

	    	nRow = sheet.getRow(rowNo-1); 	
	    	nCell = nRow.getCell(3);
	    	nCell.setCellValue("Weight"+"("+list.get(0).getQuotationProductionPriceBeanList().get(0).getWeightUnit()+")");

		
		if(product_tl == 1){
			nRow = sheet.getRow(rowNo++);	
			nCell = nRow.getCell(0);
			nCell.setCellValue(list.get(0).getProductName());
			
			if(StringUtils.isNotBlank(list.get(0).getProductImgCompress())){
				nCell = nRow.getCell(1);
				PicGps pic = new PicGps(rowNo-1, 1 , rowNo-1, 1, 20, 20, 900, 235); 
				setPictures(UploadAndDownloadPathUtil.getQuotationPath() + "img" + File.separator + list.get(0).getProductImgCompress(), sheet,pic);
			}
			
			
			List<QuotationProcessInfo> processes = list.get(0).getProcessInfos();
			nCell = nRow.getCell(2);
			if(processes != null && processes.size() != 0){	
				String processList = "";
				for (int i = 0;i < processes.size(); i++) {						
					if(i==0){
						//自动换行
						processList = processes.get(i).getProcessName();							
					}else{
						processList +=","+processes.get(i).getProcessName();
					}
				}
				nCell.setCellValue(processList);
			}
			
			
			nCell = nRow.getCell(3);
			nCell.setCellValue(list.get(0).getQuotationProductionPriceBeanList().get(0).getMaterialWeight());
			if(StringUtils.isNotBlank(list.get(0).getQuotationProductionPriceBeanList().get(0).getWeightUnit())){
				
				if("g".equals(list.get(0).getQuotationProductionPriceBeanList().get(0).getWeightUnit())){
					nCell.setCellValue(list.get(0).getQuotationProductionPriceBeanList().get(0).getMaterialWeight());
				}
				if("kg".equals(list.get(0).getQuotationProductionPriceBeanList().get(0).getWeightUnit())){
					nCell.setCellValue(list.get(0).getQuotationProductionPriceBeanList().get(0).getMaterialWeight());
				}
		    }
	
			nCell = nRow.getCell(4);
			nCell.setCellValue(currency+list.get(0).getMoldPrice());
			nCell = nRow.getCell(5);
			nCell.setCellValue(list.get(0).getQuotationProductionPriceBeanList().get(0).getQuantity().toString());
			nCell = nRow.getCell(6);
			nCell.setCellValue(currency+list.get(0).getQuotationProductionPriceBeanList().get(0).getProductPrice());
			nCell = nRow.getCell(7);
			nCell.setCellValue(list.get(0).getProductNotes());
		}else{
			for(int k=0;k<product_tl-1;k++){
			QuotationNormalPrintTemplate.insertRow(wb, sheet, rowNo, 1);				
			}
			int rowNo1 = rowNo;
			int production_tl = list.size();
			for (int i = 0;i < production_tl; i++) {	
				int price_tl = 0;
				nRow = sheet.getRow(rowNo);
				price_tl = list.get(i).getQuotationProductionPriceBeanList().size();

				nCell = nRow.getCell(0);
				nCell.setCellValue(list.get(i).getProductName());
				sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo+price_tl-1,(short)0,(short)0));

				if(StringUtils.isNotBlank(list.get(i).getProductImgCompress())){
					nCell = nRow.getCell(1);
					PicGps pic = new PicGps(rowNo1, 1 , rowNo1, 1, 20, 20, 900, 235); 
					setPictures(UploadAndDownloadPathUtil.getQuotationPath() + "img" + File.separator + list.get(i).getProductImgCompress(), sheet,pic);
					sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo+price_tl-1,(short)1,(short)1));
				}
				
				
				List<QuotationProcessInfo> processes = list.get(i).getProcessInfos();
				if(processes != null && processes.size() != 0){
					nCell = nRow.getCell(2);	
					String processList = "";
					for (int j = 0;j < processes.size(); j++) {						
						if(j==0){
							processList = processes.get(j).getProcessName();							
						}else{
							processList +=","+processes.get(j).getProcessName();
						}
					}
					nCell.setCellValue(processList);
					sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo+price_tl-1,(short)2,(short)2));
				}
				
				nCell = nRow.getCell(3);
				nCell.setCellValue(list.get(i).getQuotationProductionPriceBeanList().get(0).getMaterialWeight());
				if(StringUtils.isNotBlank(list.get(i).getQuotationProductionPriceBeanList().get(0).getWeightUnit())){
					
					if("g".equals(list.get(i).getQuotationProductionPriceBeanList().get(0).getWeightUnit())){
						nCell.setCellValue(list.get(i).getQuotationProductionPriceBeanList().get(0).getMaterialWeight());
					}
					if("kg".equals(list.get(i).getQuotationProductionPriceBeanList().get(0).getWeightUnit())){
						nCell.setCellValue(list.get(i).getQuotationProductionPriceBeanList().get(0).getMaterialWeight());
					}
				}
				sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo+price_tl-1,(short)3,(short)3));
				nCell = nRow.getCell(4);
				nCell.setCellValue(currency+list.get(i).getMoldPrice());
				sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo+price_tl-1,(short)4,(short)4));
				rowNo = rowNo + price_tl;
				for(int j = 0;j < price_tl; j++){					
					nRow = sheet.getRow(rowNo1+j);
					nCell = nRow.getCell(5);
					nCell.setCellValue(list.get(i).getQuotationProductionPriceBeanList().get(j).getQuantity().toString());
					
					nCell = nRow.getCell(6);
					nCell.setCellValue(currency+list.get(i).getQuotationProductionPriceBeanList().get(j).getProductPrice());
										
				}
				nCell = nRow.getCell(7);
				nCell.setCellValue(list.get(i).getProductNotes());
				sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo+price_tl-1,(short)7,(short)7));
				rowNo1 = rowNo1 + price_tl;
			}

		}
	}


	
	
	
	
	
}
