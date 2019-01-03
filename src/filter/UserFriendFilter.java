package filter;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import entity.EntityAttributeTable;
import io.jsonwebtoken.Claims;
import javaBean.TokenObject;
import token.CheckResult;
import token.TokenMgr;
import util.FilterResponse;

@WebFilter(filterName = "UserFriendFilter", 
urlPatterns = {"/ServletChatFriendName","/ServletChatHistory"}, 
initParams = {
		@WebInitParam(name = "encoding", value = "utf-8")
})
public class UserFriendFilter implements Filter  {
	private FilterConfig config;
	@Override
	public void init(FilterConfig config) throws ServletException {
		System.out.println("filter>"+this.getClass().getName()+" init");
		this.config = config;
	}
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws java.io.IOException, ServletException {
		//System.out.println("filter>"+this.getClass().getName()+" doFilter");
		// 获取配置参数
		String encoding = config.getInitParameter("encoding");
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		// 设置请求的字符集（post请求方式有效）
		request.setCharacterEncoding(encoding);
		//System.out.println("#"+request.getRequestURL().toString());
		//check is the hdid belong to userid
		FilterResponse filterResponse=new FilterResponse(request,response,this.getClass().getName());
		String userid=request.getParameter("userid");
		//if userid in token
		if(userid==null) {
			String token=request.getParameter("token");
			//token not exit
			if (token!= null&&!token.equals("")&&userid==null) {
				CheckResult checkResult;
				Claims claims;
				checkResult = TokenMgr.validateJWT(token);
				if (checkResult.isSuccess()) {
				    claims = checkResult.getClaims();
				    String tokenObjJson=claims.getSubject();
				    Gson gson = new Gson();
				    TokenObject tokenObj=gson.fromJson(tokenObjJson,TokenObject.class);
				    userid=tokenObj.getUserid();
				}
			}
		}
		String hyid=request.getParameter("hyid");
		String[] attribute= {"userid","hyid","flag"};
		String tableName="hyv";
		EntityAttributeTable table=new EntityAttributeTable(attribute,tableName);
		table.setSqlCondition("userid", userid);
		table.setSqlCondition("hyid", hyid);
		table.sqlSelect();
		//System.out.println("#"+userid+"#"+hdid);
		boolean isMatch=table.setObject();
		if(isMatch) {
			isMatch=table.getValue("flag").equals("同意");
		}
		if(isMatch) {
			chain.doFilter(request, response);
		}else {
			filterResponse.response("not access");
		}
	}
		
		
	@Override
	public void destroy( ){
		System.out.println("filter>"+this.getClass().getName()+" destory");
		//在 Filter 实例被 Web 容器从服务移除之前调用
		this.config =null;
	}
}
