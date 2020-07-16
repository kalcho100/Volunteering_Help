package ucl.ac.uk.servlets;

import ucl.ac.uk.main.Model;
import ucl.ac.uk.main.Shop;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.IOException;

@WebServlet("/ShopOpeningTimes")
public class ShopOpeningTimes extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        ServletContext context = getServletContext();
        Model model = (Model) context.getAttribute("model");
        Shop shop = (Shop) model.getUser();
        for(String day : days){
            String open = request.getParameter("startTime" + day);
            String close = request.getParameter("finishTime" + day);
            if(open != null && close != null) {
                shop.setOpeningTime(day, open);
                shop.setClosingTime(day, close);
            }
            if(request.getParameter("First") != null) {
                String isWorking = request.getParameter("closed" + day);
                if (isWorking != null) {
                    shop.setIsWorking(day, Boolean.parseBoolean(isWorking));
                } else {
                    shop.setIsWorking(day, true);
                }
            }
        }
        model.saveShops();
        request.setAttribute("model", model);
        request.setAttribute("user", shop);
        RequestDispatcher dispatcher = context.getRequestDispatcher("/ShopOpeningTimes.jsp");
        dispatcher.forward(request, response);
    }
}
