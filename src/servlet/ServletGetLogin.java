package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ServletLogin
 */
@WebServlet("/ServletGetLogin")
public class ServletGetLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletGetLogin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("servlet>"+this.getClass().getName());
		response.setContentType("text/html;charset=utf-8");//解决中文乱码
		request.setCharacterEncoding("utf-8");//请求解决乱码
		response.setCharacterEncoding("utf-8");//响应解决乱码
		//token method
		String jsonObject=request.getParameter("tokenObjJson");
		/*//session method(change to request.getSession() in other servlet file when use it)
		JSONObject jsonObject=new JSONObject();
		String userid=(String)request.getSession().getAttribute("userid");
		if(userid==null) {
			return;
		}
		User user=new User();
		user.setSqlCondition("userid", userid);
		user.sqlSelect();
		user.setObject();
		jsonObject.put("username",user.getValue("username"));
		jsonObject.put("nname",user.getValue("nname"));
		jsonObject.put("headpic",user.getValue("headpic"));*/
		response.getWriter().print(jsonObject);
	}
}
