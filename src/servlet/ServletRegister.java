package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.*;
import util.SetCertificate;

/**
 * Servlet implementation class ServletLogin
 */
@WebServlet("/ServletRegister")
public class ServletRegister extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletRegister() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
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
		String username=(String)request.getParameter("usernamezc");
		String password=(String)request.getParameter("passwordzc");
		String conpassword=(String)request.getParameter("conpasswordzc");
		if(username==null||password==null||conpassword==null) {
			response.getWriter().print("请输入用户名与密码");
			return;
		}
		if(username.equals("")||password.equals("")||conpassword.equals("")) {
			response.getWriter().print("请输入用户名与密码");
			return;
		}
		if(!password.equals(conpassword)) {
			response.getWriter().print("两次密码不一致");
			return;
		}
		User user=new User();
		user.setSqlCondition("username", username);
		user.sqlSelect();
		if(user.setObject()) {
			response.getWriter().print("用户名已存在，请更换用户名");
		}else {
			EntityAttributeTable userid = new EntityAttributeTable("max(userid)+1","user");
			userid.sqlSelect();
			userid.setObject();
			user.clearObject();
			user.setValue("userid",userid.getValue("max(userid)+1"));
			user.setValue("username",username);
			user.setValue("password",password);
			if(user.sqlInsert()) {
				response.getWriter().print("1");
			}else {
				response.getWriter().print("注册失败，服务器出现问题。。");
			}
			SetCertificate.setCertificate(request,response,user);//set user's token
		}
	}

}
