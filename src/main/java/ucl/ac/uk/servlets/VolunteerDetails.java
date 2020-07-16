package ucl.ac.uk.servlets;

import ucl.ac.uk.main.Model;
import ucl.ac.uk.main.Volunteer;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.IOException;

@WebServlet("/VolunteerDetails")
public class VolunteerDetails extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ServletContext context = getServletContext();
        Model model = (Model) context.getAttribute("model");
        Volunteer volunteer = (Volunteer) model.getUser();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String repeatPassword = request.getParameter("repeatPassword");
        String address = request.getParameter("address");
        String address2 = request.getParameter("address2");
        String city = request.getParameter("city");
        String country = request.getParameter("country");
        String first = request.getParameter("firstName");
        String last = request.getParameter("lastName");
        if (password != null && password.equals(repeatPassword)) {
            volunteer.setUsername(username);
            volunteer.setPassword(password);
            if (address2 != null && !address2.equals("")) {
                volunteer.setAddress(address + ", " + address2);
                volunteer.setCity(city);
                volunteer.setCountry(country);
                volunteer.setFirstName(first);
                volunteer.setLastName(last);
                model.removeVolunteer(volunteer);
                volunteer.registerAddress(address + ", " + address2 + ", " + city + ", " + country);
                model.addVolunteer(volunteer);
            } else {
                volunteer.setAddress(address);
                volunteer.setCity(city);
                volunteer.setCountry(country);
                volunteer.setFirstName(first);
                volunteer.setLastName(last);
                model.removeVolunteer(volunteer);
                volunteer.registerAddress(address + ", " + city + ", " + country);
                model.addVolunteer(volunteer);
            }
        }
        model.saveVulnerables();
        model.saveVolunteers();
        request.setAttribute("model", model);
        request.setAttribute("user", volunteer);
        RequestDispatcher dispatcher = context.getRequestDispatcher("/VolunteerDetails.jsp");
        dispatcher.forward(request, response);
    }
}
