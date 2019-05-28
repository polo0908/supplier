package com.cbt.quartz;

/**
 * @Header: MQ接收消息
 * 类描述：
 * @author: 王宏杰
 * @date 2017-06-29 上午09:52:42
 */

import java.util.HashMap;
import java.util.Map;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.cbt.util.Client;


public class Receiver {
	private static final Log MQLOG = LogFactory.getLog("mq");

	public static void main(String[] args) {
		Receiver.ReceiverForMQ();
	}

	public static void ReceiverForMQ() {
		ConnectionFactory connectionFactory;
		Connection connection = null;
		Session session;
		Destination destination;
		MessageConsumer consumer;
		Destination destination1;
		MessageConsumer consumer1;
		Destination destination2;
		MessageConsumer consumer2;
		connectionFactory = new ActiveMQConnectionFactory(
				ActiveMQConnection.DEFAULT_USER,
				ActiveMQConnection.DEFAULT_PASSWORD,
				"tcp://192.168.1.62:61616");
		try {
			connection = connectionFactory.createConnection();
			connection.start();
			session = connection.createSession(Boolean.FALSE,
					Session.AUTO_ACKNOWLEDGE);
			// 获取session注意参数值xingbo.xu-queue是一个服务器的queue，须在在ActiveMq的console配置
			destination = session.createQueue("ItemCase");
			consumer = session.createConsumer(destination);
			
			//获取发票
			destination1 = session.createQueue("Invoiceinfo1");
			consumer1 = session.createConsumer(destination1);
			
			//获取图纸、模具
			destination2 = session.createQueue("Mould");
			consumer2 = session.createConsumer(destination2);
			
			
			while (true) {
				// 设置接收者接收消息的时间，定为1s
				TextMessage message = (TextMessage) consumer.receive(3000);
//				ReceiveCurrentDataByHourPort r = new ReceiveCurrentDataByHourPort();
				if (null != message) {
				  String items = message.getText();				  				  
//				  r.importERPOrderByCurrentHour(items);
				  Map<String, String> map = new HashMap<String, String>();
				  map.put("ItemCase", items);
				  Client.sendPost("http://192.168.1.54:8080/supplier/port/importERPOrderByCurrentHour.do", map);
				}else{
					System.out.println("ItemCase没有消息");
		   	    }
				
				
				// 设置接收者接收消息的时间，定为1s
				TextMessage message1 = (TextMessage) consumer1.receive(3000);
				if (null != message1) {
				  String items = message1.getText();				  
				  Map<String, String> map = new HashMap<String, String>();
				  map.put("Invoiceinfo1", items);
				  Client.sendPost("http://192.168.1.54:8080/supplier/port/importInvoiceInfoByCurrentHour.do", map);
				  
				}else{
			 		System.out.println("Invoice没有消息");
		   	    }
				
				
				// 设置接收者接收消息的时间，定为1s
				TextMessage message2 = (TextMessage) consumer2.receive(3000);
				if (null != message2) {
				  String items = message2.getText();				  
				  Map<String, String> map = new HashMap<String, String>();
				  map.put("Mould", items);
				  Client.sendPost("http://192.168.1.54:8080/supplier/port/importDrawingByCurrentHour.do", map);
				  
				}else{
			 		System.out.println("Drawing没有消息");
		   	    }
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != connection) {
					connection.close();
				}
			} catch (Throwable ignore) {
			}
		}
	}

}
