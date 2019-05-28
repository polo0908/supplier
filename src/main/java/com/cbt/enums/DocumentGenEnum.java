package com.cbt.enums;
/**
 * 文档类型后缀
 * @author polo
 *
 */
public enum DocumentGenEnum {

	XLS(0,"xls"),

	XLSX(1,"xlsx"),

	DOC(2,"doc"),

	DOCX(3,"docx"),
	
	PDF(4,"pdfx"),	
	
	PPT(5,"ppt"),
	
	PPTX(6,"pptx");


	private int code;
	public int getCode() {
		return code;
	}

	public String getValue() {
		return value;
	}

	private String value;
	
	DocumentGenEnum(int code, String value){
		this.code = code;
		this.value = value;
	}
	
	public static DocumentGenEnum getEnum(int code) {
		for(DocumentGenEnum sourceEnum:  DocumentGenEnum.values()) {
	    	if(sourceEnum.getCode() == code) return sourceEnum;
	    }
		return null;
	}
	
	
	public static DocumentGenEnum getGen(String gen) {
		for(DocumentGenEnum sourceEnum :  DocumentGenEnum.values()) {
			if(gen.equals(sourceEnum.getValue())) return sourceEnum;
		}
		return null;
	}
}
