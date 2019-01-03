package util;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FilterResponse {
	private HttpServletRequest request;
	private HttpServletResponse response;
	private String className;
	public FilterResponse() {
		
	}
	public FilterResponse(HttpServletRequest request,HttpServletResponse response,String className) {
		this.request=request;
		this.response=response;
		this.className=className;
	}
	//is ajax request
	public boolean isAjax() {
		String xReq = request.getHeader("x-requested-with");
		if (xReq!=null && "XMLHttpRequest".equalsIgnoreCase(xReq)) {
			return true;// 是ajax异步请求
		}
		return false;
	}
	//filter response
	public void response(String resText) throws IOException {
		System.out.println("filter>"+className+" doFilter:was intercepted("+resText+")\n      >source:"+request.getRequestURL().toString());
		if(this.isAjax()) {
				response.getWriter().print("filter:"+resText);
		}else {
			response.sendRedirect("notfound.html");
		}
	}
}
