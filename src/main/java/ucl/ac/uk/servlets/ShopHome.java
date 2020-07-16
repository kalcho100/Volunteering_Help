package ucl.ac.uk.servlets;

import ucl.ac.uk.main.Model;
import ucl.ac.uk.main.Shop;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.IOException;

@WebServlet("/ShopHome")
public class ShopHome extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ServletContext context = getServletContext();
        Model model = (Model) context.getAttribute("model");
        Shop shop = (Shop) model.getUser();
        request.setAttribute("model", model);
        request.setAttribute("user", shop);
        RequestDispatcher dispatcher = context.getRequestDispatcher("/ShopHome.jsp");
        dispatcher.forward(request, response);
    }
}