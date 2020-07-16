package ucl.ac.uk.servlets;

import ucl.ac.uk.main.Government;
import ucl.ac.uk.main.Model;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.IOException;

@WebServlet("/GovernmentDetails")
public class GovernmentDetails extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ServletContext context = getServletContext();
        Model model = (Model) context.getAttribute("model");
        Government government = (Government) model.getUser();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String repeatPassword = request.getParameter("repeatPassword");
        String address = request.getParameter("address");
        String address2 = request.getParameter("address2");
        String city = request.getParameter("city");
        String country = request.getParameter("country");
        if (password != null && password.equals(repeatPassword)) {
            government.setUsername(username);
            government.setPassword(password);
            if (address2 != null && !address2.equals("")) {
                government.setAddress(address + ", " + address2);
                government.setCity(city);
                government.setCountry(country);
                model.removeGovernment(government);
                government.registerAddress(address + ", " + address2 + ", " + city + ", " + country);
                model.addGovernment(government);
            } else {
                government.setAddress(address);
                government.setCity(city);
                government.setCountry(country);
                model.removeGovernment(government);
                government.registerAddress(address + ", " + city + ", " + country);
                model.addGovernment(government);
            }
        }
        model.saveGovernments();
        request.setAttribute("model", model);
        request.setAttribute("user", government);
        RequestDispatcher dispatcher = context.getRequestDispatcher("/GovernmentDetails.jsp");
        dispatcher.forward(request, response);
    }
}
