package com.cbt.enums;
/**
 * 选择pdf模板问题
 * @author polo
 *
 */
public enum QuotePdfTypeEnum {

	NORMAL(0,"normal"),//普通

	ROTATE(1,"rotate"),//滚塑

	UPTAKE(2,"uptake"),//吸塑

	PRESS(3,"press"),//钣金冲压 
		
	ALUMINUM(4,"aluminum"),//铝
	
	FORGE(5,"forge"), //锻造
	
	LASER(6,"laser"), //激光
	
	MACHINING(7,"machining"), //机加工
	
	INJECTATE(8,"injextate"),//注塑
	
	BLOW(9,"blow"),  //吹塑
	
	EXTRUSION(10,"extrusion"),//挤塑
	
	MELTMOLDCASTING(11,"meltmoldcasting"),//熔模铸造
	
    SANDCASTING(12,"sandcasting"),//砂铸
	
	NEWPRESS(13,"newpress");//压铸


	private int code;
	public int getCode() {
		return code;
	}

	public String getValue() {
		return value;
	}

	private String value;
	
	QuotePdfTypeEnum(int code, String value){
		this.code = code;
		this.value = value;
	}
	
	public static QuotePdfTypeEnum getEnum(int code) {
		for(QuotePdfTypeEnum sourceEnum:  QuotePdfTypeEnum.values()) {
	    	if(sourceEnum.getCode() == code) return sourceEnum;
	    }
		return null;
	}
	
	
	public static QuotePdfTypeEnum getGen(String gen) {
		for(QuotePdfTypeEnum sourceEnum :  QuotePdfTypeEnum.values()) {
			if(gen.equals(sourceEnum.getValue())) return sourceEnum;
		}
		return null;
	}
}
