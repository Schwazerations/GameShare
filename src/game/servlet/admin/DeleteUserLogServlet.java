package game.servlet.admin;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import game.dao.UserLogDaoImpl;

@WebServlet("/DeleteUserLogServlet.do")
public class DeleteUserLogServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public DeleteUserLogServlet() {
        super();
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserLogDaoImpl logdao=new UserLogDaoImpl();
		
		//前端数据
		String logId=request.getParameter("logId");
		System.out.print("【删除操作日志】---前端传来的logId为："+logId);
		
		try {
			if(logdao.deleteUserLogByLogId(logId) <=0) {
				System.out.println("！500 删除log失败:"+logId);
				response.setStatus(500);	//500服务器内部错误
				request.setAttribute("msg", "删除失败。");
			
			}else {
				System.out.println("√ 删除user成功:"+logId);
				response.setStatus(200); 	//200顺利
			}
			
		} catch (Exception e) {
			System.out.println("！500 servlet-userlog-delete");
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}
}
