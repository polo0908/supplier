package com.cbt.util;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializeWriter;

/**
 * Json 工具类
 */
public class JsonUtil {
	/**
	 * 对象转化为json字符串
	 * @param object 对象
	 * @param strings 过滤的属性
	 * @return String
	 */
	public static String objectToJsonString(Object object, final String ...strings) {
		PropertyFilter filter = new PropertyFilter() {
			public boolean apply(Object source, String name, Object value) {
				for(String s : strings){  
		            if(s.equals(name)){
						return false;
					}
			    }  
				return true;
			}
		};
		SerializeWriter sw = new SerializeWriter();
		JSONSerializer serializer = new JSONSerializer(sw);
		serializer.getPropertyFilters().add(filter);
		serializer.write(object);
		return sw.toString();
	}
	/**
	 * json字符串转化为对象
	 * @param json json字符串
	 * @param clazz 对象类型
	 * @return <T> T
	 */
	public static <T> T jsonToObject(String json, Class<T> clazz){
		return JSONObject.parseObject(json, clazz);
	}
	/**
	 * json字符串转化为对象列表
	 * @param json json字符串
	 * @param clazz 对象类型
	 * @return <T> List<T>
	 */
	public static <T> List<T> jsonToObjectList(String json,Class<T> clazz){
		return JSONObject.parseArray(json, clazz);
	}
}
