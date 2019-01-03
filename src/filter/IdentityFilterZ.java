package filter;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.TokenCheck;

@WebFilter(filterName = "IdentityFilter", 
urlPatterns = "/*", 
initParams = {
		@WebInitParam(name = "encoding", value = "utf-8")
})
public class IdentityFilterZ implements Filter  {
	private FilterConfig config;
	@Override
	public void init(FilterConfig config) throws ServletException {
		System.out.println("filter>"+this.getClass().getName()+" init");
		this.config = config;
	}
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws java.io.IOException, ServletException {
		//System.out.println("filter>"+this.getClass().getName()+" doFilter");
		boolean isPass=false;
		String[] protect= {};
		String[] servlet= {"ServletSelectActivityNoid","ServletLogin","ServletRegister","ServletEditPersonalInfoHeadpic","ServletEditActivityPic","WebSocketChat"};
		String[] type= {"html","js","css","jpg","png","gif","tiff","mpeg","mpg","txt","svg","eps","jsp","jpeg"};
		// 获取配置参数
		String encoding = config.getInitParameter("encoding");
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		// 设置请求的字符集（post请求方式有效）
		request.setCharacterEncoding(encoding);
		
		// 不带http://域名:端口的地址
		String uri = request.getRequestURI();
		//deal request
		int indexPoint=uri.lastIndexOf("/");
		String source=uri.substring(indexPoint+1);
		int indexVirgule=source.lastIndexOf(".");
		//protect source check
		if(indexVirgule!=-1) {
			String sourceType=source.substring(indexVirgule+1);
			for(String iterator:protect) {
				if(iterator.equalsIgnoreCase(sourceType)) {
					response.sendRedirect("notfound.html");
		            return;
				}
			}
		}
		//requests which not need login
		if(indexVirgule==-1) {
			for(String iterator:servlet) {
				if(iterator.equalsIgnoreCase(source)) {
					isPass=true;
					break;
				}
			}
		}else {
			String sourceType=source.substring(indexVirgule+1);
			for(String iterator:type) {
				if(iterator.equalsIgnoreCase(sourceType)) {
					isPass=true;
					break;
				}
			}
		}
		//System.out.println("#"+request.getRequestURL().toString()+"#"+isPass+"#"+indexVirgule);
		if (isPass==true) {
			chain.doFilter(request, response);
			return;
		}
		TokenCheck.filterCheck(chain,request,response,this);
	}
	@Override
	public void destroy( ){
		System.out.println("filter>"+this.getClass().getName()+" destory");
		//在 Filter 实例被 Web 容器从服务移除之前调用
		this.config =null;
	}
}
