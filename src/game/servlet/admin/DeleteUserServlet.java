package game.servlet.admin;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import game.bean.User;
import game.bean.UserLog;
import game.dao.UserDaoImpl;

@WebServlet("/DeleteUserServlet.do")
public class DeleteUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public DeleteUserServlet() {
        super();
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserLog userLog = new UserLog();
		UserDaoImpl userdao=new UserDaoImpl();
		HttpSession session=request.getSession();

		String in_uid=request.getParameter("uid");
		
System.out.println("---前端传来的uid为："+in_uid);

		//测试-暂时
		User Login_user=(User) session.getAttribute("Login_user");
		String Login_uid=Login_user.getUid();
		String Login_urole=Login_user.getUrole();
		String Login_uname=Login_user.getUname();
//		String Login_uid="114514";
//		String Login_urole="admin";
//		String Login_uname="管理员先生";
		try {
			if(userdao.deleteUser(in_uid) <=0) {
				System.out.println("！500 删除student失败:"+in_uid);
				userLog.logOperation(Login_uid,Login_uname, Login_urole,  "删除用户/注销", "失败");
				response.setStatus(500);	//500服务器内部错误
				
				request.setAttribute("msg", "删除失败。");
				request.setAttribute("path", "../QueryAllUserServlet.do");
				request.getRequestDispatcher("error.jsp").forward(request, response);
			
			}else {
				System.out.println("√ 删除user成功:"+in_uid);
				response.setStatus(200); 	//200顺利
				//紧接着，如果不是管理员在操作，也要移除登录状态：
				if( !("admin".equals(Login_urole)) ) {
					session.invalidate();
					System.out.println("√ 已移除登录状态。");
					userLog.logOperation(Login_uid,Login_uname, Login_urole,  "用户注销", "成功");
				}
				userLog.logOperation(Login_uid,Login_uname, Login_urole,  "管理员删除用户", "成功");
				
				request.setAttribute("msg", "删除成功。");
				request.setAttribute("path", "../QueryAllUserServlet.do");
				request.getRequestDispatcher("error.jsp").forward(request, response);
			}
			
		} catch (Exception e) {
			userLog.logOperation(Login_uid,Login_uname, Login_urole,  "管理员删除用户:500", "成功");
			System.out.println("servlet-deleteuser：删除时报错。");
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}
}
