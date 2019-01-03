package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.*;
import entity.*;

/**
 * Servlet implementation class ServletEditPassword
 */
@WebServlet("/ServletEditPassword")
public class ServletEditPassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletEditPassword() {
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
		JSONObject jsonObject=new JSONObject();
		int jsonNum=0;
		String userid=(String)request.getParameter("userid");
		String password=(String)request.getParameter("password");
		String newPassword=(String)request.getParameter("newPassword");
		Entity table;
		table=new EntityAttributeTable("password","user");
		table.setSqlCondition("userid", userid);
		table.sqlSelect();
		if(table.setObject()) {
			if(table.getValue("password").equals(password)) {
				table.setValue("password", newPassword);
				table.sqlUpdate();
				jsonNum=1;
			}else {
				jsonNum=-1;
			}
		}else {
			jsonNum=0;
		}
		jsonObject.put("num",jsonNum);
		response.getWriter().print(jsonObject);
	}
}
