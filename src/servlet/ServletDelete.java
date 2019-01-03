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
 * Servlet implementation class ServletDelete
 */
@WebServlet("/ServletDelete")
public class ServletDelete extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletDelete() {
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
		String[] attribute= {"delflag","delday"};
		Entity table=new EntityAttributeTable(attribute,"hd");
		String hdid=request.getParameter("hdid");
		JSONObject jsonObject=new JSONObject();
		table.setSqlCondition("hdid", hdid);
		table.setSqlCondition("delflag", "");
		table.sqlSelect();
		table.delSqlCondition("delflag");
		if(table.isHasNextResult()) {
			table.setValue("delflag", "'已删除'");
			table.setValue("delday", "'0'");
			table.sqlUpdateNoQuot();
			jsonObject.put("text","已删除！");
		}else {
			table.setValue("delflag", "''");
			table.setValue("delday", "NULL");
			table.sqlUpdateNoQuot();
			jsonObject.put("text","已恢复！");
		}
		response.getWriter().print(jsonObject);
	}
}
