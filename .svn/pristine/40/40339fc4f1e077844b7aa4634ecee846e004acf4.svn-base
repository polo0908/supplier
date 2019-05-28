package com.cbt.enums;
/**
 * 销售发送给客户日志类型
 * @author polo
 *
 */
public enum MessageTypeEnum {

	INVITATION_LOG(0,"邀请登录 "),

	DELIVERY_LOG(1,"交期提醒"),

	PO_LOG(2,"po上传"),

	QC_LOG(3,"qc上传"),
	
	SHIPPING_LOG(4,"shipping上传"),	
	
	WEEKLY_REPORT_LOG(5,"周报上传"),
	
	INVOICE_LOG(6,"发票上传");


	private int code;
	public int getCode() {
		return code;
	}

	public String getValue() {
		return value;
	}

	private String value;
	
	MessageTypeEnum(int code, String value){
		this.code = code;
		this.value = value;
	}
	
	public static MessageTypeEnum getEnum(int code) {
		for(MessageTypeEnum sourceEnum:  MessageTypeEnum.values()) {
	    	if(sourceEnum.getCode() == code) return sourceEnum;
	    }
		return null;
	}
	
	
	public static MessageTypeEnum getGen(String gen) {
		for(MessageTypeEnum sourceEnum :  MessageTypeEnum.values()) {
			if(gen.equals(sourceEnum.getValue())) return sourceEnum;
		}
		return null;
	}
}
