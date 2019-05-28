package com.cbt.enums;
/**
 * 文档类型后缀
 * @author polo
 *
 */
public enum ImgGenEnum {

	BMP(0,"bmp"),

	JPG(1,"jpg"),

	JPEG(2,"jpeg"),

	PNG(3,"png"),
	
	TIFF(4,"tiff"),	
	
	GIF(5,"gif"),
	
	TGA(6,"tga"),
	
	PCX(7,"pcx");


	private int code;
	public int getCode() {
		return code;
	}

	public String getValue() {
		return value;
	}

	private String value;
	
	ImgGenEnum(int code, String value){
		this.code = code;
		this.value = value;
	}
	
	public static ImgGenEnum getEnum(int code) {
		for(ImgGenEnum sourceEnum:  ImgGenEnum.values()) {
	    	if(sourceEnum.getCode() == code) return sourceEnum;
	    }
		return null;
	}
	
	
	public static ImgGenEnum getGen(String gen) {
		for(ImgGenEnum sourceEnum :  ImgGenEnum.values()) {
			if(gen.equalsIgnoreCase(sourceEnum.getValue())) return sourceEnum;
		}
		return null;
	}
}
