package ucl.ac.uk.servlets;

import ucl.ac.uk.main.Government;
import ucl.ac.uk.main.Model;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.IOException;

@WebServlet("/GovernmentHome")
public class GovernmentHome extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ServletContext context = getServletContext();
        Model model = (Model) context.getAttribute("model");
        Government government = (Government) model.getUser();
        request.setAttribute("model", model);
        request.setAttribute("user", government);
        RequestDispatcher dispatcher = context.getRequestDispatcher("/GovernmentHome.jsp");
        dispatcher.forward(request, response);
    }
}