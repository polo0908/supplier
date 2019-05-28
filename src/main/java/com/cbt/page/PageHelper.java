package com.cbt.page;

import java.io.Serializable;
/**
 * 分页助手
 * @author tb
 * @time 2017.3.28
 */
public class PageHelper implements Serializable {

	private static final long serialVersionUID = 4325106763166124191L;

	boolean isOk;
	
	Long count;
	
	Object obj;

	public boolean isOk() {
		return isOk;
	}

	public void setOk(boolean isOk) {
		this.isOk = isOk;
	}
	
	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

}
