package game.other;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;


@WebFilter("/*")
public class UTF8CodeFilter extends HttpFilter implements Filter {
       
	private static final long serialVersionUID = 1L;

	public UTF8CodeFilter() {
        super();
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		String path =((HttpServletRequest)request).getRequestURI();
		if( path.contains(".css") || path.contains(".js") 
				|| path.contains(".png") ||  path.contains(".jpg") ||  path.contains(".jif") ){
			chain.doFilter(request, response);//pass
		}else {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=utf-8");
			chain.doFilter(request, response);
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}
