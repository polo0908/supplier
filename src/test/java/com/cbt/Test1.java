package com.cbt;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cbt.dao.ClientDrawingsDao;
import com.cbt.dao.ClientOrderDao;
import com.cbt.dao.InvoiceInfoDao;
import com.cbt.dao.PaymentPlanDao;
import com.cbt.dao.ReOrderDao;
import com.cbt.dao.ReOrderDrawingsDao;
import com.cbt.dao.RfqInfoDao;
import com.cbt.dao.UserDao;
import com.cbt.entity.ClientDrawings;
import com.cbt.entity.ClientOrder;
import com.cbt.entity.ClientOrderQuery;
import com.cbt.entity.InvoiceInfo;
import com.cbt.entity.ReOrder;
import com.cbt.entity.ReOrderDrawings;
import com.cbt.entity.RfqInfoQuery;
import com.cbt.entity.User;
import com.cbt.service.ClientDrawingsService;
import com.cbt.service.PaymentPlanService;
import com.cbt.util.Base64;
import com.cbt.util.Base64Encode;
import com.cbt.util.Md5Util;
import com.cbt.util.OperationFileUtil;
import com.cbt.util.SecurityHelper;
import com.cbt.util.UploadAndDownloadPathUtil;

public class Test1 {

	@Test
	
	public void updateById(){
		
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("spring-mybatis.xml");

		
		ReOrderDao reOrderdao = ac.getBean(ReOrderDao.class);
		
		ReOrder reorder = reOrderdao.queryById(28);

		System.out.println(reorder);
	}
	@Test
	
	public void queryById(){
		
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("spring-mybatis.xml");
		
		
		ClientDrawingsDao clientDrawingsDao = ac.getBean(ClientDrawingsDao.class);
		
		List<ClientDrawings> draw = clientDrawingsDao.queryListByOrderId("O201610110001");	
		//ClientDrawings d = clientDrawingsDao.queryById(1);
		
		
        System.out.println(draw);
	}


	@Test
      public void queryByKey(){
		
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("spring-mybatis.xml");
		
		
		ClientDrawingsDao clientDrawingsDao = ac.getBean(ClientDrawingsDao.class);
	

	}	
//	#{item.userId},#{item.oldOrderId},#{item.projectName},
//    #{item.drawingPath},#{item.drawingsName},#{item.requiredTime},#{item.comments},
//    #{item.term},#{item.quantity},#{item.unitPrice},#{item.moldPrice}
	@Test
	public void queryReOrderDrawings(){
		
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("spring-mybatis.xml");
		
		ReOrderDrawingsDao reOrderDrawingsDao = ac.getBean(ReOrderDrawingsDao.class);
		
		List<ReOrderDrawings> list = reOrderDrawingsDao.queryReOrderDrawings(2);
		
		System.out.println(list);
	}	
	
	@Test
	public void insert1() throws ParseException{
		
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("spring-mybatis.xml");
		
		ReOrderDao reOrderDao = ac.getBean(ReOrderDao.class);
		
		ReOrder reOrder = new ReOrder();
		long time = System.currentTimeMillis();
    	String t = "2011-07-09";
//    	java.text.SimpleDateFormat   formatter   =  new   SimpleDateFormat( "yyyy-MM-dd ");
//    	Date   date   =   formatter.parse(t);
//    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//    	String xx = sdf.format(t);
		System.out.println(t);
		reOrder.setRequiredTime(t);
		reOrder.setComments("23432");
		reOrder.setUserid("12342");
	}

	@Test
       public void queryfjksdId(){
    	   ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("spring-mybatis.xml");
    	   ReOrderDao reOrderDao = ac.getBean(ReOrderDao.class);
		   System.out.println(ac);

	}
	
	@Test
	 public void updateDrawings(){
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("spring-mybatis.xml");
		ClientDrawingsDao clientDrawingsDao = ac.getBean(ClientDrawingsDao.class);
//		ClientDrawings clientDrawing = clientDrawingsDao.queryById(4);
//        clientDrawing.setDrawingsName("dfgdg");
//		long time = System.currentTimeMillis();
//    	Date date = new Date(time); 
//    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//    	String t = sdf.format(date);
//    	clientDrawing.setDrawingState("YES");
//        clientDrawingsDao.updateClientDrawings(clientDrawing);
//	    System.out.println(clientDrawing);
	}
	
	
	@Test
	public void ReceiveDataPort(){
	ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("spring-mybatis.xml");
	ClientOrderDao clientOrderDao = ac.getBean(ClientOrderDao.class);
	ClientDrawingsDao clientDrawingDao = ac.getBean(ClientDrawingsDao.class);
	UserDao userDao = ac.getBean(UserDao.class);
	
	List<Object> list = new ArrayList<Object>();  
	 
	Map<Object,Object> map= new HashMap<Object,Object>();   	
//	Map<Object,Object> map3 = {amount=3541.00, createTime=2015-10-25 09:56:01.33, customerId=34, caseStatus=14, saildate=, orderId=SHS11426};
	ClientOrder clientOrder = new ClientOrder();
	clientOrder.setAmount(200.00);
	long time = System.currentTimeMillis();
	Date date = new Date(time); 
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	String t = sdf.format(date);
	clientOrder.setCreateTime(t);
	clientOrder.setOutputTime(t);
	clientOrder.setPoPath("");
//	clientOrder.setInvoicePath("");
	clientOrder.setQcReportPath("");
	clientOrder.setShippingDocPath("");
	clientOrder.setOrderId("440");
	clientOrder.setOrderStatus(2);
	clientOrder.setUserid("dsfsd");
	
//	map.put("userid", "1000");
//	map.put("orderId", "O201610110030");
//	map.put("amount", 200.00);
//	map.put("orderStatus",1);
//	long time = System.currentTimeMillis();
//	Date date = new Date(time); 
//	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//	String t = sdf.format(date);
//	map.put("createTime", t);
//	map.put("outputTime", t);
//	map.put("term", "dshfk");
//	map.put("poPath", "21312");
//	map.put("invoicePath", "21312");
//	map.put("qcReportPath", "21312");
//	map.put("shippingDocPath", "21312");
	list.add(clientOrder);
	ClientOrder clientOrder1 = new ClientOrder();
	clientOrder.setAmount(200.00);
	clientOrder1.setCreateTime(t);
	clientOrder1.setOutputTime(t);
	clientOrder1.setPoPath("");
//	clientOrder1.setInvoicePath("");
	clientOrder1.setQcReportPath("");
	clientOrder1.setShippingDocPath("");
	clientOrder1.setOrderId("476");
	clientOrder1.setOrderStatus(2);
	clientOrder1.setUserid("dsfsd");
//	list.add(clientOrder1);
	System.out.println(list);
//	clientOrderDao.insertClientOrder(clientOrder);
	clientOrderDao.insertClientOrders(list);

	List<Object> list3 = new ArrayList<Object>();
	User u = new User();
	u.setEmail("");
   Random random =new Random();
   String r = random.toString();
   r = Md5Util.encoder(r);
   u.setPwd(r);
   u.setTel("");
   u.setUpdateTime(t);
   u.setUserid("10000");
   u.setUserName("asd");
   list3.add(u);
   System.out.println(list3);
   userDao.insertUser(list3);
   
   
   List<Object> list4 = new ArrayList<Object>();
   ClientDrawings c = new ClientDrawings();
   c.setDrawingsName("dfgfdg");
   c.getDrawingsPath();
   c.setMoldPrice(0.5);
   c.setOrderId("43244532");
   c.setProjectCname("分就开始对");
   c.setProjectName("name");
   c.setQuoteTime(t);
   c.setUnitPrice(4.9);
   c.setUpdateTime(t);
   c.setUserid("10000");
   list4.add(c);
   System.out.println(list4);
   clientDrawingDao.insertClientDrawings(list4);
  
	}
		
	@Test
    public void queryKey(){
		
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("spring-mybatis.xml");
		
		ClientOrderDao clientOrderdao = ac.getBean(ClientOrderDao.class);
		
		ClientOrderQuery clientOrderQuery = new ClientOrderQuery();
		
//		String beginDate = "2014-10-25 09:56:01";
//		String endDate = "2016-11-25 09:57:14";
		clientOrderQuery.setUserName("10086");
//		clientOrderQuery.setBeginDate(beginDate);
//		clientOrderQuery.setEndDate(endDate);
//		clientOrderQuery.setUserid("10086");
		clientOrderQuery.setStart(0);
		clientOrderQuery.setPageSize(10);
		int a = clientOrderdao.totalAmount(clientOrderQuery);
		System.out.println(a);
		List<ClientOrderQuery> list = clientOrderdao.queryListByDate(clientOrderQuery);
		System.out.println(list);
		
	}
	
	
	@Test
    public void queryRfqInfo(){
		
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("spring-mybatis.xml");
		
        RfqInfoDao rfqInfoDao = ac.getBean(RfqInfoDao.class);
        
        RfqInfoQuery rq = new RfqInfoQuery();
//        rq.setUserName("dianxin");
        rq.setStart(0);
        rq.setPageSize(20);
        int a = rfqInfoDao.totalAmount(rq);
        System.out.println(a);
        List<RfqInfoQuery> list = rfqInfoDao.queryAllRfqInfo(rq);
        System.out.println(list);
		
	}
	
	@Test
	public void readPor(){
		String oldDrawingPath = UploadAndDownloadPathUtil.getOldDrawingUpLoadAndDownLoadPath();
		String newDrawingPath = UploadAndDownloadPathUtil.getNewDrawingUpLoadAndDownLoadPath();
		String clientOrderPath = UploadAndDownloadPathUtil.getClientOrderUpLoadAndDownLoadPath();
		String compressedPath = UploadAndDownloadPathUtil.getCompressedUpLoadAndDownLoadPath();

		System.out.println("oldDrawingPath: " + oldDrawingPath);
		System.out.println("newDrawingPath: " + newDrawingPath);
		System.out.println("clientOrderPath: " + clientOrderPath);
		System.out.println("compressedPath: " + compressedPath);
	}
	
	@Test
	public void md5(){
	
		String encryptTxt = "";
		  String plainTxt = "userid=6479&pwd=789";
		  try {
		   System.out.println(plainTxt);
		   encryptTxt = SecurityHelper.encrypt("token", plainTxt);
		   System.out.println(encryptTxt);
		  } catch (Exception e) {
		   e.printStackTrace();
		   System.exit(-1);
		  }
		 }
	@Test
	public void md51(){
		
		String encryptTxt = "lKCNof7Ltug=kP1QOPCao/xj7F8TBl53mWsCp5QYOQ3X";
		try {

			String plainTxt = SecurityHelper.decrypt("token", encryptTxt);
			System.out.println(encryptTxt);
			System.out.println(plainTxt);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	@Test
    public void deletePayment(){
		
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("spring-mybatis.xml");
		
        PaymentPlanService paymentPlanDao = ac.getBean(PaymentPlanService.class);
        
        paymentPlanDao.deleteByInvoiceId("sh10010-3");
	}
	 
    @Test
    public void encode() throws UnsupportedEncodingException{
    	String token = "456@163.com&031BCD1F51DACB9DCC5C2B0B00586BC5";
 

    	String base64 = Base64Encode.getBase64(token);
    	System.out.println(base64);
    	String fromBase64 = Base64Encode.getFromBase64(base64);
    	System.out.println(fromBase64);
    }

    
    
	@Test
    public void queryByOrder(){
 	   ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("spring-mybatis.xml");
 	   InvoiceInfoDao invoiceInfoDao = ac.getBean(InvoiceInfoDao.class);
// 	   List<InvoiceInfo> list = invoiceInfoDao.queryInvoiceByOrderId("SHS03403");
//	   System.out.println(ac);

	}
	
}
