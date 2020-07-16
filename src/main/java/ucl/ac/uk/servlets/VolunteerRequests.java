package ucl.ac.uk.servlets;

import ucl.ac.uk.main.Model;
import ucl.ac.uk.main.Volunteer;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.IOException;

@WebServlet("/VolunteerRequests")
public class VolunteerRequests extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        ServletContext context = getServletContext();
        Model model = (Model) context.getAttribute("model");
        Volunteer volunteer = (Volunteer) model.getUser();
        String remove = request.getParameter("remove");
        if(remove != null) {
//            volunteer.removeRequest(Integer.parseInt(remove));
        }
        model.saveVolunteers();
        model.saveVulnerables();
        request.setAttribute("model", model);
        request.setAttribute("user", model.getUser());
        RequestDispatcher dispatcher = context.getRequestDispatcher("/VolunteerRequests.jsp");
        dispatcher.forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
