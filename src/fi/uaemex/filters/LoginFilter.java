
package fi.uaemex.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import fi.uaemex.beans.LoginBean;

@WebFilter(filterName = "AuthFilter", urlPatterns = {"*.xhtml"})
public class LoginFilter implements Filter {
    

    private FilterConfig filterConfig = null;
    
    public LoginFilter() {
    }    
   
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        try
        {
            LoginBean loginB =(LoginBean) ((HttpServletRequest)request).getSession().getAttribute("login");
            String reqURI = ((HttpServletRequest)request).getRequestURI();
            System.out.println("uri " + reqURI);
            if(true)
          //  if((loginB == null  || !loginB.isIsLoggin()) && !reqURI.contains("/index.xhtml") && !reqURI.contains("/index.ico") && !reqURI.contains("javax.faces.resource"))
            { // Si no hay un usuario loggueado redirecciona a la pagina index para realizar el login (TOP)
               // String contextPath = ((HttpServletRequest)request).getContextPath();
                //System.out.println("context path " + contextPath);
                //((HttpServletResponse)response).sendRedirect(contextPath + "/index.xhtml");
            } // Si no hay un usuario loggueado redirecciona a la pagina index para realizar el login (BOTTOM)
            else
                chain.doFilter(request, response);
        }
        catch(IOException | ServletException ex)
        {
            System.out.println("ocurrió un error al reponder la petición : " + ex.toString());
        }
    }


    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void destroy() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
    
}
