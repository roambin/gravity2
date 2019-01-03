package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import entity.User;
import util.SetCertificate;

/**
 * Servlet implementation class ServletLogin
 */
@WebServlet("/ServletLogin")
public class ServletLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletLogin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
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
		String username=(String)request.getParameter("usernamedr");
		String password=(String)request.getParameter("passworddr");
		//System.out.println(username+"#"+password);
		if(username==null||password==null) {
			response.getWriter().print("请输入用户名与密码");
			return;
		}
		if(username.equals("")||password.equals("")) {
			response.getWriter().print("请输入用户名与密码");
			return;
		}
		User user=new User();
		user.setSqlCondition("username", username);
		user.sqlSelect();
		if(!user.setObject()) {
			response.getWriter().print("用户名不存在或密码错误！");
			return;
		}
		if(user.getValue("username").equals(username)&&user.getValue("password").equals(password)) {
			SetCertificate.setCertificate(request,response,user);//set user's token
			response.getWriter().print("1");
		}else {
			response.getWriter().print("用户名不存在或密码错误！");
		}
	}
}
