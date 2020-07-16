package ucl.ac.uk.servlets;

import ucl.ac.uk.main.Model;
import ucl.ac.uk.main.Shop;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.IOException;

@WebServlet("/ShopDetails")
public class ShopDetails extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ServletContext context = getServletContext();
        Model model = (Model) context.getAttribute("model");
        Shop shop = (Shop) model.getUser();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String repeatPassword = request.getParameter("repeatPassword");
        String address = request.getParameter("address");
        String address2 = request.getParameter("address2");
        String city = request.getParameter("city");
        String country = request.getParameter("country");
        String name = request.getParameter("name");
        String type = request.getParameter("type");
        if (password != null && password.equals(repeatPassword)) {
            shop.setUsername(username);
            shop.setPassword(password);
            if (address2 != null && !address2.equals("")) {
                shop.setAddress(address + ", " + address2);
                shop.setCity(city);
                shop.setCountry(country);
                shop.setName(name);
                shop.setType(type);
                model.removeShop(shop);
                shop.registerAddress(address + ", " + address2 + ", " + city + ", " + country);
                model.addShop(shop);
            } else {
                shop.setAddress(address);
                shop.setCity(city);
                shop.setCountry(country);
                shop.setName(name);
                shop.setType(type);
                model.removeShop(shop);
                shop.registerAddress(address + ", " + city + ", " + country);
                model.addShop(shop);
            }
        }
        model.saveShops();
        request.setAttribute("model", model);
        request.setAttribute("user", shop);
        RequestDispatcher dispatcher = context.getRequestDispatcher("/ShopDetails.jsp");
        dispatcher.forward(request, response);
    }
}