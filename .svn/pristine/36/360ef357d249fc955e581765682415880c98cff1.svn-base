package com.cbt.print;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;




import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFSimpleShape;
import org.apache.poi.hssf.usermodel.HSSFTextbox;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;

import com.cbt.entity.PicGps;
import com.cbt.entity.QuotationBean;
import com.cbt.entity.QuotationProcessInfo;
import com.cbt.entity.QuotationProductionBean;
import com.cbt.entity.QuotationProductionPriceBean;
import com.cbt.util.DateFormat;
import com.cbt.util.OperationFileUtil;
import com.cbt.util.PicturePdfUtil;
import com.cbt.util.UploadAndDownloadPathUtil;


/**
 * @Description: 根据模板文件打印，输出报价模板
 * @Author: polo
 * @CreateDate: 2017/12/28
 */

public class QuotationMeltmoldPrintTemplate {

	private static File tempPath;

	/**
	 * pdf打印,使用excel编辑，生成pdf
	 * 
	 * @param path
	 * @throws Exception
	 */
	public static String printExcel(HttpServletRequest request, String path,
			QuotationBean quotationBean, List<QuotationProductionBean> list)
			throws Exception {

		int tl = 0;
		if (list.size() != 0) {
			for (QuotationProductionBean quotationProductionBean : list) {
				List<QuotationProductionPriceBean> priceBeanLists = quotationProductionBean
						.getQuotationProductionPriceBeanList();
				int pr_tl = priceBeanLists.size();
				tl += pr_tl;
			}
		}
		String currency = quotationBean.getCurrency();
		if ("USD".equals(currency)) {
			currency = "$";
		} else if ("CNY".equals(currency)) {
			currency = "¥";
		} else if ("EUR".equals(currency)) {
			currency = "€";
		} else if ("GBP".equals(currency)) {
			currency = "£";
		}

		/*
		 * 打开模板，复制sheet，另存
		 */
       File file = null;
       if(quotationBean.getProcessStatus() == 0 && quotationBean.getWeightStatus() == 1){
    	   file = new File(path+"pdf"+File.separator+"quotation_meltmoldcasting_one.xls");
       }else if(quotationBean.getProcessStatus() == 1 && quotationBean.getWeightStatus() == 0){
    	   file = new File(path+"pdf"+File.separator+"quotation_meltmoldcasting_one.xls");
       }else if(quotationBean.getProcessStatus() == 0 && quotationBean.getWeightStatus() == 0){
    	   file = new File(path+"pdf"+File.separator+"quotation_meltmoldcasting_none.xls");
       }else{
    	   file = new File(path+"pdf"+File.separator+"quotation_meltmoldcasting.xls");
       }
		

		FileInputStream fi = new FileInputStream(file);
		HSSFWorkbook wb = new HSSFWorkbook(fi);
		wb.cloneSheet(0); // 复制工作簿
		wb.setSheetName(1, "quotation for product"); // 设置工作簿名称

		// 设置相同内容
		int rowNo = 0;
		int colNo = 0;
		Row nRow = null;
		Cell nCell = null;

		HSSFSheet sheet = wb.getSheetAt(1); // 定位到当前工作表
		sheet.setForceFormulaRecalculation(true); // 强制公式自动计算，利用模板时，模板中的公式不会因值发生变化而自动计算。

		nRow = sheet.getRow(rowNo);
		nCell = nRow.getCell(5);
		// sheet.addMergedRegion(new CellRangeAddress(0, 2,(short)3,(short)5));
		nCell.setCellValue("Quotation:" + quotationBean.getProjectId());

		rowNo++;
		nRow = sheet.getRow(rowNo++);
		nCell = nRow.getCell(7);
		nCell.setCellValue("Contact:" + quotationBean.getSaleName());

		nRow = sheet.getRow(rowNo++);
		nCell = nRow.getCell(7);
		nCell.setCellValue(quotationBean.getQuoterEmail());
		nRow = sheet.getRow(rowNo++);
		nCell = nRow.getCell(7);
		nCell.setCellValue("TEL:" + quotationBean.getQuoterTel());

		nRow = sheet.getRow(rowNo++);
		nCell = nRow.getCell(1);
		nCell.setCellValue(quotationBean.getCustomerName());
		nCell = nRow.getCell(5);
		nCell.setCellValue(quotationBean.getQuotationDate());
		nCell = nRow.getCell(7);
		nCell.setCellValue("valid for" + quotationBean.getQuotationValidity()
				+ "days");

		rowNo = 7;
		//添加产品数据表
		PicturePdfUtil.addProduct(sheet, rowNo, quotationBean, list, currency, tl, nRow, nCell, wb);

		// default
		// //声明一个画图的顶级管理器

		HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
		/**
		 * dx1：起始单元格的x偏移量，如例子中的255表示直线起始位置距A1单元格左侧的距离；
		 * dy1：起始单元格的y偏移量，如例子中的125表示直线起始位置距A1单元格上侧的距离；
		 * dx2：终止单元格的x偏移量，如例子中的1023表示直线起始位置距C3单元格左侧的距离；
		 * dy2：终止单元格的y偏移量，如例子中的150表示直线起始位置距C3单元格上侧的距离； colFrom：起始单元格列序号，从0开始计算；
		 * rowFrom：起始单元格行序号，从0开始计算，如例子中col1=0,row1=0就表示起始单元格为A1；
		 * colTo：终止单元格列序号，从0开始计算；
		 * rowTo：终止单元格行序号，从0开始计算，如例子中col2=2,row2=2就表示起始单元格为C3；
		 */

		//addCheckBox(path, sheet, quotationBean);
		if(StringUtils.isNotBlank(quotationBean.getRemark())){							
			//新建text文本框							
				rowNo = 9+tl;
				for (int k = 0; k < 8; k++) {
					HSSFRow row = sheet.getRow(rowNo+k);
					nCell = row.getCell(0);
					nCell.setCellValue("");
				}
				
				nRow = sheet.getRow(rowNo);
				nCell = nRow.getCell(0);
				HSSFCellStyle cellStyle = wb.createCellStyle(); 
				cellStyle.setVerticalAlignment(cellStyle.VERTICAL_CENTER);//垂直  
				cellStyle.setWrapText(true);     
				nCell.setCellStyle(cellStyle);
				nCell.setCellValue(quotationBean.getRemark());
				sheet.addMergedRegion(new CellRangeAddress(rowNo, rowNo + 8, (short) 0, (short) 7));							
		}

		/*
		 * 最后的图片和text
		 */
		if (StringUtils.isNotBlank(quotationBean.getImgNames())){
			addTextAndPic(path, sheet,tl, quotationBean, patriarch,wb);
		}else{
			addStaticPic(path, sheet, tl, quotationBean);
		}

		String pic_path = path + File.separator + "pdf" + File.separator
				+ "9001.png";
	
		PicGps pic9001 = new PicGps(9+tl, 6, 11+tl, 7, 400, 20,
				400, 20);
		PicturePdfUtil.setPictures(pic_path, sheet, pic9001);
		

		String paths = UploadAndDownloadPathUtil.getQuotationPath()
				+ quotationBean.getFactoryId();

		tempPath = new File(paths);
		// deleteFile(tempPath);
		if (!tempPath.exists() || !tempPath.isDirectory()) {
			tempPath.mkdir(); // 如果不存在，则创建该文件夹
		}
		wb.removeSheetAt(0); // 删除模板sheet
		FileOutputStream fs = new FileOutputStream(paths + File.separator + quotationBean.getProjectId() + "-" + DateFormat.currentDate().replace("-", ".") + ".xls", false);
		wb.write(fs);
		fs.close();
		 PicturePdfUtil.office2PDF(paths + File.separator + quotationBean.getProjectId() + "-" + DateFormat.currentDate().replace("-", ".") + ".xls", 
				 paths + File.separator + quotationBean.getProjectId()+ "-"+DateFormat.currentDate().replace("-", ".") + ".pdf");

		return paths + File.separator + quotationBean.getProjectId() + "-" + DateFormat.currentDate().replace("-", ".") + ".pdf";
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



	// private static String openOfficePath = "/opt/openoffice4";

	

	// 删除文件和目录
	private static void clearFiles(String workspaceRootPath) {
		File file = new File(workspaceRootPath);
		if (file.exists()) {
			deleteFile(file);
		}
	}

	private static void deleteFile(File file) {
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				deleteFile(files[i]);
			}
		}
		file.delete();
	}

	public static void delFolder(String folderPath) {
		try {
			boolean delAllFile = delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // 删除空文件夹
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 删除指定文件夹下所有文件
	// param path 文件夹完整绝对路径
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
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);// 再删除空文件夹
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * excel插入行数据
	 * 
	 * @param wb
	 * @param sheet
	 * @param starRow
	 * @param rows
	 */
	public static void insertRow(HSSFWorkbook wb, HSSFSheet sheet, int starRow,
			int rows) {

		sheet.shiftRows(starRow + 1, sheet.getLastRowNum(), rows, true, false);

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

			for (m = sourceRow.getFirstCellNum(); m < sourceRow
					.getLastCellNum(); m++) {

				sourceCell = sourceRow.getCell(m);
				targetCell = targetRow.createCell(m);
				targetCell.setCellStyle(sourceCell.getCellStyle());
				targetCell.setCellType(sourceCell.getCellType());

			}
		}

	}

	

	// 处理图片备注存放 偏移量不同 dx1 = 255; dy1 = 125; dx2 = 200; dy2 = 150;
	public static void setPicture1(String pic, HSSFSheet sheet, int startRow,
			int startCol, int stopRow, int stopCol) throws IOException {
		File imageFile = new File(pic);
		if (imageFile.exists()) {
			InputStream is = new FileInputStream(new File(pic));
			byte[] bytes = IOUtils.toByteArray(is);
			int pictureIdx = sheet.getWorkbook().addPicture(bytes,
					Workbook.PICTURE_TYPE_JPEG); // 扩展名可为.jpg/.jpeg/.png
			is.close();

			HSSFPatriarch drawing = sheet.createDrawingPatriarch(); // Create
																	// the
																	// drawing
																	// patriarch.
																	// This is
																	// the top
																	// level
																	// container
																	// for all
																	// shapes.
			// 前面四个参数是图片偏移量
			HSSFClientAnchor anchor = new HSSFClientAnchor(0, 20, 0, 20,
					(short) startCol, startRow, (short) stopCol, stopRow); // add
																			// a
																			// picture
																			// shape
			anchor.setRow1(startRow); // set position corner of the picture
			anchor.setCol1(startCol);
			anchor.setRow2(stopRow);
			anchor.setCol2(stopCol);

			drawing.createPicture(anchor, pictureIdx);
		}

	}


	

	public static void addStaticPic(String path, HSSFSheet sheet, int tl,
			QuotationBean quotationBean) throws IOException {

		int rownum = 19; //

		PicGps picGps = new PicGps(rownum + tl, 5, rownum +7+ tl, 7, 0, 20,
				800, 20);
		String pic_path = path + "images" + File.separator + "meltmoldcasting"
				+ File.separator + "1.png";
		PicturePdfUtil.setPictures(pic_path, sheet, picGps);
		
		
		

		picGps = new PicGps(rownum+26 + tl, 0, rownum +35+ tl,2, 0, 20,
				0, 20);
		pic_path = path + "images" + File.separator + "meltmoldcasting"
				+ File.separator + "2.png";
		PicturePdfUtil.setPictures(pic_path, sheet, picGps);
		
		
		
		picGps = new PicGps(rownum +26+ tl, 2, rownum +33+ tl,4, 0, 20,
				0, 20);
		pic_path = path + "images" + File.separator + "meltmoldcasting"
				+ File.separator + "3.png";
		PicturePdfUtil.setPictures(pic_path, sheet, picGps);
		
		picGps = new PicGps(rownum +26+ tl, 4, rownum +33+ tl,6, 0, 20,
				0, 20);
		pic_path = path + "images" + File.separator + "meltmoldcasting"
				+ File.separator + "4.png";
		PicturePdfUtil.setPictures(pic_path, sheet, picGps);
		
		picGps = new PicGps(rownum +26+ tl, 6, rownum +33+ tl,8, 0, 20,
				0, 20);
		pic_path = path + "images" + File.separator + "meltmoldcasting"
				+ File.separator + "5.png";
		PicturePdfUtil.setPictures(pic_path, sheet, picGps);
	
		
		picGps = new PicGps(rownum +35+ tl, 0, rownum +41+ tl,2, 0, 20,
				0, 20);
		pic_path = path + "images" + File.separator + "meltmoldcasting"
				+ File.separator + "6.png";
		PicturePdfUtil.setPictures(pic_path, sheet, picGps);
		
		picGps = new PicGps(rownum +35+ tl, 2, rownum +41+ tl,4, 0, 20,
				0, 20);
		pic_path = path + "images" + File.separator + "meltmoldcasting"
				+ File.separator + "7.png";
		PicturePdfUtil.setPictures(pic_path, sheet, picGps);
		
		picGps = new PicGps(rownum +35+ tl, 4, rownum +41+ tl,6, 0, 20,
				0, 20);
		pic_path = path + "images" + File.separator + "meltmoldcasting"
				+ File.separator + "8.png";
		PicturePdfUtil.setPictures(pic_path, sheet, picGps);
		
		picGps = new PicGps(rownum +35+ tl, 6, rownum +41+ tl,8, 0, 20,
				0, 20);
		pic_path = path + "images" + File.separator + "meltmoldcasting"
				+ File.separator + "9.png";
		PicturePdfUtil.setPictures(pic_path, sheet, picGps);
		
		
		
		
	}

	public static void addTextAndPic(String path, HSSFSheet sheet, int tl,
			QuotationBean quotationBean, HSSFPatriarch patriarch,
			HSSFWorkbook wb) throws IOException {
		Cell nCell = null;
		int dx1 = 0, dy1 = 0, dx2 = 0, dy2 = 0;
		int colFrom = 0, rowFrom = 38 + tl, colTo = 7, rowTo =  48+ tl;


		String imgNames = quotationBean.getImgNames();
		String pic_path = "";
		if (StringUtils.isNotBlank(imgNames)) {
			int img_tl = 0;
			String[] split = imgNames.split(",");
			if (!(imgNames == null || "".equals(imgNames))) {
				img_tl = split.length;
			}

			dx1 = 0;
			dy1 = 125;
			dx2 = 100;
			dy2 = 150;

			
            //添加图片
			PicGps picGps = new PicGps(19 + tl, 5, 26+ tl, 7, 0, 20,
					800, 20);
			pic_path = path + "images" + File.separator + "meltmoldcasting"
					+ File.separator + "1.png";
			PicturePdfUtil.setPictures(pic_path, sheet, picGps);
			
			
			// 添加色条
			sheet.addMergedRegion(new CellRangeAddress(37 + tl, 37 + tl,
					(short) 0, (short) 7));

			CellStyle style = wb.createCellStyle();
			style.setFillForegroundColor(IndexedColors.DARK_YELLOW.getIndex());
			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			Row row = sheet.createRow((short) 37 + tl);
			nCell = row.createCell((short) 0);
			nCell.setCellStyle(style);
			

			// 图片4个一行存取，最多8张图片，大于8张取前8张
			if (img_tl > 0 && img_tl <= 4) {
				for (int i = 0; i < img_tl; i++) {
					pic_path = UploadAndDownloadPathUtil.getRemakPicturePath()
							+ quotationBean.getFactoryId() + File.separator
							+ OperationFileUtil.changeZipName(split[i]);
					if (i == 0) {
						colFrom = 0;
						colTo = 2;
					} else if (i == 1) {
						colFrom = 2;
						colTo = 4;
					} else if (i == 2) {
						colFrom = 4;
						colTo = 6;
					} else if (i == 3) {
						colFrom = 6;
						colTo = 8;
					}
					setPicture1(pic_path, sheet, rowFrom, colFrom, rowTo, colTo);

				}
			} else if (img_tl > 4) {
				
      			// 添加色条
      			sheet.addMergedRegion(new CellRangeAddress(48 + tl, 48 + tl,
      					(short) 0, (short) 7));
      			style.setFillForegroundColor(IndexedColors.DARK_YELLOW.getIndex());
      			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
      			Row row1 = sheet.createRow((short) 48 + tl);
      			nCell = row1.createCell((short) 0);
      			nCell.setCellStyle(style); 
				
				for (int i = 0; i < img_tl; i++) {
					pic_path = UploadAndDownloadPathUtil.getRemakPicturePath()
							+ quotationBean.getFactoryId() + File.separator
							+ split[i];

					if (i < 4) {
						rowFrom = 38 + tl;
						rowTo = 48 + tl;
					} else {
						rowFrom = 49 + tl;
						rowTo = 59 + tl;
					}

					
					
					if(i < 8){
						if (i == 0 || i == 4) {
							colFrom = 0;
							colTo = 2;
						} else if (i == 1 || i == 5) {
							colFrom = 2;
							colTo = 4;
						} else if (i == 2 || i == 6) {
							colFrom = 4;
							colTo = 6;
						} else if (i == 3 || i == 7) {
							colFrom = 6;
							colTo = 8;
						}

						setPicture1(pic_path, sheet, rowFrom, colFrom, rowTo, colTo);
					}

				}
			}

		}

	}

	public static void main(String[] args) throws Exception {

		String sourcePath = "G:\\apache-tomcat-7.0.57\\wtpwebapps\\quotation\\f1010\\blowure_casting.xls";
		String destFile = "e:\\1213121.pdf";
		int flag = PicturePdfUtil.office2PDF(sourcePath, destFile);
		if (flag == 1) {
			System.out.println("转化失败");
		} else if (flag == 0) {
			System.out.println("转化成功");
		} else {
			System.out.println("找不到源文件, 或url.properties配置错误");
		}
	}

}
