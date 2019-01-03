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
 * Servlet implementation class ServletA
 */
@WebServlet("/ServletFriendA")
public class ServletFriendA extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletFriendA() {
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
		Entity table;
		String[] attribute= {"userid","hyid","flag"};
		String userid=(String)request.getParameter("userid");
		String hyid=(String)request.getParameter("hyid");
		String flag="待审";
		JSONObject jsonObject=new JSONObject();
		table=new EntityAttributeTable(attribute,"hy");
		table.setValue("userid", userid);
		table.setValue("hyid", hyid);
		table.setValue("flag", flag);
		table.sqlInsert();
		//System.out.println(selnum+"#"+jsonArray);
		jsonObject.put("text","已发送好友请求");
		jsonObject.put("num","1");
		response.getWriter().print(jsonObject);
	}
}
