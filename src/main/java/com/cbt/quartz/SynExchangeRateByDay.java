package com.cbt.quartz;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component("synExchangeRateByDay")  
public class SynExchangeRateByDay {

	    @Scheduled(cron = "0 0 12 * * ?")  
//	    @Scheduled(cron="0/60 * *  * * ? ")
	    public void job1() {  
	    	MyTimerTask task = new MyTimerTask();
	    	task.run();
	        System.out.println("任务进行中。。。");  
	    }  
}
