package game.other;

import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/SESSIONIDGeneratorServlet.do")
public class SESSIONIDGeneratorServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private static SecureRandom random = new SecureRandom();

    public static String generateSessionId() {
        return new BigInteger(130, random).toString(32).toUpperCase();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	HttpSession session=request.getSession();
    	if(session.getAttribute("SESSIONID") == null) {//第一次进index，或是登出后的首次进入
    		session.setAttribute("SESSIONID",generateSessionId() );
    		

    		session.setAttribute("SESSIONID_test",generateSessionId() );
    		session.setAttribute("x","y" );
    		
    		System.out.println("【jsid生成器】x="+(String)session.getAttribute("x"));
    	}
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	doGet(request, response);
    }
}
