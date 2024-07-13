package game.servlet.admin;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import game.bean.UserLog;
import game.dao.UserLogDaoImpl;

@WebServlet("/QueryAllUserLogServlet.do")
public class QueryAllUserLogServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public QueryAllUserLogServlet() {
		super();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		UserLogDaoImpl logdao=new UserLogDaoImpl();
		HttpSession session=request.getSession();

		String path_param=request.getParameter("path");
		String path=(String) session.getAttribute("path");//session中
			if(path_param!=null) path=path_param;
			
System.out.print("【日志】---path（日志全查）="+path+" ; ");
		
		try {
			List<UserLog> li =logdao.queryAllUserLog();
			if(li.isEmpty()) {
				System.out.println("[null] servlet-userLog-全查:li是空的。");
				response.setStatus(400);
				return;
			}else {
				System.out.println("√ servlet-userLog-全查:li成功获取数据。");
//for(UserLog i:li) {
//	System.out.println(i);
//}
			}
			request.setAttribute("allUserLogs", li);
			response.setStatus(200);//200
			request.getRequestDispatcher("jsp_admin/"+path).forward(request, response);
		} catch (Exception e) {
			System.out.println("!500 servlet-userLog-全查");
			response.setStatus(500);
		}
		
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
