package ucl.ac.uk.servlets;

import ucl.ac.uk.main.Model;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.IOException;

@WebServlet("/Login")
public class Login extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        Model model = new Model();
        ServletContext context = getServletContext();
        context.setAttribute("model", model);
        if(model.login(request.getParameter("username"), request.getParameter("password")) == 1){
            RequestDispatcher dispatcher = context.getRequestDispatcher("/VolunteerHome");
            dispatcher.forward(request, response);
        }
        else if(model.login(request.getParameter("username"), request.getParameter("password")) == 2){
            RequestDispatcher dispatcher = context.getRequestDispatcher("/VulnerableHome");
            dispatcher.forward(request, response);
        }
        else if(model.login(request.getParameter("username"), request.getParameter("password")) == 3){
            RequestDispatcher dispatcher = context.getRequestDispatcher("/ShopHome");
            dispatcher.forward(request, response);
        }
        else if(model.login(request.getParameter("username"), request.getParameter("password")) == 4){
            RequestDispatcher dispatcher = context.getRequestDispatcher("/GovernmentHome");
            dispatcher.forward(request, response);
        }
        else{
            response.sendRedirect("index.html");
        }
    }
}
