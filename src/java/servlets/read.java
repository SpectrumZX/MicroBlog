package servlets;

import beans.ArticlesFacade;
import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "read", urlPatterns = {"/page", "/admin/page"})
public class read extends HttpServlet {

    @EJB
    ArticlesFacade articlesFacade;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String parameter = request.getParameter("id");
        int id = Integer.parseInt(parameter);
        String userPath = request.getServletPath();
        if ("/admin/page".equals(userPath)) {
            getServletContext().setAttribute("current_article", articlesFacade.find(id));
            response.sendRedirect("onearticle.xhtml");
        }  else {
            if ("/page".equals(userPath)) {
                getServletContext().setAttribute("current_article", articlesFacade.find(id));
                //  request.getRequestDispatcher("onearticle.xhtml").forward(request, response);
                response.sendRedirect("onearticle.xhtml");
             //   request.getSession().getAttribute(parameter)
                      
            }
        } 
    }

        @Override
        protected void doGet
        (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            processRequest(request, response);
        }

       
        @Override
        protected void doPost
        (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            processRequest(request, response);
        }


        @Override
        public String getServletInfo
        
            () {
        return "Short description";
        }

    }
