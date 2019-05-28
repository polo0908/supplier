package com.cbt.print;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.cbt.entity.ClientOrder;
import com.cbt.entity.FactoryInfo;
import com.cbt.entity.InvoiceInfo;
import com.cbt.entity.InvoiceProduct;
import com.cbt.entity.ShippingInfo;
import com.cbt.entity.User;
import com.cbt.util.DateFormat;
import com.cbt.util.DownloadUtil;
import com.cbt.util.NumberParser;
import com.cbt.util.OperationFileUtil;
import com.cbt.util.UploadAndDownloadPathUtil;
import com.cbt.util.UtilFuns;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import com.artofsolving.jodconverter.DocumentConverter;  
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;  
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;  
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;  



/**
 * @Description: 根据模板文件打印，输出发票pdf
 * @Author:	polo
 * @CreateDate:	2017/4/5
 */

public class InvoicePrintTemplate {
	
	
	private static File tempPath;	
	
	public static void print(HttpServletRequest request,ClientOrder clientOrder,String path,String invoiceId,HttpServletResponse response) throws IOException, DocumentException {  
	
//	String fileName = path + "\\China Synergy Group invoice.pdf";// pdf模板  
	
	 String fileName = UploadAndDownloadPathUtil.getClientOrderUpLoadAndDownLoadPath() + clientOrder.getOrderId() + File.separator +invoiceId+ ".pdf";	
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
	 * @param response
	 * @param invoiceInfo
	 * @param invoiceProducts
	 * @param invoiceMolds
	 * @throws Exception
	 */
	public static String printExcel(String path,HttpServletResponse response,User user,
			FactoryInfo factoryInfo,ShippingInfo shippingInfo,ClientOrder clientOrder,
			InvoiceInfo invoiceInfo,List<InvoiceProduct> invoiceProducts,String remark,String remark1,String paymentRemark) throws Exception{
		
		
		String invoiceId = invoiceInfo.getInvoiceId();
		int tl = invoiceProducts.size();
		/*
		 * 打开模板，复制sheet，另存
		 */
		File file = new File(path+"pdf"+File.separator+"invoice.xls");	
			
		FileInputStream fi = new FileInputStream(file);
		HSSFWorkbook wb = new HSSFWorkbook(fi);
			wb.cloneSheet(0);						        //复制工作簿
			wb.setSheetName(1, "invoice for product");		//设置工作簿名称
		
			//设置相同内容
			int rowNo = 3;
			int colNo = 0;
			Row nRow = null;
			Cell nCell = null;
			
			HSSFSheet sheet = wb.getSheetAt(1);						//定位到当前工作表
			sheet.setForceFormulaRecalculation(true);				//强制公式自动计算，利用模板时，模板中的公式不会因值发生变化而自动计算。
			
			//插入行对象
			if(tl > 1){
				for(int j=0;j<tl-1;j++){
				 insertRow(wb, sheet, 10, 1);	
				}								
			}				
			nRow = sheet.getRow(rowNo++);
			nCell = nRow.getCell(1);
			nCell.setCellValue(UtilFuns.convertNull(user.getUserName()));
			nCell = nRow.getCell(4);
			if(!(invoiceInfo.getCreateTime() == null || "".equals(invoiceInfo.getCreateTime()))){
				nCell.setCellValue(DateFormat.formatDate1(invoiceInfo.getCreateTime()));	
			}else{
				nCell.setCellValue(DateFormat.currentDate());	
			}
			
			
			nRow = sheet.getRow(rowNo++);
			nCell = nRow.getCell(1);
			if(!(shippingInfo == null || "".equals(shippingInfo))){
				nCell.setCellValue(shippingInfo.getDetailedAddress() == null ? "" : shippingInfo.getDetailedAddress());	
			}			
			
			nRow = sheet.getRow(rowNo++);
			nCell = nRow.getCell(1);
			nCell.setCellValue(invoiceInfo.getOrderId());
			
			
	        // default
			//声明一个画图的顶级管理器
	        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
	        HSSFPatriarch patriarch1 = sheet.createDrawingPatriarch();
	        HSSFPatriarch patriarch2 = sheet.createDrawingPatriarch();
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
			
			int dx1 = 50, dy1 = 0, dx2 = 450, dy2 = 0;
			int colFrom = 0, rowFrom = 21, colTo = 7, rowTo = 35;
			int rowFrom1 = 35,rowTo1 = 39;
			int rowFrom2 = 15,rowTo2 = 20;
			
			
           if(tl == 1 || tl == 0){
				if(tl == 0){
					rowNo = 10;	
					nRow = sheet.getRow(rowNo++);
					nCell = nRow.getCell(2);
					nCell.setCellValue("/");
					nCell = nRow.getCell(3);
					nCell.setCellValue("/");
					nCell = nRow.getCell(4);
					nCell.setCellValue("/");
					nCell = nRow.getCell(5);
					nCell.setCellValue("/");
					nCell = nRow.getCell(6);
					nCell.setCellValue(UtilFuns.convertNull(invoiceInfo.getAmount())); 
					
					
					nRow = sheet.getRow(rowNo++);
					nCell = nRow.getCell(6);
					nCell.setCellValue(0);
					
					
					nRow = sheet.getRow(rowNo++);
					nCell = nRow.getCell(6);
					nCell.setCellValue(0);
					
								
					nRow = sheet.getRow(rowNo++);
					nCell = nRow.getCell(6);
					nCell.setCellValue(UtilFuns.convertNull(invoiceInfo.getAmount()));
					
					nRow = sheet.getRow(rowNo++);
					nCell = nRow.getCell(1);
					nCell.setCellValue(NumberParser.parse(invoiceInfo.getAmount().toString()));
				}else{
				rowNo = 10;	
				nRow = sheet.getRow(rowNo++);
				nCell = nRow.getCell(2);
				nCell.setCellValue(UtilFuns.convertNull(invoiceProducts.get(0).getProductName()));
				nCell = nRow.getCell(3);
				nCell.setCellValue(UtilFuns.convertNull(invoiceProducts.get(0).getQuantity()));
				nCell = nRow.getCell(4);
				nCell.setCellValue(UtilFuns.convertNull(invoiceProducts.get(0).getUnitPrice()));
				nCell = nRow.getCell(5);
				nCell.setCellValue(UtilFuns.convertNull(invoiceProducts.get(0).getMoldPrice()));
				nCell = nRow.getCell(6);
				Double price = new BigDecimal(invoiceProducts.get(0).getQuantity()).multiply(new BigDecimal
						(invoiceProducts.get(0).getUnitPrice())).add(new BigDecimal(invoiceProducts.get(0).getMoldPrice())).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				nCell.setCellValue(price);
		
				
				nRow = sheet.getRow(rowNo++);
				nCell = nRow.getCell(6);
				nCell.setCellValue(UtilFuns.convertNull(invoiceInfo.getOtherPrice()));
				
				
				nRow = sheet.getRow(rowNo++);
				nCell = nRow.getCell(6);
				nCell.setCellValue(UtilFuns.convertNull(invoiceInfo.getShippingFee()));
				
				
				Double total = new BigDecimal(invoiceInfo.getProductPrice()).add(new BigDecimal(invoiceInfo.getMoldPrice())).add(new BigDecimal(invoiceInfo.getOtherPrice())).add(new BigDecimal(invoiceInfo.getShippingFee())).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				nRow = sheet.getRow(rowNo++);
				nCell = nRow.getCell(6);
				nCell.setCellValue(total);
				
				nRow = sheet.getRow(rowNo++);
				nCell = nRow.getCell(1);
				nCell.setCellValue(NumberParser.parse(total.toString()));
			}	
				
				
				//新建text文本框
				HSSFClientAnchor bigValueAnchorTextBox2 = new HSSFClientAnchor(dx1, dy1, dx2, dy2, (short)(colFrom), rowFrom2+1, (short)(colTo-1), rowTo2);
				HSSFTextbox bigValueTextbox2 = patriarch2.createTextbox(bigValueAnchorTextBox2);
				
				bigValueTextbox2.setString(new HSSFRichTextString(paymentRemark));
				bigValueTextbox2.setLineStyle(HSSFSimpleShape.LINESTYLE_SOLID);
				//新建text文本框
				HSSFClientAnchor bigValueAnchorTextBox = new HSSFClientAnchor(dx1, dy1, dx2, dy2, (short)(colFrom), rowFrom+1, (short)(colTo-1), rowTo);
				HSSFTextbox bigValueTextbox = patriarch.createTextbox(bigValueAnchorTextBox);

				bigValueTextbox.setString(new HSSFRichTextString(remark));
				bigValueTextbox.setLineStyle(HSSFSimpleShape.LINESTYLE_SOLID);
				
				//新建text文本框
				HSSFClientAnchor bigValueAnchorTextBox1 = new HSSFClientAnchor(dx1, dy1, dx2, dy2, (short)(colFrom), rowFrom1+1, (short)(colTo-1), rowTo1);
				HSSFTextbox bigValueTextbox1 = patriarch1.createTextbox(bigValueAnchorTextBox1);
				
				bigValueTextbox1.setString(new HSSFRichTextString(remark1));
				bigValueTextbox1.setLineStyle(HSSFSimpleShape.LINESTYLE_SOLID);
				
				
				
				
				
			}else if(tl > 1){
				
				for (int i = 0; i < tl; i++) {
					rowNo = 10;	
					nRow = sheet.getRow(rowNo+i);
					nCell = nRow.getCell(0);
					nCell.setCellValue(i+1);
					nCell = nRow.getCell(1);
					nCell.setCellValue("/");
					nCell = nRow.getCell(2);
					nCell.setCellValue(UtilFuns.convertNull(invoiceProducts.get(i).getProductName()));
					nCell = nRow.getCell(3);
					nCell.setCellValue(UtilFuns.convertNull(invoiceProducts.get(i).getQuantity()));
					nCell = nRow.getCell(4);
					nCell.setCellValue(UtilFuns.convertNull(invoiceProducts.get(i).getUnitPrice()));
					nCell = nRow.getCell(5);
					nCell.setCellValue(UtilFuns.convertNull(invoiceProducts.get(i).getMoldPrice()));
					nCell = nRow.getCell(6);
					Double price = new BigDecimal(invoiceProducts.get(i).getQuantity()).multiply(new BigDecimal
							(invoiceProducts.get(i).getUnitPrice())).add(new BigDecimal(invoiceProducts.get(i).getMoldPrice())).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
					nCell.setCellValue(price);
				}	
				    rowNo = 10+tl;
					nRow = sheet.getRow(rowNo++);
					nCell = nRow.getCell(6);
					nCell.setCellValue(invoiceInfo.getOtherPrice());
					
					
					nRow = sheet.getRow(rowNo++);
					nCell = nRow.getCell(6);
					nCell.setCellValue(invoiceInfo.getShippingFee());
				
				
					Double total = new BigDecimal(invoiceInfo.getProductPrice()).add(new BigDecimal(invoiceInfo.getMoldPrice())).add(new BigDecimal(invoiceInfo.getOtherPrice())).add(new BigDecimal(invoiceInfo.getShippingFee())).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
					nRow = sheet.getRow(rowNo++);
					nCell = nRow.getCell(6);
					nCell.setCellValue(total);
					
					nRow = sheet.getRow(rowNo++);
					nCell = nRow.getCell(1);
					nCell.setCellValue(NumberParser.parse(total.toString()));
					
					
					colFrom = 0; rowFrom = 19+tl; colTo = 7; rowTo = 33+tl;	rowFrom1 = 34+tl;rowTo1 = 38+tl;rowFrom2 = 14+tl;rowTo2 = 19+tl;	
					
					//新建text文本框
					HSSFClientAnchor bigValueAnchorTextBox2 = new HSSFClientAnchor(dx1, dy1, dx2, dy2, (short)(colFrom), rowFrom2+1, (short)(colTo-1), rowTo2);
					HSSFTextbox bigValueTextbox2 = patriarch2.createTextbox(bigValueAnchorTextBox2);
					
					bigValueTextbox2.setString(new HSSFRichTextString(paymentRemark));
					bigValueTextbox2.setLineStyle(HSSFSimpleShape.LINESTYLE_SOLID);
					

					//新建text文本框
					HSSFClientAnchor bigValueAnchorTextBox = new HSSFClientAnchor(dx1, dy1, dx2, dy2, (short)(colFrom), rowFrom+1, (short)(colTo-1), rowTo);
					HSSFTextbox bigValueTextbox = patriarch.createTextbox(bigValueAnchorTextBox);

					bigValueTextbox.setString(new HSSFRichTextString(remark));
					bigValueTextbox.setLineStyle(HSSFSimpleShape.LINESTYLE_SOLID);
					
					
					//新建text文本框
					HSSFClientAnchor bigValueAnchorTextBox1 = new HSSFClientAnchor(dx1, dy1, dx2, dy2, (short)(colFrom), rowFrom1+1, (short)(colTo-1), rowTo1);
					HSSFTextbox bigValueTextbox1 = patriarch1.createTextbox(bigValueAnchorTextBox1);
					
					bigValueTextbox1.setString(new HSSFRichTextString(remark1));
					bigValueTextbox1.setLineStyle(HSSFSimpleShape.LINESTYLE_SOLID);
		
			}
			
			
		String paths = UploadAndDownloadPathUtil.getInvoiceUpLoadAndDownLoadPath()+invoiceId;	
		
		String pdfPath = UploadAndDownloadPathUtil.getClientOrderUpLoadAndDownLoadPath() + clientOrder.getOrderId();
		
		tempPath= new File(paths);
//		deleteFile(tempPath);
		 if(!tempPath.exists() || !tempPath.isDirectory())
	        {
	            tempPath.mkdir();  //如果不存在，则创建该文件夹
	        }
		 wb.removeSheetAt(0);	   //删除模板sheet
		 FileOutputStream fs = new FileOutputStream(paths+ File.separator +invoiceId+".xls",false);
		 wb.write(fs);	
		 fs.close();		 
		 office2PDF(paths+ File.separator+invoiceId+".xls", pdfPath+File.separator+invoiceId+ ".pdf");
		 delAllFile(UploadAndDownloadPathUtil.getInvoiceUpLoadAndDownLoadPath());
		 
		 
		 return pdfPath+File.separator+invoiceId+ ".pdf";	 

	}
		

	
	
	/**
	 * 将excel转化为Pdf
	 * @param els
	 * @param pdf
	 * @throws Exception 
	 */
	public static void els2pdf(String els,String pdf){  
        
		OperationFileUtil.deleteFile(pdf);
		ComThread.InitSTA();
        System.out.println("Starting excel...");    
        long start = System.currentTimeMillis();    
        ActiveXComponent app = new ActiveXComponent("Excel.Application");   
          
        try {        	
            app.setProperty("Visible",false);    
            Dispatch workbooks = app.getProperty("Workbooks").toDispatch();    
            System.out.println("opening document:" + els);    
            Dispatch workbook = Dispatch.invoke(workbooks, "Open", Dispatch.Method, new Object[]{els, new Variant(false),new Variant(false)}, new int[3]).toDispatch();    
            Dispatch.invoke(workbook, "SaveAs", Dispatch.Method, new Object[] {    
            pdf, new Variant(57), new Variant(false),    
            new Variant(57), new Variant(57), new Variant(false),    
            new Variant(true), new Variant(57), new Variant(true),    
            new Variant(true), new Variant(true) }, new int[1]);    
            Variant f = new Variant(false);    
            System.out.println("to pdf " + pdf);    
            Dispatch.call(workbook, "Close", f);    
            long end = System.currentTimeMillis();    
            System.out.println("completed..used:" + (end - start)/1000 + " s");    
        } catch (Exception e) {    
            System.out.println("========Error:Operation fail:" + e.getMessage());  

        }finally {    
            if (app != null){    
                app.invoke("Quit", new Variant[] {});    
            }    
            ComThread.Release();
        }    
	
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
	
//	            private static String openOfficePath = "C:\\Program Files (x86)\\OpenOffice 4";
	            private static String openOfficePath = "/opt/openoffice4";
	
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
	    	                OpenOffice_HOME += "/";    
	    	            }    
	    	            // 启动OpenOffice的服务    
//	    	            String command = OpenOffice_HOME    
//	    	            		+ "program/soffice -headless -accept=\"socket,host=162.249.2.241,port=8100;urp;\" -nofirststartwizard &";      
//	    	            Process pro = Runtime.getRuntime().exec(command); 
//	    	            // connect to an OpenOffice.org instance running on port 8100    
//	    	            OpenOfficeConnection connection = new SocketOpenOfficeConnection("162.249.2.241", 8100);   
	    	            
	    	            
	    	            // 启动OpenOffice的服务    
	    	            String command = OpenOffice_HOME    
	    	                    + "program/soffice -headless -accept=\"socket,host=67.198.209.91,port=8100;urp;\" -nofirststartwizard &";      
	    	            Process pro = Runtime.getRuntime().exec(command); 
	    	            // connect to an OpenOffice.org instance running on port 8100    
	    	            OpenOfficeConnection connection = new SocketOpenOfficeConnection("67.198.209.91", 8100);    
	    	            
	    	            
	    	            
//	    	            String command = OpenOffice_HOME    
//	    	                    + "program\\soffice -headless -accept=\"socket,host=127.0.0.1,port=8100;urp;\" -nofirststartwizard";      
//	    	            Process pro = Runtime.getRuntime().exec(command); 
//	    	            // connect to an OpenOffice.org instance running on port 8100    
//	    	            OpenOfficeConnection connection = new SocketOpenOfficeConnection("127.0.0.1", 8100);   
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
	    	    

	    	    
	        
	    public static void main(String []args) throws Exception {    
	          
	        String sourcePath = "G:\\apache-tomcat-7.0.57\\wtpwebapps\\invoice\\inv10010\\inv10010.xls";   
	        String destFile = "G:\\apache-tomcat-7.0.57\\wtpwebapps\\invoice\\inv10010\\dest.pdf";   
	        int flag = office2PDF(sourcePath, destFile);   
	        if (flag == 1) {  
	            System.out.println("转化失败");  
	        }else if(flag == 0){  
	            System.out.println("转化成功");  
	        }else {  
	            System.out.println("找不到源文件, 或url.properties配置错误");            
	        }  
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
		public void setPicture(String pic, HSSFSheet sheet, int startRow, int startCol, int stopRow, int stopCol) throws IOException{
			File imageFile = new File(pic);
			if(imageFile.exists()){
				InputStream is = new FileInputStream(new File(pic));
				byte[] bytes = IOUtils.toByteArray(is);
				int pictureIdx = sheet.getWorkbook().addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);		//扩展名可为.jpg/.jpeg/.png
				is.close();
				
				HSSFPatriarch drawing = sheet.createDrawingPatriarch();	// Create the drawing patriarch.  This is the top level container for all shapes.
				//前面四个参数是图片偏移量
				HSSFClientAnchor anchor = new HSSFClientAnchor(512, 255, 0, 0, (short)startCol, startRow, (short)stopCol, stopRow);	//add a picture shape
				anchor.setRow1(startRow);							//set position corner of the picture		
				anchor.setCol1(startCol);
				anchor.setRow2(stopRow);
				anchor.setCol2(stopCol);
				
				drawing.createPicture(anchor, pictureIdx);
			}
		}
	
		

		 public static void excel2pdf(String sourceFile, String destFile) throws DocumentException, IOException{

			 FileInputStream input_document = new FileInputStream(new File(sourceFile));
             // Read workbook into HSSFWorkbook
             HSSFWorkbook my_xls_workbook = new HSSFWorkbook(input_document); 
             // Read worksheet into HSSFSheet
             HSSFSheet my_worksheet = my_xls_workbook.getSheetAt(0); 
             // To iterate over the rows
             Iterator<Row> rowIterator = my_worksheet.iterator();
             //We will create output PDF document objects at this point
             Document iText_xls_2_pdf = new Document();
             PdfWriter.getInstance(iText_xls_2_pdf, new FileOutputStream(destFile));
             iText_xls_2_pdf.open();
             //we have two columns in the Excel sheet, so we create a PDF table with two columns
             //Note: There are ways to make this dynamic in nature, if you want to.
             PdfPTable my_table = new PdfPTable(2);
             //We will use the object below to dynamically add new data to the table
             PdfPCell table_cell;
             //Loop through rows.
             while(rowIterator.hasNext()) {
                     Row row = rowIterator.next(); 
                     Iterator<Cell> cellIterator = row.cellIterator();
                             while(cellIterator.hasNext()) {
                                     Cell cell = cellIterator.next(); //Fetch CELL
                                     switch(cell.getCellType()) { //Identify CELL type
                                             //you need to add more code here based on
                                             //your requirement / transformations
                                     case Cell.CELL_TYPE_STRING:
                                             //Push the data from Excel to PDF Cell
                                              table_cell=new PdfPCell(new Phrase(cell.getStringCellValue()));
                                              //feel free to move the code below to suit to your needs
                                              my_table.addCell(table_cell);
                                             break;
                                     }
                                     //next line
                             }

             }
             //Finally add the table to PDF document
             iText_xls_2_pdf.add(my_table);                       
             iText_xls_2_pdf.close();                
             //we created our pdf file..
             input_document.close(); //close xls
		}	

		
		
		
		
}
