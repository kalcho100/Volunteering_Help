package ucl.ac.uk.servlets;

import ucl.ac.uk.main.Model;
import ucl.ac.uk.main.Vulnerable;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.IOException;

@WebServlet("/VulnerableDetails")
public class VulnerableDetails extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ServletContext context = getServletContext();
        Model model = (Model) context.getAttribute("model");
        Vulnerable vulnerable = (Vulnerable) model.getUser();
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
            vulnerable.setUsername(username);
            vulnerable.setPassword(password);
            if (address2 != null && !address2.equals("")) {
                vulnerable.setAddress(address + ", " + address2);
                vulnerable.setCity(city);
                vulnerable.setCountry(country);
                vulnerable.setFirstName(first);
                vulnerable.setLastName(last);
                model.removeVulnerable(vulnerable);
                vulnerable.registerAddress(address + ", " + address2 + ", " + city + ", " + country);
                model.addVulnerable(vulnerable);
            } else {
                vulnerable.setAddress(address);
                vulnerable.setCity(city);
                vulnerable.setCountry(country);
                vulnerable.setFirstName(first);
                vulnerable.setLastName(last);
                model.removeVulnerable(vulnerable);
                vulnerable.registerAddress(address + ", " + city + ", " + country);
                model.addVulnerable(vulnerable);
            }
        }
        model.saveVolunteers();
        model.saveVulnerables();
        request.setAttribute("model", model);
        request.setAttribute("user", vulnerable);
        RequestDispatcher dispatcher = context.getRequestDispatcher("/VulnerableDetails.jsp");
        dispatcher.forward(request, response);
    }
}
