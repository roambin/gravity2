package util;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import io.jsonwebtoken.Claims;
import javaBean.TokenObject;
import token.CheckResult;
import token.Constant;
import token.TokenMgr;

public class TokenCheck {
	public static boolean filterCheck(FilterChain chain,HttpServletRequest request,HttpServletResponse response,Object perThis) throws IOException, ServletException {
		//filter response util
		FilterResponse filterResponse=new FilterResponse(request,response,perThis.getClass().getName());
		//requests which need login
		String token=request.getParameter("token");//System.out.println("#"+token);
		CheckResult checkResult;
		Claims claims;
		//token not exit
		if (token == null||token.equals("")) {
			filterResponse.response("not access");
		          return false;
		    }
		// 验证JWT的签名，返回CheckResult对象
		checkResult = TokenMgr.validateJWT(token);
		if (checkResult.isSuccess()) {
		    claims = checkResult.getClaims();
		    String tokenObjJson=claims.getSubject();
		    //json to object
		    Gson gson = new Gson();
		    TokenObject tokenObj=gson.fromJson(tokenObjJson,TokenObject.class);
		    //a object reload HttpServletRequestWrapper for add parameter in request
		    RequestParameterWrapper requestParameterWrapper = new RequestParameterWrapper(request);
		    requestParameterWrapper.addParameter("userid",tokenObj.getUserid());
		    requestParameterWrapper.addParameter("tokenObjJson",tokenObjJson);//requst object in token with json form
		    chain.doFilter(requestParameterWrapper, response);
		    return true;
		}else {
			switch (checkResult.getErrCode()) {
				// 签名过期，返回过期提示码
		        case Constant.JWT_ERRCODE_EXPIRE:
		        		filterResponse.response("time out");
		        		break;
		        // 签名验证不通过
		        case Constant.JWT_ERRCODE_FAIL:
		        		filterResponse.response("not access");
		        		break;
		        default:
		        		filterResponse.response("not access");
		        		break;
			}
			return false;
		}
	}
	public static String fileUploadCheck(HttpServletRequest request,HttpServletResponse response,Object perThis,String token) throws IOException, ServletException {
		//filter response util
		FilterResponse filterResponse=new FilterResponse(request,response,perThis.getClass().getName());
		//requests which need login
		CheckResult checkResult;
		Claims claims;
		//token not exit
		if (token == null||token.equals("")) {
			filterResponse.response("not access");
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
		    String userid=tokenObj.getUserid();
		    return userid;
		}else {
			switch (checkResult.getErrCode()) {
				// 签名过期，返回过期提示码
		        case Constant.JWT_ERRCODE_EXPIRE:
		        		filterResponse.response("time out");
		        		break;
		        // 签名验证不通过
		        case Constant.JWT_ERRCODE_FAIL:
		        		filterResponse.response("not access");
		        		break;
		        default:
		        		filterResponse.response("not access");
		        		break;
			}
			return null;
		}
	}
}
