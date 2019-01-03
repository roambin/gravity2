package util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import entity.User;
import javaBean.TokenObject;
import token.Constant;
import token.TokenMgr;

public class SetCertificate {
	public static void setCertificate(HttpServletRequest request, HttpServletResponse response,User user) {
		//token method
		Gson gson = new Gson();
		TokenObject tokenObj=new TokenObject(user.getValue("userid"),user.getValue("username"),user.getValue("nname"),user.getValue("headpic"));
		String tokenObjJson=gson.toJson(tokenObj);
		String token = TokenMgr.createJWT(Constant.JWT_ID, tokenObjJson, Constant.JWT_TTL);
        Cookie cookie=new Cookie("token",token);//create cookie 
        cookie.setMaxAge(86400);//set servive time(sec)
        response.addCookie(cookie);//save to client
		/*//session method(change to request.getSession() in other servlet file when use it)
		request.getSession().setAttribute("userid",user.getValue("userid")); 
		request.getSession().setMaxInactiveInterval(600);*/
	}
}
