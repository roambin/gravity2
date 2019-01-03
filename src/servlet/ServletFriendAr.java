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
 * Servlet implementation class ServletFriendAr
 */
@WebServlet("/ServletFriendAr")
public class ServletFriendAr extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletFriendAr() {
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
		String flag=(String)request.getParameter("flag");
		JSONObject jsonObject=new JSONObject();
		table=new EntityAttributeTable(attribute,"hy");
		table.setValue("userid", hyid);
		table.setValue("hyid", userid);
		table.setValue("flag", flag);
		table.setSqlCondition("userid", hyid);
		table.setSqlCondition("hyid", userid);
		table.sqlUpdate();
		//System.out.println(selnum+"#"+jsonArray);
		jsonObject.put("text",flag);
		jsonObject.put("num","0");
		response.getWriter().print(jsonObject);
	}
}
