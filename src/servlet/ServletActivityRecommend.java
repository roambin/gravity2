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
 * Servlet implementation class ServletActivityRecommend
 */
@WebServlet("/ServletActivityRecommend")
public class ServletActivityRecommend extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletActivityRecommend() {
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
		String[] addswitName= {"addswitname","addswitarea","addswitclas"};
		String[] conditionName= {"hdm","place","classify"};
		String[] condition=new String[conditionName.length];
		ActivityRecommend activity;
		String userid=(String)request.getParameter("userid");
		JSONArray jsonArray=new JSONArray();
		int selnum=Integer.parseInt(request.getParameter("selnum"));
		int sellength=Integer.parseInt(request.getParameter("sellength"));
		activity=new ActivityRecommend();
		activity.setSqlCondition("userid", userid);
		for(int i=0;i<condition.length;i++) {
			condition[i]=(String)request.getParameter(addswitName[i]);
			if(condition[i]!=null&&!condition[i].equals("")) {
				activity.setSqlCondition(conditionName[i], condition[i]);
			}
		}
		activity.setSqlLimit(selnum, sellength);
		activity.sqlSelect(userid);
		while(activity.setObject()) {
			jsonArray.put(activity.toJsonObject());
		}
		//System.out.println(selnum+"#"+jsonArray);
		response.getWriter().print(jsonArray);
	}
}
