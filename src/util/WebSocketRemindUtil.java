package util;

import java.net.URI;
import com.google.gson.Gson;

import io.jsonwebtoken.Claims;
import javaBean.TokenObject;
import token.CheckResult;
import token.TokenMgr;

public class WebSocketRemindUtil {
	public static String onOpenCheck(URI uri){
		UriUtil uriUtil=new UriUtil(uri);
        String token=uriUtil.getParameter("token");
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
		    	return userid;
		}
		return null;
	}
}
