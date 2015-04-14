package servlets;

import beans.Articles;
import beans.ArticlesFacade;
import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "read_ranges", urlPatterns = {"/faces/index", "/faces/admin/index", "/index.xhtml"})
public class read_ranges extends HttpServlet {
    
 @EJB ArticlesFacade articlesFacade;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
   Integer first_id;
   Integer last_id;

       // если GET параметры пустые ставим значения по умолчанию иначе берем из параметров
       if (request.getParameter("first") == null || request.getParameter("last") == null){
           first_id = 1;
           last_id = 5;
       }
       else{
        first_id = Integer.parseInt(request.getParameter("first"));
        last_id = Integer.parseInt(request.getParameter("last"));
       }
        // наполняем массив range для метода List<Articles> findRange(int[] range)
      int[] range = new int[2]; 
      range[0] = first_id-1;
      range[1] = last_id-1;
        List<Articles> articles_range = articlesFacade.findRange(range);
        getServletContext().setAttribute("articles_range", articles_range);
        
        // если при старте зашли на /MicroBlog/index.xhtml то перенаправить в faces/index.xhtml
        if("/MicroBlog/index.xhtml".equals(request.getRequestURI())) {
            response.sendRedirect("faces/index.xhtml");
        } else response.sendRedirect("index.xhtml");
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
