package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import entity.Entity;
import entity.EntityAttributeTable;
import entity.User;
import util.SetCertificate;

/**
 * Servlet implementation class ServletEditPersonalInfo
 */
@WebServlet("/ServletEditPersonalInfo")
public class ServletEditPersonalInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletEditPersonalInfo() {
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
		String action=(String)request.getParameter("action");
		Entity table;
		if(action.equals("update")) {
			String[] attribute={"nname","name","email","adress","job","area"};
			table=new EntityAttributeTable(attribute,"user");
			for(int i=0;i<attribute.length;i++) {
				table.setValue(attribute[i], (String)request.getParameter(attribute[i]));
			}
			table.setSqlCondition("userid", userid);
			table.sqlUpdate();
			//update token
			User user=new User();
			user.setSqlCondition("userid", userid);
			user.sqlSelect();
			if(user.setObject()) {
				SetCertificate.setCertificate(request,response,user);
				jsonNum=1;
			}
		}else {
			table=new User();
			table.setSqlCondition("userid", userid);
			table.sqlSelect();
			if(table.setObject()) {
				jsonObject=table.toJsonObject();
				jsonNum=1;
			}else {
				jsonNum=0;
			}
		}
		jsonObject.put("num",jsonNum);
		response.getWriter().print(jsonObject);
	}
}
