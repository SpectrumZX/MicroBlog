package servlets;

import beans.ArticlesFacade;
import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "delete", urlPatterns = {"/admin/delete"})
public class delete extends HttpServlet {

    @EJB
    ArticlesFacade articlesFacade;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            response.setContentType("text/html;charset=UTF-8");
            String parameter = request.getParameter("id");
            int id = Integer.parseInt(parameter);
            String userPath = request.getServletPath();

            if ("/admin/delete".equals(userPath)) {
            articlesFacade.remove(articlesFacade.find(id));
                
                response.sendRedirect("/MicroBlog/faces/admin/index.xhtml");
            }
  
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
