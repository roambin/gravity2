package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import entity.*;

/**
 * Servlet implementation class ServletJoin
 */
@WebServlet("/ServletJoin")
public class ServletJoin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletJoin() {
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
		String[] attribute= {"userid","hdid"};
		Entity table=new EntityAttributeTable(attribute,"userjoin");
		String userid=(String)request.getParameter("userid");
		String hdid=request.getParameter("hdid");
		JSONObject jsonObject=new JSONObject();
		if(userid!=null) {
			table.setSqlCondition("userid", userid);
			table.setSqlCondition("hdid", hdid);
			table.sqlSelect();
			if(table.isHasNextResult()) {
				table.sqlDelete();
				jsonObject.put("text","已取消报名！");
				jsonObject.put("num","0");
			}else {
				if(check(request,response,userid,hdid)) {
					table.setValue("userid", userid);
					table.setValue("hdid", hdid);
					table.sqlInsert();
					jsonObject.put("text","报名成功！");
					jsonObject.put("num","1");
				}else {
					jsonObject.put("text","与已报名的活动时间冲突");
					jsonObject.put("num","2");
				}
			}
		}else {
			jsonObject.put("text","未登入！");
			jsonObject.put("num","2");
		}
		response.getWriter().print(jsonObject);
	}
	private boolean check(HttpServletRequest request,HttpServletResponse response,String userid,String hdid) throws IOException {
		Entity activity=new Activity();
		activity.setSqlCondition("hdid", hdid);
		activity.sqlSelect();
		activity.setObject();
		if(activity.getValue("crashflag").equals("可兼报")) {
			return true;
		}
		Entity activityAll=new ActivityTime(userid,activity.getValue("stime"),activity.getValue("etime"));
		activityAll.sqlSelect();
		if(!activityAll.isHasNextResult()) {
			return false;
		}
		return true;
	}
}
