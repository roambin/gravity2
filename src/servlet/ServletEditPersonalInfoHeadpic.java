package servlet;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.jspsmart.upload.SmartUpload;

import entity.EntityAttributeTable;
import entity.User;
import upload.UploadImage;
import util.SetCertificate;
import util.TokenCheck;

/**
 * Servlet implementation class ServletEditPersonalInfoHeadpic
 */
@WebServlet("/ServletEditPersonalInfoHeadpic")
public class ServletEditPersonalInfoHeadpic extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletEditPersonalInfoHeadpic() {
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
		int jsonNum=0;
		//upload file
		String userid,token,path;
		UploadImage upload=new UploadImage(this.getServletConfig(),request,response);
		SmartUpload smartUpload = upload.smartUpload();
		token=smartUpload.getRequest().getParameter("token");
		userid=TokenCheck.fileUploadCheck(request, response, this, token);
		path=upload.saveFile(smartUpload, "pic"+File.separator+"userpic"+File.separator, userid);
		//update datebase
		EntityAttributeTable table=new EntityAttributeTable("headpic","user");
		table.setValue("headpic", path);
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
		//json return
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("num",jsonNum);
		jsonObject.put("path",path);
		response.getWriter().print(jsonObject);
	}
}
