package ucl.ac.uk.servlets;

import ucl.ac.uk.main.*;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.IOException;

@WebServlet(urlPatterns = { "/Register"})
public class Register extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        Model model = new Model();
        ServletContext context = getServletContext();
        context.setAttribute("model", model);
        RequestDispatcher dispatcher = null;
        String type = request.getParameter("user");
        String address = request.getParameter("address") + ", " + request.getParameter("city") + ", " + request.getParameter("country");
        if(type.equals("government")){
            Government government = new Government(request.getParameter("username"), request.getParameter("password"));
            government.registerAddress(address);
            government.setAddress(request.getParameter("address"));
            government.setCity(request.getParameter("city"));
            government.setCountry(request.getParameter("country"));
            model.setUser(government);
            model.addGovernment(government);
            dispatcher = context.getRequestDispatcher("/GovernmentHome");
        }
        else if(type.equals("shop")){
            Shop shop = new Shop(request.getParameter("username"), request.getParameter("password"));
            shop.registerAddress(address);
            shop.setAddress(request.getParameter("address"));
            shop.setCity((request.getParameter("city")));
            shop.setCountry(request.getParameter("country"));
            shop.setName(request.getParameter("name"));
            shop.setType(request.getParameter("type"));
            model.setUser(shop);
            model.addShop(shop);
            dispatcher = context.getRequestDispatcher("/ShopHome");
        }
        else if(type.equals("vulnerable")){
            Vulnerable vulnerable = new Vulnerable(request.getParameter("username"), request.getParameter("password"));
            vulnerable.registerAddress(address);
            vulnerable.setAddress(request.getParameter("address"));
            vulnerable.setCity(request.getParameter("city"));
            vulnerable.setCountry(request.getParameter("country"));
            vulnerable.setFirstName(request.getParameter("firstName"));
            vulnerable.setLastName(request.getParameter("lastName"));
            try {
                if (model.isVulnerableRegistered(vulnerable)) {
                    model.setUser(vulnerable);
                    model.addVulnerable(vulnerable);
                    dispatcher = context.getRequestDispatcher("/VulnerableHome");
                } else {
                    response.sendRedirect("index.html");
                }
            }catch (NullPointerException e){
                response.sendRedirect("index.html");
            }
        }
        else{
            Volunteer volunteer = new Volunteer(request.getParameter("username"), request.getParameter("password"));
            volunteer.registerAddress(address);
            volunteer.setAddress(request.getParameter("address"));
            volunteer.setCity(request.getParameter("city"));
            volunteer.setCountry(request.getParameter("country"));
            volunteer.setFirstName(request.getParameter("firstName"));
            volunteer.setLastName(request.getParameter("lastName"));
            model.setUser(volunteer);
            model.addVolunteer(volunteer);
            dispatcher = context.getRequestDispatcher("/VolunteerHome");
        }
        dispatcher.forward(request, response);
    }
}