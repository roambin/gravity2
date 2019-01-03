package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;

import entity.EntityAttributeTable;
import io.jsonwebtoken.Claims;
import javaBean.TokenObject;
import token.CheckResult;
import token.TokenMgr;

public class WebSocketChatUtil {
	public static String onOpenCheck(URI uri){
		UriUtil uriUtil=new UriUtil(uri);
        String token=uriUtil.getParameter("token");
        String hyid=uriUtil.getParameter("hyid");
		CheckResult checkResult;
		Claims claims;
		//token not exit
		if (token == null||token.equals("")) {
			return null;
		}
		// 验证JWT的签名，返回CheckResult对象
		checkResult = TokenMgr.validateJWT(token);
		if (checkResult.isSuccess()) {
		    claims = checkResult.getClaims();
		    String tokenObjJson=claims.getSubject();
		    //json to object
		    Gson gson = new Gson();
		    TokenObject tokenObj=gson.fromJson(tokenObjJson,TokenObject.class);
		    //get userid
		    String userid=tokenObj.getUserid();
		    EntityAttributeTable table=new EntityAttributeTable("hyid","hyv");
		    table.setSqlCondition("userid", userid);
		    table.setSqlCondition("hyid", hyid);
		    table.setSqlCondition("flag", "同意");
		    table.sqlSelect();//System.out.println("#"+uri);
		    if(table.isHasNextResult()) {
		    		return userid;
		    }
		}
		return null;
	}
	
	public static String getAttribute(String userid,String attribute){
		EntityAttributeTable table=new EntityAttributeTable(attribute,"user");
		table.setSqlCondition("userid", userid);
		table.sqlSelect();
		if(table.setObject()) {
			return table.getValue(attribute);
		}
		return null;
	}
	
	public static String addMessage(String message,String userid,String hyid,String userNname) {
		//set path
		String path=null;
		String prepPath=new Object() {}.getClass().getClassLoader().getResource(File.separator+".."+File.separator+"..").getPath();
		try {
			prepPath=URLDecoder.decode(prepPath, "utf-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		if(userid.compareTo(hyid)<0) {
			path=prepPath+"chatRecord"+File.separator+userid+"_"+hyid+".txt";
		}else {
			path=prepPath+"chatRecord"+File.separator+hyid+"_"+userid+".txt";
		}
		//set message
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");//设置日期格式
		String date = dateFormat.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
		message=date+"  "+userNname+":\n"+message+"\n\n";
		//file action
		File file = new File(path); 
        try {
	    		if (!file.exists()) {   
	    			file.createNewFile();
	    		}
	    		FileWriter fw = new FileWriter(file,true);
	    		BufferedWriter bw = new BufferedWriter(fw);
	    		if(file.length()+message.length()>1*1024*1024) {
		    		BufferedReader br = new BufferedReader(new FileReader(file));
		    		List<String> list = new ArrayList<String>();
		    		int num=0;
		    		String temp;
		    		while( (temp=br.readLine())!=null){
			    		num++;
			    		if(num>6291) {
			    			list.add(temp);
			    		}
			    	}
		    		br.close();
		    		BufferedWriter bwDel = new BufferedWriter(new FileWriter(file,false));
		    		bwDel.write("<<系统消息：你们俩话好多。。。之前的记录装不下了，被我删掉咯～>>\n\n");
		    		for(int i=0;i<list.size();i++ ){
			    		bwDel.write(list.get(i).toString());
			    		bwDel.newLine();
			    	}
		    		bwDel.flush();
		    		bwDel.close();
            }
			bw.write(message);
			bw.flush();
			bw.close();
			fw.close();
		} catch (IOException e) {
			System.out.println("false> WebSocketChatUtil/addMessage:");
			e.printStackTrace();
			return null;
		}
		return message;
	}
}
