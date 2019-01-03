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
 * Servlet implementation class ServletEditActivity
 */
@WebServlet("/ServletEditActivity")
public class ServletEditActivity extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletEditActivity() {
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
		String action=(String)request.getParameter("action");
		String hdid=(String)request.getParameter("hdid");
		String userid=(String)request.getParameter("userid");
		Entity table;
		if(action.equals("update")) {
			String[] attribute={"hdid","hdm","stime","etime","place","shdjj","hdjj","classify","maxpeo","crashflag"};
			table=new EntityAttributeTable(attribute,"hd");
			for(int i=1;i<attribute.length;i++) {
				table.setValue(attribute[i], (String)request.getParameter(attribute[i]));
			}
			//is has hdid
			if(hdid==null||hdid.equals("")) {
				//insert activity info
				EntityAttributeTable tableHdid=new EntityAttributeTable("max(hdid)+1","hd");
				tableHdid.sqlSelect();
				tableHdid.setObject();
				hdid=tableHdid.getValue("max(hdid)+1");
				table.setValue(attribute[0], hdid);
				table.sqlInsert();
				//add usercreate
				String[] userCreateAttr= {"userid","hdid"};
				Entity userCreate=new EntityAttributeTable(userCreateAttr,"usercreate");
				userCreate.setValue("userid", userid);
				userCreate.setValue("hdid", hdid);
				userCreate.sqlInsert();
				jsonObject.put("hdid",hdid);
				//set default activity picture
				String pic="pic/icon/defhdpic.svg";
				String[] tablePicAttr= {"pic","hdid"};
				Entity tablePic=new EntityAttributeTable(tablePicAttr,"hd");
				tablePic.setValue("pic",pic);
				tablePic.setSqlCondition("hdid",hdid);
				tablePic.sqlUpdate();
			}else {
				table.setValue(attribute[0], hdid);
				table.setSqlCondition("hdid", hdid);
				table.sqlUpdate();
			}
			jsonNum=1;
		}else {
			table=new Activity();
			table.setSqlCondition("hdid", hdid);
			table.sqlSelect();
			if(table.setObject()) {
				jsonObject=table.toJsonObject();
				jsonNum=1;
			}
		}
		jsonObject.put("num",jsonNum);
		response.getWriter().print(jsonObject);
	}
}
