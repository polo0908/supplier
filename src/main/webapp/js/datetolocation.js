
//对不同国家日期格式化处理
var format = function(stringTime){
var timestamp = Date.parse(new Date(stringTime));	
var d = new Date(timestamp)	
var dateTime = '';
//Invalid Date
if(d != "Invalid Date"){
     dateTime = d.getFullYear() + '-' + ((d.getMonth() + 1)> 9 ? (d.getMonth() + 1) : '0'+(d.getMonth() + 1))
	                  + '-' + (d.getDate() > 9 ? d.getDate() : '0'+ d.getDate()) 
	                  + ' ' + (d.getHours() > 9 ? d.getHours() : '0'+ d.getHours())
	                  + ':' + (d.getMinutes() > 9 ? d.getMinutes() : '0'+ d.getMinutes())
	                  + ':' + (d.getSeconds() > 9 ? d.getSeconds() : '0'+ d.getSeconds());  	
  }else{
	  dateTime = ''; 
  }
   return dateTime;
}