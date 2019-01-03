package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

/**
 * Servlet implementation class ServletChatHistory
 */
@WebServlet("/ServletChatHistory")
public class ServletChatHistory extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletChatHistory() {
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
		JSONObject jsonObject=new JSONObject();
		String userid,hyid,path=null;
		userid=request.getParameter("userid");
		hyid=request.getParameter("hyid");
		if(userid.compareTo(hyid)<0) {
			path="chatRecord/"+userid+"_"+hyid+".txt";
		}else {
			path="chatRecord/"+hyid+"_"+userid+".txt";
		}
		jsonObject.put("path", path);
		response.getWriter().print(jsonObject);
	}
}
