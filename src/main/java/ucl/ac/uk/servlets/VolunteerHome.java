package ucl.ac.uk.servlets;

import ucl.ac.uk.main.Model;
import ucl.ac.uk.main.Volunteer;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.IOException;

@WebServlet("/VolunteerHome")
public class VolunteerHome extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ServletContext context = getServletContext();
        Model model = (Model) context.getAttribute("model");
        Volunteer volunteer = (Volunteer) model.getUser();
        request.setAttribute("model", model);
        request.setAttribute("user", volunteer);
        RequestDispatcher dispatcher = context.getRequestDispatcher("/VolunteerHome.jsp");
        dispatcher.forward(request, response);
    }
}