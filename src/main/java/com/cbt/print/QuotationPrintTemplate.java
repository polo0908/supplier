package com.cbt.print;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFSimpleShape;
import org.apache.poi.hssf.usermodel.HSSFTextbox;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import com.cbt.entity.ClientOrder;
import com.cbt.entity.QuotationBean;
import com.cbt.entity.QuotationProductionBean;
import com.cbt.entity.QuotationProductionPriceBean;
import com.cbt.util.DownloadUtil;
import com.cbt.util.OperationFileUtil;
import com.cbt.util.UploadAndDownloadPathUtil;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.artofsolving.jodconverter.DocumentConverter;  
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;  
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;  
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;  



/**
 * @Description: 根据模板文件打印，输出发票pdf
 * @Author:	polo
 * @CreateDate:	2017/4/5
 */

public class QuotationPrintTemplate {
	
    
    
	private static File tempPath;	
	
	public static void print(HttpServletRequest request,ClientOrder clientOrder,String path,String invoiceId,HttpServletResponse response) throws IOException, DocumentException {  
	
//	String fileName = path + "\\China Synergy Group invoice.pdf";// pdf模板  
	
	 String fileName = UploadAndDownloadPathUtil.getClientOrderUpLoadAndDownLoadPath() + clientOrder.getId().toString()+"&"+clientOrder.getOrderId() + File.separator +invoiceId+ ".pdf";	
     PdfReader reader = new PdfReader(fileName);  
     ByteArrayOutputStream bos = new ByteArrayOutputStream();  
     /* 将要生成的目标PDF文件名称 */   
     PdfStamper ps = new PdfStamper(reader, bos);       
       
       /* 使用中文字体 */    
//     BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);  
//     ArrayList<BaseFont> fontList = new ArrayList<BaseFont>();  
//     fontList.add(bf);  
      
     /* 取出报表模板中的所有字段 */    
     AcroFields fields = ps.getAcroFields();  
//     fields.setSubstitutionFonts(fontList);  
//     fillData(fields, data());  
       
     /* 必须要调用这个，否则文档不会生成的 */    
     ps.setFormFlattening(true);  
     ps.close();       
     bos.close(); 
     DownloadUtil du = new DownloadUtil();
     du.download(bos, response, "invoice.pdf");
     
 }  
	
	
//	public static void print(HttpServletRequest request,ClientOrder clientOrder,String path,String invoiceId,HttpServletResponse response) throws IOException, DocumentException {  
		
//	String fileName = path + "\\China Synergy Group invoice.pdf";// pdf模板  
		
//		String fileName = UploadAndDownloadPathUtil.getClientOrderUpLoadAndDownLoadPath() + clientOrder.getId().toString()+"&"+clientOrder.getOrderId() + File.separator +invoiceId+ ".xls";	
//	   
//		OperationFileUtil.download(fileName);
//		
//	}  
	
	
	/**
	 * pdf打印,使用excel编辑，生成pdf
	 * @param path
	 * @throws Exception
	 */
	public static String printExcel(HttpServletRequest request,String path,QuotationBean quotationBean,List<QuotationProductionBean> list) throws Exception{
		
		int tl = 0;
        if(list.size() != 0){
        	for (QuotationProductionBean quotationProductionBean : list) {
        		List<QuotationProductionPriceBean> priceBeanLists = quotationProductionBean.getQuotationProductionPriceBeanList();
        		int pr_tl = priceBeanLists.size();
        		tl +=pr_tl;
			}
        }
		/*
		 * 打开模板，复制sheet，另存
		 */
       File file = new File(path+"pdf"+File.separator+"quotation.xls");
	
			
		FileInputStream fi = new FileInputStream(file);
		HSSFWorkbook wb = new HSSFWorkbook(fi);
			wb.cloneSheet(0);						            //复制工作簿
			wb.setSheetName(1, "quotation for product");		//设置工作簿名称
		
			//设置相同内容
			int rowNo = 1;
			int colNo = 0;
			Row nRow = null;
			Cell nCell = null;
			
			HSSFSheet sheet = wb.getSheetAt(1);						//定位到当前工作表
			sheet.setForceFormulaRecalculation(true);				//强制公式自动计算，利用模板时，模板中的公式不会因值发生变化而自动计算。
			
			nRow = sheet.getRow(rowNo++);
			nCell = nRow.getCell(4);
//			sheet.addMergedRegion(new CellRangeAddress(0, 2,(short)3,(short)5));
			nCell.setCellValue("Quotaion:"+quotationBean.getProjectId());

			nCell = nRow.getCell(7);
			nCell.setCellValue("Contact:"+quotationBean.getSaleName());
						
			nRow = sheet.getRow(rowNo++);
			nCell = nRow.getCell(7);
			nCell.setCellValue(quotationBean.getQuoterEmail());
			nRow = sheet.getRow(rowNo++);
			nCell = nRow.getCell(7);
			nCell.setCellValue("TEL:"+quotationBean.getQuoterTel());

			nRow = sheet.getRow(rowNo++);
			nCell = nRow.getCell(2);
			nCell.setCellValue(quotationBean.getCustomerName());
			nCell = nRow.getCell(5);
			nCell.setCellValue(quotationBean.getQuotationDate());
			nCell = nRow.getCell(7);
			nCell.setCellValue("valid for" +quotationBean.getQuotationValidity()+ "days");
			
			rowNo = 8;
			//当只有一个产品时，处理列表显示
			if(tl == 1){
				nRow = sheet.getRow(rowNo++);	
				nCell = nRow.getCell(0);
				nCell.setCellValue(1);
				nCell = nRow.getCell(1);
				nCell.setCellValue(list.get(0).getProductName());
				nCell = nRow.getCell(2);
				nCell.setCellValue(list.get(0).getProductNotes());
				nCell = nRow.getCell(3);
				nCell.setCellValue(list.get(0).getMoldPrice());
				nCell = nRow.getCell(4);
				nCell.setCellValue(list.get(0).getQuotationProductionPriceBeanList().get(0).getQuantity());
				nCell = nRow.getCell(5);
				nCell.setCellValue(list.get(0).getQuotationProductionPriceBeanList().get(0).getProductPrice());
				nCell = nRow.getCell(6);
			Double price = 	new BigDecimal(list.get(0).getQuotationProductionPriceBeanList().get(0).getQuantity()).multiply(new BigDecimal(list.get(0).getQuotationProductionPriceBeanList().get(0).getProductPrice())).add(new BigDecimal(list.get(0).getMoldPrice())).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
				nCell.setCellValue(price);
				
			}else{
				for(int k=0;k<tl-1;k++){
				insertRow(wb, sheet, 8, 1);
				}
				int rowNo1 = 8;
				int production_tl = list.size();
				for (int i = 0;i < production_tl; i++) {	
					int price_tl = 0;
					nRow = sheet.getRow(rowNo);
					price_tl = list.get(i).getQuotationProductionPriceBeanList().size();
					nCell = nRow.getCell(0);
					nCell.setCellValue(1);
					sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo+price_tl-1,(short)0,(short)0));
					nCell = nRow.getCell(1);
					nCell.setCellValue(list.get(i).getProductName());
					sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo+price_tl-1,(short)1,(short)1));
					nCell = nRow.getCell(2);
					nCell.setCellValue(list.get(i).getProductNotes());
					sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo+price_tl-1,(short)2,(short)2));
					nCell = nRow.getCell(3);
					nCell.setCellValue(list.get(i).getMoldPrice());
					sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo+price_tl-1,(short)3,(short)3));
					rowNo = rowNo + price_tl;
					for(int j = 0;j < price_tl; j++){					
						nRow = sheet.getRow(rowNo1+j);
						nCell = nRow.getCell(4);
						nCell.setCellValue(list.get(i).getQuotationProductionPriceBeanList().get(j).getQuantity());
						
						nCell = nRow.getCell(5);
						nCell.setCellValue(list.get(i).getQuotationProductionPriceBeanList().get(j).getProductPrice());

						nCell = nRow.getCell(6);
						Double price = 	new BigDecimal(list.get(i).getQuotationProductionPriceBeanList().get(j).getQuantity()).multiply(new BigDecimal(list.get(i).getQuotationProductionPriceBeanList().get(j).getProductPrice())).add(new BigDecimal(list.get(i).getMoldPrice())).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
						nCell.setCellValue(price);	
						sheet.addMergedRegion(new CellRangeAddress(rowNo1+j, rowNo1+j,(short)6,(short)7));						
					}
					rowNo1 = rowNo1 + price_tl;
				}
							
			}
			
			            // default
						//声明一个画图的顶级管理器
				        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
				        /**
						 * dx1：起始单元格的x偏移量，如例子中的255表示直线起始位置距A1单元格左侧的距离；
						 * dy1：起始单元格的y偏移量，如例子中的125表示直线起始位置距A1单元格上侧的距离；
						 * dx2：终止单元格的x偏移量，如例子中的1023表示直线起始位置距C3单元格左侧的距离；
						 * dy2：终止单元格的y偏移量，如例子中的150表示直线起始位置距C3单元格上侧的距离；
						 * colFrom：起始单元格列序号，从0开始计算；
						 * rowFrom：起始单元格行序号，从0开始计算，如例子中col1=0,row1=0就表示起始单元格为A1；
						 * colTo：终止单元格列序号，从0开始计算；
						 * rowTo：终止单元格行序号，从0开始计算，如例子中col2=2,row2=2就表示起始单元格为C3；
						 */
						
						int dx1 = 50, dy1 = 0, dx2 = 0, dy2 = 0;
						int colFrom = 0, rowFrom = 11+tl, colTo = 9, rowTo = 16+tl;
			
						//新建text文本框
						HSSFClientAnchor bigValueAnchorTextBox = new HSSFClientAnchor(dx1, dy1, dx2, dy2, (short)(colFrom), rowFrom+1, (short)(colTo-1), rowTo);
						HSSFTextbox bigValueTextbox = patriarch.createTextbox(bigValueAnchorTextBox);
						
						bigValueTextbox.setString(new HSSFRichTextString(quotationBean.getRemark()));
						bigValueTextbox.setLineStyle(HSSFSimpleShape.LINESTYLE_SOLID);	
//						bigValueTextbox2.set
						
			
                        rowNo = 8+tl;         
			       	    ServletContext sctx = request.getSession().getServletContext();				
					    String pic1 = sctx.getRealPath("/pdf/9001.png");
					    setPicture(pic1, sheet, rowNo, 7, rowNo+3, 8);
    
					    
					    String imgNames = quotationBean.getImgNames();
					    int img_tl = 0;
					    String[] split = imgNames.split(",");
					    if(!(imgNames == null || "".equals(imgNames))){
						    img_tl = split.length;
					    }

					    
					    dx1 = 255; dy1 = 125; dx2 = 200; dy2 = 150;
					    //图片4个一行存取，最多8张图片，大于8张取前8张
                        if(img_tl > 0 && img_tl <= 4){
                        for (int i = 0; i < img_tl; i++) {	    			        
 					    String pic_path = "";
                        pic_path = UploadAndDownloadPathUtil.getRemakPicturePath() + quotationBean.getFactoryId() +File.separator+ OperationFileUtil.changeZipName(split[i]);	
					    rowFrom	= 17+tl;
					    rowTo = 24+tl;		    
					    if(i == 0){
					    	colFrom = 0;
						    colTo = 2;
					    }else if(i == 1){
					    	colFrom = 2;
						    colTo = 3;
					    }else if(i == 2){
					    	colFrom = 3;
						    colTo = 5;
					    }else if(i == 3){
					    	colFrom = 5;
						    colTo = 7;
					    }
					    setPicture1(pic_path, sheet,  rowFrom+1, colFrom, rowTo, colTo);
                       }	
				    }else if(img_tl > 4 && img_tl <= 8){
				    	for (int i = 0; i < img_tl; i++) {	
//	    			        ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();          	
//	                        BufferedImage bufferImg = null;   
	 					    String pic_path = "";
	                        pic_path = UploadAndDownloadPathUtil.getRemakPicturePath() + quotationBean.getFactoryId() +File.separator+ split[i];
//					        bufferImg = ImageIO.read(new File(pic_path));     
//					        ImageIO.write(bufferImg, "jpg", byteArrayOut); 
					       
					        if(i<4){
							    rowFrom	= 17+tl;
							    rowTo = 24+tl;	
					        }else{
							    rowFrom	= 24+tl;
							    rowTo = 31+tl;	
					        }
	    
						    if(i == 0 || i == 4){
						    	colFrom = 0;
							    colTo = 2;
						    }else if(i == 1 || i == 5){
						    	colFrom = 2;
							    colTo = 3;
						    }else if(i == 2 || i == 6){
						    	colFrom = 3;
							    colTo = 5;
						    }else if(i == 3 || i == 7){
						    	colFrom = 5;
							    colTo = 7;
						    }
//						    bigValueAnchorTextBox = new HSSFClientAnchor(dx1, dy1, dx2, dy2, (short)(colFrom), rowFrom+1, (short)(colTo), rowTo);
//						    bigValueAnchorTextBox.setAnchorType(3);						
//							patriarch.createPicture(bigValueAnchorTextBox, wb.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG)); 
//							byteArrayOut.close();
						    setPicture1(pic_path, sheet,  rowFrom+1, colFrom, rowTo, colTo);
				    	}	
				    }
					    
					    
					    
					    
			
		String paths = UploadAndDownloadPathUtil.getQuotationPath() + quotationBean.getFactoryId();	
		
		tempPath= new File(paths);
//		deleteFile(tempPath);
		 if(!tempPath.exists() || !tempPath.isDirectory())
	        {
	            tempPath.mkdir();  //如果不存在，则创建该文件夹
	        }
		 wb.removeSheetAt(0);	   //删除模板sheet
		 FileOutputStream fs = new FileOutputStream(paths+ File.separator +quotationBean.getProjectId()+".xls",false);
		 wb.write(fs);	
		 fs.close();		 
		 office2PDF(paths+ File.separator+quotationBean.getProjectId()+".xls", paths+File.separator+quotationBean.getProjectId()+ ".pdf");	 
		 
		 return paths+File.separator+quotationBean.getProjectId()+ ".pdf";	 

	}
	
	
	
	
	
	
	
	/**
	 * pdf打印,使用excel编辑，生成pdf
	 * @param path
	 * @throws Exception
	 */
	public static String printExcel1(HttpServletRequest request,String path,QuotationBean quotationBean,List<QuotationProductionBean> list) throws Exception{
		
		int tl = 0;
        if(list.size() != 0){
        	for (QuotationProductionBean quotationProductionBean : list) {
        		List<QuotationProductionPriceBean> priceBeanLists = quotationProductionBean.getQuotationProductionPriceBeanList();
        		int pr_tl = priceBeanLists.size();
        		tl +=pr_tl;
			}
        }
		/*
		 * 打开模板，复制sheet，另存
		 */
       File file =  new File(path+"pdf"+File.separator+"quotation1.xls");
 
			
		FileInputStream fi = new FileInputStream(file);
		HSSFWorkbook wb = new HSSFWorkbook(fi);
			wb.cloneSheet(0);						            //复制工作簿
			wb.setSheetName(1, "quotation for product");		//设置工作簿名称
		
			//设置相同内容
			int rowNo = 1;
			int colNo = 0;
			Row nRow = null;
			Cell nCell = null;
			
			HSSFSheet sheet = wb.getSheetAt(1);						//定位到当前工作表
			sheet.setForceFormulaRecalculation(true);				//强制公式自动计算，利用模板时，模板中的公式不会因值发生变化而自动计算。
			
			nRow = sheet.getRow(rowNo++);
			nCell = nRow.getCell(4);
//			sheet.addMergedRegion(new CellRangeAddress(0, 2,(short)3,(short)5));
			nCell.setCellValue("Quotaion:"+quotationBean.getProjectId());

			nCell = nRow.getCell(7);
			nCell.setCellValue("Contact:"+quotationBean.getSaleName());
						
			nRow = sheet.getRow(rowNo++);
			nCell = nRow.getCell(7);
			nCell.setCellValue(quotationBean.getQuoterEmail());
			nRow = sheet.getRow(rowNo++);
			nCell = nRow.getCell(7);
			nCell.setCellValue("TEL:"+quotationBean.getQuoterTel());

			nRow = sheet.getRow(rowNo++);
			nCell = nRow.getCell(2);
			nCell.setCellValue(quotationBean.getCustomerName());
			nCell = nRow.getCell(5);
			nCell.setCellValue(quotationBean.getQuotationDate());
			nCell = nRow.getCell(7);
			nCell.setCellValue("valid for" +quotationBean.getQuotationValidity()+ "days");
			
			rowNo = 8;
			//当只有一个产品时，处理列表显示
			if(tl == 1){
				nRow = sheet.getRow(rowNo++);	
				nCell = nRow.getCell(0);
				nCell.setCellValue(1);
				nCell = nRow.getCell(1);
				nCell.setCellValue(list.get(0).getProductName());
				nCell = nRow.getCell(2);
				nCell.setCellValue(list.get(0).getProductNotes());
				nCell = nRow.getCell(3);
				nCell.setCellValue(list.get(0).getMoldPrice());
				nCell = nRow.getCell(4);
				nCell.setCellValue(list.get(0).getQuotationProductionPriceBeanList().get(0).getMaterialWeight() * list.get(0).getQuotationProductionPriceBeanList().get(0).getQuantity());
				nCell = nRow.getCell(5);
				nCell.setCellValue(list.get(0).getQuotationProductionPriceBeanList().get(0).getQuantity());
				nCell = nRow.getCell(6);
				nCell.setCellValue(list.get(0).getQuotationProductionPriceBeanList().get(0).getProductPrice());
				nCell = nRow.getCell(7);
			Double price = 	new BigDecimal(list.get(0).getQuotationProductionPriceBeanList().get(0).getQuantity()).multiply(new BigDecimal(list.get(0).getQuotationProductionPriceBeanList().get(0).getProductPrice())).add(new BigDecimal(list.get(0).getMoldPrice())).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
				nCell.setCellValue(price);
				
			}else{
				for(int k=0;k<tl-1;k++){
				insertRow(wb, sheet, 8, 1);
				}
				int rowNo1 = 8;
				int production_tl = list.size();
				for (int i = 0;i < production_tl; i++) {	
					int price_tl = 0;
					nRow = sheet.getRow(rowNo);
					price_tl = list.get(i).getQuotationProductionPriceBeanList().size();
					nCell = nRow.getCell(0);
					nCell.setCellValue(1);
					sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo+price_tl-1,(short)0,(short)0));
					nCell = nRow.getCell(1);
					nCell.setCellValue(list.get(i).getProductName());
					sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo+price_tl-1,(short)1,(short)1));
					nCell = nRow.getCell(2);
					nCell.setCellValue(list.get(i).getProductNotes());
					sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo+price_tl-1,(short)2,(short)2));
					nCell = nRow.getCell(3);
					nCell.setCellValue(list.get(i).getMoldPrice());
					sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo+price_tl-1,(short)3,(short)3));
					rowNo = rowNo + price_tl;
					for(int j = 0;j < price_tl; j++){					
						nRow = sheet.getRow(rowNo1+j);
						
						nCell = nRow.getCell(4);
						nCell.setCellValue(list.get(i).getQuotationProductionPriceBeanList().get(j).getMaterialWeight() * list.get(i).getQuotationProductionPriceBeanList().get(j).getQuantity());
						
						nCell = nRow.getCell(5);
						nCell.setCellValue(list.get(i).getQuotationProductionPriceBeanList().get(j).getQuantity());
						
						nCell = nRow.getCell(6);
						nCell.setCellValue(list.get(i).getQuotationProductionPriceBeanList().get(j).getProductPrice());

						nCell = nRow.getCell(7);
						Double price = 	new BigDecimal(list.get(i).getQuotationProductionPriceBeanList().get(j).getQuantity()).multiply(new BigDecimal(list.get(i).getQuotationProductionPriceBeanList().get(j).getProductPrice())).add(new BigDecimal(list.get(i).getMoldPrice())).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
						nCell.setCellValue(price);	
						sheet.addMergedRegion(new CellRangeAddress(rowNo1+j, rowNo1+j,(short)7,(short)8));						
					}
					rowNo1 = rowNo1 + price_tl;
				}
							
			}
			
			            // default
						//声明一个画图的顶级管理器
				        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
				        /**
						 * dx1：起始单元格的x偏移量，如例子中的255表示直线起始位置距A1单元格左侧的距离；
						 * dy1：起始单元格的y偏移量，如例子中的125表示直线起始位置距A1单元格上侧的距离；
						 * dx2：终止单元格的x偏移量，如例子中的1023表示直线起始位置距C3单元格左侧的距离；
						 * dy2：终止单元格的y偏移量，如例子中的150表示直线起始位置距C3单元格上侧的距离；
						 * colFrom：起始单元格列序号，从0开始计算；
						 * rowFrom：起始单元格行序号，从0开始计算，如例子中col1=0,row1=0就表示起始单元格为A1；
						 * colTo：终止单元格列序号，从0开始计算；
						 * rowTo：终止单元格行序号，从0开始计算，如例子中col2=2,row2=2就表示起始单元格为C3；
						 */
						
						int dx1 = 50, dy1 = 0, dx2 = 0, dy2 = 0;
						int colFrom = 0, rowFrom = 11+tl, colTo = 9, rowTo = 16+tl;
			
						//新建text文本框
						HSSFClientAnchor bigValueAnchorTextBox = new HSSFClientAnchor(dx1, dy1, dx2, dy2, (short)(colFrom), rowFrom+1, (short)(colTo-1), rowTo);
						HSSFTextbox bigValueTextbox = patriarch.createTextbox(bigValueAnchorTextBox);
						
						bigValueTextbox.setString(new HSSFRichTextString(quotationBean.getRemark()));
						bigValueTextbox.setLineStyle(HSSFSimpleShape.LINESTYLE_SOLID);	
//						bigValueTextbox2.set
						
			
                        rowNo = 8+tl;         
			       	    ServletContext sctx = request.getSession().getServletContext();				
					    String pic1 = sctx.getRealPath("/pdf/9001.png");
					    setPicture(pic1, sheet, rowNo, 7, rowNo+3, 8);
    
					    
					    String imgNames = quotationBean.getImgNames();
					    int img_tl = 0;
					    String[] split = imgNames.split(",");
					    if(!(imgNames == null || "".equals(imgNames))){
						    img_tl = split.length;
					    }

					    
					    dx1 = 255; dy1 = 125; dx2 = 200; dy2 = 150;
					    //图片4个一行存取，最多8张图片，大于8张取前8张
                        if(img_tl > 0 && img_tl <= 4){
                        for (int i = 0; i < img_tl; i++) {	    			        
 					    String pic_path = "";
                        pic_path = UploadAndDownloadPathUtil.getRemakPicturePath() + quotationBean.getFactoryId() +File.separator+ OperationFileUtil.changeZipName(split[i]);	
					    rowFrom	= 17+tl;
					    rowTo = 24+tl;		    
					    if(i == 0){
					    	colFrom = 0;
						    colTo = 2;
					    }else if(i == 1){
					    	colFrom = 2;
						    colTo = 3;
					    }else if(i == 2){
					    	colFrom = 3;
						    colTo = 5;
					    }else if(i == 3){
					    	colFrom = 5;
						    colTo = 7;
					    }
					    setPicture1(pic_path, sheet,  rowFrom+1, colFrom, rowTo, colTo);
                       }	
				    }else if(img_tl > 4 && img_tl <= 8){
				    	for (int i = 0; i < img_tl; i++) {	
//	    			        ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();          	
//	                        BufferedImage bufferImg = null;   
	 					    String pic_path = "";
	                        pic_path = UploadAndDownloadPathUtil.getRemakPicturePath() + quotationBean.getFactoryId() +File.separator+ split[i];
//					        bufferImg = ImageIO.read(new File(pic_path));     
//					        ImageIO.write(bufferImg, "jpg", byteArrayOut); 
					       
					        if(i<4){
							    rowFrom	= 17+tl;
							    rowTo = 24+tl;	
					        }else{
							    rowFrom	= 24+tl;
							    rowTo = 31+tl;	
					        }
	    
						    if(i == 0 || i == 4){
						    	colFrom = 0;
							    colTo = 2;
						    }else if(i == 1 || i == 5){
						    	colFrom = 2;
							    colTo = 3;
						    }else if(i == 2 || i == 6){
						    	colFrom = 3;
							    colTo = 5;
						    }else if(i == 3 || i == 7){
						    	colFrom = 5;
							    colTo = 7;
						    }
//						    bigValueAnchorTextBox = new HSSFClientAnchor(dx1, dy1, dx2, dy2, (short)(colFrom), rowFrom+1, (short)(colTo), rowTo);
//						    bigValueAnchorTextBox.setAnchorType(3);						
//							patriarch.createPicture(bigValueAnchorTextBox, wb.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG)); 
//							byteArrayOut.close();
						    setPicture1(pic_path, sheet,  rowFrom+1, colFrom, rowTo, colTo);
				    	}	
				    }
					    
					    
					    
					    
			
		String paths = UploadAndDownloadPathUtil.getQuotationPath() + quotationBean.getFactoryId();	
		
		tempPath= new File(paths);
		 if(!tempPath.exists() || !tempPath.isDirectory())
	        {
	            tempPath.mkdir();  //如果不存在，则创建该文件夹
	        }
		 wb.removeSheetAt(0);	   //删除模板sheet
		 FileOutputStream fs = new FileOutputStream(paths+ File.separator +quotationBean.getProjectId()+".xls",false);
		 wb.write(fs);	
		 fs.close();		 
		 office2PDF(paths+ File.separator+quotationBean.getProjectId()+".xls", paths+File.separator+quotationBean.getProjectId()+ ".pdf");	 
		 
		 return paths+File.separator+quotationBean.getProjectId()+ ".pdf";	 

	}
	
	
	
	
	/**
	 * pdf打印,使用excel编辑，生成pdf
	 * @param path
	 * @throws Exception
	 */
	public static String printExcel2(HttpServletRequest request,String path,QuotationBean quotationBean,List<QuotationProductionBean> list) throws Exception{
		
		int tl = 0;
        if(list.size() != 0){
        	for (QuotationProductionBean quotationProductionBean : list) {
        		List<QuotationProductionPriceBean> priceBeanLists = quotationProductionBean.getQuotationProductionPriceBeanList();
        		int pr_tl = priceBeanLists.size();
        		tl +=pr_tl;
			}
        }
		/*
		 * 打开模板，复制sheet，另存
		 */
       File file =  new File(path+"pdf"+File.separator+"quotation2.xls");
		
		FileInputStream fi = new FileInputStream(file);
		HSSFWorkbook wb = new HSSFWorkbook(fi);
			wb.cloneSheet(0);						            //复制工作簿
			wb.setSheetName(1, "quotation for product");		//设置工作簿名称
		
			//设置相同内容
			int rowNo = 1;
			int colNo = 0;
			Row nRow = null;
			Cell nCell = null;
			
			HSSFSheet sheet = wb.getSheetAt(1);						//定位到当前工作表
			sheet.setForceFormulaRecalculation(true);				//强制公式自动计算，利用模板时，模板中的公式不会因值发生变化而自动计算。
			
			nRow = sheet.getRow(rowNo++);
			nCell = nRow.getCell(4);
//			sheet.addMergedRegion(new CellRangeAddress(0, 2,(short)3,(short)5));
			nCell.setCellValue("Quotaion:"+quotationBean.getProjectId());

			nCell = nRow.getCell(7);
			nCell.setCellValue("Contact:"+quotationBean.getSaleName());
						
			nRow = sheet.getRow(rowNo++);
			nCell = nRow.getCell(7);
			nCell.setCellValue(quotationBean.getQuoterEmail());
			nRow = sheet.getRow(rowNo++);
			nCell = nRow.getCell(7);
			nCell.setCellValue("TEL:"+quotationBean.getQuoterTel());

			nRow = sheet.getRow(rowNo++);
			nCell = nRow.getCell(2);
			nCell.setCellValue(quotationBean.getCustomerName());
			nCell = nRow.getCell(5);
			nCell.setCellValue(quotationBean.getQuotationDate());
			nCell = nRow.getCell(7);
			nCell.setCellValue("valid for" +quotationBean.getQuotationValidity()+ "days");
			
			rowNo = 8;
			//当只有一个产品时，处理列表显示
			if(tl == 1){
				nRow = sheet.getRow(rowNo++);	
				nCell = nRow.getCell(0);
				nCell.setCellValue(1);
				nCell = nRow.getCell(1);
				nCell.setCellValue(list.get(0).getProductName());
				nCell = nRow.getCell(2);
				nCell.setCellValue(list.get(0).getProductNotes());
				nCell = nRow.getCell(3);
				nCell.setCellValue(list.get(0).getMoldPrice());
				
						
				nCell = nRow.getCell(4);
				nCell.setCellValue(list.get(0).getProcessList());
				nCell = nRow.getCell(5);
				nCell.setCellValue(list.get(0).getQuotationProductionPriceBeanList().get(0).getQuantity());
				nCell = nRow.getCell(6);
				nCell.setCellValue(list.get(0).getQuotationProductionPriceBeanList().get(0).getProductPrice());
				nCell = nRow.getCell(7);
			Double price = 	new BigDecimal(list.get(0).getQuotationProductionPriceBeanList().get(0).getQuantity()).multiply(new BigDecimal(list.get(0).getQuotationProductionPriceBeanList().get(0).getProductPrice())).add(new BigDecimal(list.get(0).getMoldPrice())).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
				nCell.setCellValue(price);
				
			}else{
				for(int k=0;k<tl-1;k++){
				insertRow(wb, sheet, 8, 1);
				}
				int rowNo1 = 8;
				int production_tl = list.size();
				for (int i = 0;i < production_tl; i++) {	
					int price_tl = 0;
					nRow = sheet.getRow(rowNo);
					price_tl = list.get(i).getQuotationProductionPriceBeanList().size();
					nCell = nRow.getCell(0);
					nCell.setCellValue(1);
					sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo+price_tl-1,(short)0,(short)0));
					nCell = nRow.getCell(1);
					nCell.setCellValue(list.get(i).getProductName());
					sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo+price_tl-1,(short)1,(short)1));
					nCell = nRow.getCell(2);
					nCell.setCellValue(list.get(i).getProductNotes());
					sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo+price_tl-1,(short)2,(short)2));
					nCell = nRow.getCell(3);
					nCell.setCellValue(list.get(i).getMoldPrice());
					sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo+price_tl-1,(short)3,(short)3));
					nCell = nRow.getCell(4);
					nCell.setCellValue(list.get(i).getProcessList());
					sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo+price_tl-1,(short)4,(short)4));
					rowNo = rowNo + price_tl;
					for(int j = 0;j < price_tl; j++){					
						nRow = sheet.getRow(rowNo1+j);
						nCell = nRow.getCell(5);
						nCell.setCellValue(list.get(i).getQuotationProductionPriceBeanList().get(j).getQuantity());
						
						nCell = nRow.getCell(6);
						nCell.setCellValue(list.get(i).getQuotationProductionPriceBeanList().get(j).getProductPrice());

						nCell = nRow.getCell(7);
						Double price = 	new BigDecimal(list.get(i).getQuotationProductionPriceBeanList().get(j).getQuantity()).multiply(new BigDecimal(list.get(i).getQuotationProductionPriceBeanList().get(j).getProductPrice())).add(new BigDecimal(list.get(i).getMoldPrice())).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
						nCell.setCellValue(price);	
						sheet.addMergedRegion(new CellRangeAddress(rowNo1+j, rowNo1+j,(short)7,(short)8));						
					}
					rowNo1 = rowNo1 + price_tl;
				}
							
			}
			
			            // default
						//声明一个画图的顶级管理器
				        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
				        /**
						 * dx1：起始单元格的x偏移量，如例子中的255表示直线起始位置距A1单元格左侧的距离；
						 * dy1：起始单元格的y偏移量，如例子中的125表示直线起始位置距A1单元格上侧的距离；
						 * dx2：终止单元格的x偏移量，如例子中的1023表示直线起始位置距C3单元格左侧的距离；
						 * dy2：终止单元格的y偏移量，如例子中的150表示直线起始位置距C3单元格上侧的距离；
						 * colFrom：起始单元格列序号，从0开始计算；
						 * rowFrom：起始单元格行序号，从0开始计算，如例子中col1=0,row1=0就表示起始单元格为A1；
						 * colTo：终止单元格列序号，从0开始计算；
						 * rowTo：终止单元格行序号，从0开始计算，如例子中col2=2,row2=2就表示起始单元格为C3；
						 */
						
						int dx1 = 50, dy1 = 0, dx2 = 0, dy2 = 0;
						int colFrom = 0, rowFrom = 11+tl, colTo = 9, rowTo = 16+tl;
			
						//新建text文本框
						HSSFClientAnchor bigValueAnchorTextBox = new HSSFClientAnchor(dx1, dy1, dx2, dy2, (short)(colFrom), rowFrom+1, (short)(colTo-1), rowTo);
						HSSFTextbox bigValueTextbox = patriarch.createTextbox(bigValueAnchorTextBox);
						
						bigValueTextbox.setString(new HSSFRichTextString(quotationBean.getRemark()));
						bigValueTextbox.setLineStyle(HSSFSimpleShape.LINESTYLE_SOLID);	
//						bigValueTextbox2.set
						
			
                        rowNo = 8+tl;         
			       	    ServletContext sctx = request.getSession().getServletContext();				
					    String pic1 = sctx.getRealPath("/pdf/9001.png");
					    setPicture(pic1, sheet, rowNo, 7, rowNo+3, 8);
    
					    
					    String imgNames = quotationBean.getImgNames();
					    int img_tl = 0;
					    String[] split = imgNames.split(",");
					    if(!(imgNames == null || "".equals(imgNames))){
						    img_tl = split.length;
					    }

					    
					    dx1 = 255; dy1 = 125; dx2 = 200; dy2 = 150;
					    //图片4个一行存取，最多8张图片，大于8张取前8张
                        if(img_tl > 0 && img_tl <= 4){
                        for (int i = 0; i < img_tl; i++) {	    			        
 					    String pic_path = "";
                        pic_path = UploadAndDownloadPathUtil.getRemakPicturePath() + quotationBean.getFactoryId() +File.separator+ OperationFileUtil.changeZipName(split[i]);	
					    rowFrom	= 17+tl;
					    rowTo = 24+tl;		    
					    if(i == 0){
					    	colFrom = 0;
						    colTo = 2;
					    }else if(i == 1){
					    	colFrom = 2;
						    colTo = 3;
					    }else if(i == 2){
					    	colFrom = 3;
						    colTo = 5;
					    }else if(i == 3){
					    	colFrom = 5;
						    colTo = 7;
					    }
					    setPicture1(pic_path, sheet,  rowFrom+1, colFrom, rowTo, colTo);
                       }	
				    }else if(img_tl > 4 && img_tl <= 8){
				    	for (int i = 0; i < img_tl; i++) {	
//	    			        ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();          	
//	                        BufferedImage bufferImg = null;   
	 					    String pic_path = "";
	                        pic_path = UploadAndDownloadPathUtil.getRemakPicturePath() + quotationBean.getFactoryId() +File.separator+ split[i];
//					        bufferImg = ImageIO.read(new File(pic_path));     
//					        ImageIO.write(bufferImg, "jpg", byteArrayOut); 
					       
					        if(i<4){
							    rowFrom	= 17+tl;
							    rowTo = 24+tl;	
					        }else{
							    rowFrom	= 24+tl;
							    rowTo = 31+tl;	
					        }
	    
						    if(i == 0 || i == 4){
						    	colFrom = 0;
							    colTo = 2;
						    }else if(i == 1 || i == 5){
						    	colFrom = 2;
							    colTo = 3;
						    }else if(i == 2 || i == 6){
						    	colFrom = 3;
							    colTo = 5;
						    }else if(i == 3 || i == 7){
						    	colFrom = 5;
							    colTo = 7;
						    }
//						    bigValueAnchorTextBox = new HSSFClientAnchor(dx1, dy1, dx2, dy2, (short)(colFrom), rowFrom+1, (short)(colTo), rowTo);
//						    bigValueAnchorTextBox.setAnchorType(3);						
//							patriarch.createPicture(bigValueAnchorTextBox, wb.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG)); 
//							byteArrayOut.close();
						    setPicture1(pic_path, sheet,  rowFrom+1, colFrom, rowTo, colTo);
				    	}	
				    }
					    
					    
					    
					    
			
		String paths = UploadAndDownloadPathUtil.getQuotationPath() + quotationBean.getFactoryId();	
		
		tempPath= new File(paths);
//		deleteFile(tempPath);
		 if(!tempPath.exists() || !tempPath.isDirectory())
	        {
	            tempPath.mkdir();  //如果不存在，则创建该文件夹
	        }
		 wb.removeSheetAt(0);	   //删除模板sheet
		 FileOutputStream fs = new FileOutputStream(paths+ File.separator +quotationBean.getProjectId()+".xls",false);
		 wb.write(fs);	
		 fs.close();		 
		 office2PDF(paths+ File.separator+quotationBean.getProjectId()+".xls", paths+File.separator+quotationBean.getProjectId()+ ".pdf");	 
		 
		 return paths+File.separator+quotationBean.getProjectId()+ ".pdf";	 

	}
	
	
	/**
	 * pdf打印,使用excel编辑，生成pdf
	 * @param path
	 * @throws Exception
	 */
	public static String printExcel3(HttpServletRequest request,String path,QuotationBean quotationBean,List<QuotationProductionBean> list) throws Exception{
		
		int tl = 0;
        if(list.size() != 0){
        	for (QuotationProductionBean quotationProductionBean : list) {
        		List<QuotationProductionPriceBean> priceBeanLists = quotationProductionBean.getQuotationProductionPriceBeanList();
        		int pr_tl = priceBeanLists.size();
        		tl +=pr_tl;
			}
        }
		/*
		 * 打开模板，复制sheet，另存
		 */
       File file = new File(path+"pdf"+File.separator+"quotation3.xls");

		
		FileInputStream fi = new FileInputStream(file);
		HSSFWorkbook wb = new HSSFWorkbook(fi);
			wb.cloneSheet(0);						            //复制工作簿
			wb.setSheetName(1, "quotation for product");		//设置工作簿名称
		
			//设置相同内容
			int rowNo = 1;
			int colNo = 0;
			Row nRow = null;
			Cell nCell = null;
			
			HSSFSheet sheet = wb.getSheetAt(1);						//定位到当前工作表
			sheet.setForceFormulaRecalculation(true);				//强制公式自动计算，利用模板时，模板中的公式不会因值发生变化而自动计算。
			
			nRow = sheet.getRow(rowNo++);
			nCell = nRow.getCell(4);
//			sheet.addMergedRegion(new CellRangeAddress(0, 2,(short)3,(short)5));
			nCell.setCellValue("Quotaion:"+quotationBean.getProjectId());

			nCell = nRow.getCell(7);
			nCell.setCellValue("Contact:"+quotationBean.getSaleName());
						
			nRow = sheet.getRow(rowNo++);
			nCell = nRow.getCell(7);
			nCell.setCellValue(quotationBean.getQuoterEmail());
			nRow = sheet.getRow(rowNo++);
			nCell = nRow.getCell(7);
			nCell.setCellValue("TEL:"+quotationBean.getQuoterTel());

			nRow = sheet.getRow(rowNo++);
			nCell = nRow.getCell(2);
			nCell.setCellValue(quotationBean.getCustomerName());
			nCell = nRow.getCell(5);
			nCell.setCellValue(quotationBean.getQuotationDate());
			nCell = nRow.getCell(7);
			nCell.setCellValue("valid for" +quotationBean.getQuotationValidity()+ "days");
			
			rowNo = 8;
			//当只有一个产品时，处理列表显示
			if(tl == 1){
				nRow = sheet.getRow(rowNo++);	
				nCell = nRow.getCell(0);
				nCell.setCellValue(1);
				nCell = nRow.getCell(1);
				nCell.setCellValue(list.get(0).getProductName());
				nCell = nRow.getCell(2);
				nCell.setCellValue(list.get(0).getProductNotes());
				nCell = nRow.getCell(3);
				nCell.setCellValue(list.get(0).getMoldPrice());
				nCell = nRow.getCell(4);
				nCell.setCellValue(list.get(0).getProcessList());
				nCell = nRow.getCell(5);
				nCell.setCellValue(list.get(0).getQuotationProductionPriceBeanList().get(0).getQuantity() * list.get(0).getQuotationProductionPriceBeanList().get(0).getMaterialWeight());
				nCell = nRow.getCell(6);
				nCell.setCellValue(list.get(0).getQuotationProductionPriceBeanList().get(0).getQuantity());
				nCell = nRow.getCell(7);
				nCell.setCellValue(list.get(0).getQuotationProductionPriceBeanList().get(0).getProductPrice());
				nCell = nRow.getCell(8);
			Double price = 	new BigDecimal(list.get(0).getQuotationProductionPriceBeanList().get(0).getQuantity()).multiply(new BigDecimal(list.get(0).getQuotationProductionPriceBeanList().get(0).getProductPrice())).add(new BigDecimal(list.get(0).getMoldPrice())).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
				nCell.setCellValue(price);
				
			}else{
				for(int k=0;k<tl-1;k++){
				insertRow(wb, sheet, 8, 1);
				}
				int rowNo1 = 8;
				int production_tl = list.size();
				for (int i = 0;i < production_tl; i++) {	
					int price_tl = 0;
					nRow = sheet.getRow(rowNo);
					price_tl = list.get(i).getQuotationProductionPriceBeanList().size();
					nCell = nRow.getCell(0);
					nCell.setCellValue(1);
					sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo+price_tl-1,(short)0,(short)0));
					nCell = nRow.getCell(1);
					nCell.setCellValue(list.get(i).getProductName());
					sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo+price_tl-1,(short)1,(short)1));
					nCell = nRow.getCell(2);
					nCell.setCellValue(list.get(i).getProductNotes());
					sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo+price_tl-1,(short)2,(short)2));
					nCell = nRow.getCell(3);
					nCell.setCellValue(list.get(i).getMoldPrice());
					sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo+price_tl-1,(short)3,(short)3));
					nCell = nRow.getCell(4);
					nCell.setCellValue(list.get(i).getProcessList());
					sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo+price_tl-1,(short)4,(short)4));
					rowNo = rowNo + price_tl;
					for(int j = 0;j < price_tl; j++){					
						nRow = sheet.getRow(rowNo1+j);
						nCell = nRow.getCell(5);
						nCell.setCellValue(list.get(i).getQuotationProductionPriceBeanList().get(j).getQuantity() * list.get(i).getQuotationProductionPriceBeanList().get(j).getMaterialWeight());
						
						nCell = nRow.getCell(6);
						nCell.setCellValue(list.get(i).getQuotationProductionPriceBeanList().get(j).getQuantity());
						
						nCell = nRow.getCell(7);
						nCell.setCellValue(list.get(i).getQuotationProductionPriceBeanList().get(j).getProductPrice());

						nCell = nRow.getCell(8);
						Double price = 	new BigDecimal(list.get(i).getQuotationProductionPriceBeanList().get(j).getQuantity()).multiply(new BigDecimal(list.get(i).getQuotationProductionPriceBeanList().get(j).getProductPrice())).add(new BigDecimal(list.get(i).getMoldPrice())).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
						nCell.setCellValue(price);	
						sheet.addMergedRegion(new CellRangeAddress(rowNo1+j, rowNo1+j,(short)8,(short)9));						
					}
					rowNo1 = rowNo1 + price_tl;
				}
							
			}
			
			            // default
						//声明一个画图的顶级管理器
				        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
				        /**
						 * dx1：起始单元格的x偏移量，如例子中的255表示直线起始位置距A1单元格左侧的距离；
						 * dy1：起始单元格的y偏移量，如例子中的125表示直线起始位置距A1单元格上侧的距离；
						 * dx2：终止单元格的x偏移量，如例子中的1023表示直线起始位置距C3单元格左侧的距离；
						 * dy2：终止单元格的y偏移量，如例子中的150表示直线起始位置距C3单元格上侧的距离；
						 * colFrom：起始单元格列序号，从0开始计算；
						 * rowFrom：起始单元格行序号，从0开始计算，如例子中col1=0,row1=0就表示起始单元格为A1；
						 * colTo：终止单元格列序号，从0开始计算；
						 * rowTo：终止单元格行序号，从0开始计算，如例子中col2=2,row2=2就表示起始单元格为C3；
						 */
						
						int dx1 = 50, dy1 = 0, dx2 = 0, dy2 = 0;
						int colFrom = 0, rowFrom = 11+tl, colTo = 9, rowTo = 16+tl;
			
						//新建text文本框
						HSSFClientAnchor bigValueAnchorTextBox = new HSSFClientAnchor(dx1, dy1, dx2, dy2, (short)(colFrom), rowFrom+1, (short)(colTo-1), rowTo);
						HSSFTextbox bigValueTextbox = patriarch.createTextbox(bigValueAnchorTextBox);
						
						bigValueTextbox.setString(new HSSFRichTextString(quotationBean.getRemark()));
						bigValueTextbox.setLineStyle(HSSFSimpleShape.LINESTYLE_SOLID);	
//						bigValueTextbox2.set
						
			
                        rowNo = 8+tl;         
			       	    ServletContext sctx = request.getSession().getServletContext();				
					    String pic1 = sctx.getRealPath("/pdf/9001.png");
					    setPicture(pic1, sheet, rowNo, 7, rowNo+3, 8);
    
					    
					    String imgNames = quotationBean.getImgNames();
					    int img_tl = 0;
					    String[] split = imgNames.split(",");
					    if(!(imgNames == null || "".equals(imgNames))){
						    img_tl = split.length;
					    }

					    
					    dx1 = 255; dy1 = 125; dx2 = 200; dy2 = 150;
					    //图片4个一行存取，最多8张图片，大于8张取前8张
                        if(img_tl > 0 && img_tl <= 4){
                        for (int i = 0; i < img_tl; i++) {	    			        
 					    String pic_path = "";
                        pic_path = UploadAndDownloadPathUtil.getRemakPicturePath() + quotationBean.getFactoryId() +File.separator+ OperationFileUtil.changeZipName(split[i]);	
					    rowFrom	= 17+tl;
					    rowTo = 24+tl;		    
					    if(i == 0){
					    	colFrom = 0;
						    colTo = 2;
					    }else if(i == 1){
					    	colFrom = 2;
						    colTo = 3;
					    }else if(i == 2){
					    	colFrom = 3;
						    colTo = 5;
					    }else if(i == 3){
					    	colFrom = 5;
						    colTo = 7;
					    }
					    setPicture1(pic_path, sheet,  rowFrom+1, colFrom, rowTo, colTo);
                       }	
				    }else if(img_tl > 4 && img_tl <= 8){
				    	for (int i = 0; i < img_tl; i++) {	
//	    			        ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();          	
//	                        BufferedImage bufferImg = null;   
	 					    String pic_path = "";
	                        pic_path = UploadAndDownloadPathUtil.getRemakPicturePath() + quotationBean.getFactoryId() +File.separator+ split[i];
//					        bufferImg = ImageIO.read(new File(pic_path));     
//					        ImageIO.write(bufferImg, "jpg", byteArrayOut); 
					       
					        if(i<4){
							    rowFrom	= 17+tl;
							    rowTo = 24+tl;	
					        }else{
							    rowFrom	= 24+tl;
							    rowTo = 31+tl;	
					        }
	    
						    if(i == 0 || i == 4){
						    	colFrom = 0;
							    colTo = 2;
						    }else if(i == 1 || i == 5){
						    	colFrom = 2;
							    colTo = 3;
						    }else if(i == 2 || i == 6){
						    	colFrom = 3;
							    colTo = 5;
						    }else if(i == 3 || i == 7){
						    	colFrom = 5;
							    colTo = 7;
						    }
//						    bigValueAnchorTextBox = new HSSFClientAnchor(dx1, dy1, dx2, dy2, (short)(colFrom), rowFrom+1, (short)(colTo), rowTo);
//						    bigValueAnchorTextBox.setAnchorType(3);						
//							patriarch.createPicture(bigValueAnchorTextBox, wb.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG)); 
//							byteArrayOut.close();
						    setPicture1(pic_path, sheet,  rowFrom+1, colFrom, rowTo, colTo);
				    	}	
				    }
					    
					    
					    
					    
			
		String paths = UploadAndDownloadPathUtil.getQuotationPath() + quotationBean.getFactoryId();	
		
		tempPath= new File(paths);
//		deleteFile(tempPath);
		 if(!tempPath.exists() || !tempPath.isDirectory())
	        {
	            tempPath.mkdir();  //如果不存在，则创建该文件夹
	        }
		 wb.removeSheetAt(0);	   //删除模板sheet
		 FileOutputStream fs = new FileOutputStream(paths+ File.separator +quotationBean.getProjectId()+".xls",false);
		 wb.write(fs);	
		 fs.close();		 
		 office2PDF(paths+ File.separator+quotationBean.getProjectId()+".xls", paths+File.separator+quotationBean.getProjectId()+ ".pdf");	 
		 
		 return paths+File.separator+quotationBean.getProjectId()+ ".pdf";	 

	}

	    	      
	    	    /**  
	    	     * 将Office文档转换为PDF. 运行该函数需要用到OpenOffice, OpenOffice下载地址为  
	    	     * http://www.openoffice.org/  
	    	     *   
	    	     * <pre>  
	    	     * 方法示例:  
	    	     * String sourcePath = "F:\\office\\source.doc";  
	    	     * String destFile = "F:\\pdf\\dest.pdf";  
	    	     * Converter.office2PDF(sourcePath, destFile);  
	    	     * </pre>  
	    	     *   
	    	     * @param sourceFile  
	    	     *            源文件, 绝对路径. 可以是Office2003-2007全部格式的文档, Office2010的没测试. 包括.doc,  
	    	     *            .docx, .xls, .xlsx, .ppt, .pptx等. 示例: F:\\office\\source.doc  
	    	     * @param destFile  
	    	     *            目标文件. 绝对路径. 示例: F:\\pdf\\dest.pdf  
	    	     * @return 操作成功与否的提示信息. 如果返回 -1, 表示找不到源文件, 或url.properties配置错误; 如果返回 0,  
	    	     *         则表示操作成功; 返回1, 则表示转换失败  
	    	     */    
	
	            private static String openOfficePath = "C:\\Program Files (x86)\\OpenOffice 4";
//	            private static String openOfficePath = "/opt/openoffice4";
	
	    	    public static int office2PDF(String sourceFile, String destFile) throws Exception{    
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
	    	    
	    	            String OpenOffice_HOME = openOfficePath;//这里是OpenOffice的安装目录    
	    	            // 如果从文件中读取的URL地址最后一个字符不是 '\'，则添加'\'    
	    	            if (OpenOffice_HOME.charAt(OpenOffice_HOME.length() - 1) != '/') {    
	    	                OpenOffice_HOME += "\\";    
	    	            }    
	    	            // 启动OpenOffice的服务    
//	    	            String command = OpenOffice_HOME    
//	    	                    + "program/soffice -headless -accept=\"socket,host=162.249.2.241,port=8100;urp;\" -nofirststartwizard";      
//	    	            Process pro = Runtime.getRuntime().exec(command); 
//	    	            // connect to an OpenOffice.org instance running on port 8100    
//	    	            OpenOfficeConnection connection = new SocketOpenOfficeConnection("162.249.2.241", 8100);   
	    	            
	    	            
//	    	            // 启动OpenOffice的服务    
//	    	            String command = OpenOffice_HOME    
//	    	            		+ "program/soffice -headless -accept=\"socket,host=67.198.209.91,port=8100;urp;\" -nofirststartwizard";      
//	    	            Process pro = Runtime.getRuntime().exec(command); 
//	    	            // connect to an OpenOffice.org instance running on port 8100    
//	    	            OpenOfficeConnection connection = new SocketOpenOfficeConnection("67.198.209.91", 8100);    
	    	            
	    	            
//	    	            // 启动OpenOffice的服务    (window启动)
	    	            String command = OpenOffice_HOME    
	    	            		+ "program\\soffice -headless -accept=\"socket,host=192.168.1.151,port=8100;urp;\" -nofirststartwizard";      
	    	            Process pro = Runtime.getRuntime().exec(command); 
	    	            // connect to an OpenOffice.org instance running on port 8100    
	    	            OpenOfficeConnection connection = new SocketOpenOfficeConnection("192.168.1.151", 8100);    
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
	
 
	
	
	
	

	//删除文件和目录
	private static void clearFiles(String workspaceRootPath){
	     File file = new File(workspaceRootPath);
	     if(file.exists()){
	          deleteFile(file);
	     }
	}
	private static void deleteFile(File file){
	     if(file.isDirectory()){
	          File[] files = file.listFiles();
	          for(int i=0; i<files.length; i++){
	               deleteFile(files[i]);
	          }
	     }
	     file.delete();
	}
	
	
	
	public static void delFolder(String folderPath) {
	     try {
	        boolean delAllFile = delAllFile(folderPath); //删除完里面所有内容
	        String filePath = folderPath;
	        filePath = filePath.toString();
	        java.io.File myFilePath = new java.io.File(filePath);
	        myFilePath.delete(); //删除空文件夹
	     } catch (Exception e) {
	       e.printStackTrace(); 
	     }
	}
	//删除指定文件夹下所有文件
	//param path 文件夹完整绝对路径
	   public static boolean delAllFile(String path) {
	       boolean flag = false;
	       File file = new File(path);
	       if (!file.exists()) {
	         return flag;
	       }
	       if (!file.isDirectory()) {
	         return flag;
	       }
	       String[] tempList = file.list();
	       File temp = null;
	       for (int i = 0; i < tempList.length; i++) {
	          if (path.endsWith(File.separator)) {
	             temp = new File(path + tempList[i]);
	          } else {
	              temp = new File(path + File.separator + tempList[i]);
	          }
	          if (temp.isFile()) {
	             temp.delete();
	          }
	          if (temp.isDirectory()) {
	             delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
	             delFolder(path + "/" + tempList[i]);//再删除空文件夹
	             flag = true;
	          }
	       }
	       return flag;
	     }
	
	   
	   
	    /**
	     * excel插入行数据
	     * @param wb
	     * @param sheet
	     * @param starRow
	     * @param rows
	     */
		public static void insertRow(HSSFWorkbook wb, HSSFSheet sheet, int starRow,int rows) {

			
			  sheet.shiftRows(starRow + 1, sheet.getLastRowNum(), rows,true,false);
			  
			  starRow = starRow - 1;

			  for (int i = 0; i < rows; i++) {

			   HSSFRow sourceRow = null;
			   HSSFRow targetRow = null;
			   HSSFCell sourceCell = null;
			   HSSFCell targetCell = null;
			   short m;

			   starRow = starRow + 1;
			   sourceRow = sheet.getRow(starRow);
			   targetRow = sheet.createRow(starRow + 1);
			   targetRow.setHeight(sourceRow.getHeight());

			   for (m = sourceRow.getFirstCellNum(); m < sourceRow.getLastCellNum(); m++) {

			    sourceCell = sourceRow.getCell(m);
			    targetCell = targetRow.createCell(m);
			    targetCell.setCellStyle(sourceCell.getCellStyle());
			    targetCell.setCellType(sourceCell.getCellType());

			   }
			  }

			 }

		
		
		//处理图片，excel中图片是单独对象存放
		public static void setPicture(String pic, HSSFSheet sheet, int startRow, int startCol, int stopRow, int stopCol) throws IOException{
			File imageFile = new File(pic);
			if(imageFile.exists()){
				InputStream is = new FileInputStream(new File(pic));
				byte[] bytes = IOUtils.toByteArray(is);
				int pictureIdx = sheet.getWorkbook().addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);		//扩展名可为.jpg/.jpeg/.png
				is.close();
				
				HSSFPatriarch drawing = sheet.createDrawingPatriarch();	// Create the drawing patriarch.  This is the top level container for all shapes.
				//前面四个参数是图片偏移量
				HSSFClientAnchor anchor = new HSSFClientAnchor(512, 200, 0, 0, (short)startCol, startRow, (short)stopCol, stopRow);	//add a picture shape
				anchor.setRow1(startRow);							//set position corner of the picture		
				anchor.setCol1(startCol);
				anchor.setRow2(stopRow);
				anchor.setCol2(stopCol);
				
				drawing.createPicture(anchor, pictureIdx);
			}
		}	
			//处理图片备注存放 偏移量不同    dx1 = 255; dy1 = 125; dx2 = 200; dy2 = 150;
			public static void setPicture1(String pic, HSSFSheet sheet, int startRow, int startCol, int stopRow, int stopCol) throws IOException{
				File imageFile = new File(pic);
				if(imageFile.exists()){
					InputStream is = new FileInputStream(new File(pic));
					byte[] bytes = IOUtils.toByteArray(is);
					int pictureIdx = sheet.getWorkbook().addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);		//扩展名可为.jpg/.jpeg/.png
					is.close();
					
					HSSFPatriarch drawing = sheet.createDrawingPatriarch();	// Create the drawing patriarch.  This is the top level container for all shapes.
					//前面四个参数是图片偏移量
					HSSFClientAnchor anchor = new HSSFClientAnchor(255, 125, 200, 150, (short)startCol, startRow, (short)stopCol, stopRow);	//add a picture shape
					anchor.setRow1(startRow);							//set position corner of the picture		
					anchor.setCol1(startCol);
					anchor.setRow2(stopRow);
					anchor.setCol2(stopCol);
					
					drawing.createPicture(anchor, pictureIdx);
				}
		}


		
			
		    
		    public static void main(String []args) throws Exception {    
		          
		        String sourcePath = "G:\\apache-tomcat-7.0.57\\wtpwebapps\\quotation\\f1010\\11.xls";   
		        String destFile = "f:\\11.pdf";   
		        int flag = office2PDF(sourcePath, destFile);   
		        if (flag == 1) {  
		            System.out.println("转化失败");  
		        }else if(flag == 0){  
		            System.out.println("转化成功");  
		        }else {  
		            System.out.println("找不到源文件, 或url.properties配置错误");            
		        }  
		    }      
		
		
		
}
