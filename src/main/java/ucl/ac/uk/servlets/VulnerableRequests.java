package ucl.ac.uk.servlets;

import ucl.ac.uk.main.Model;
import ucl.ac.uk.main.Vulnerable;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.IOException;

@WebServlet("/VulnerableRequests")
public class VulnerableRequests extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ServletContext context = getServletContext();
        Model model = (Model) context.getAttribute("model");
        Vulnerable vulnerable = (Vulnerable) model.getUser();
        String[] products = request.getParameterValues("product");
        String[] quantities = request.getParameterValues("quantity");
        String[] days = request.getParameterValues("wait");
        String[] types = request.getParameterValues("type");
        if(products != null && quantities != null) {
            int num = products.length;
            for (int i = 0; i < num; i++) {
                if (!quantities[i].equals("0") && !products[i].equals("")) {
                    String quantity = quantities[i];
                    try{
                        vulnerable.addRequest(products[i], Integer.parseInt(quantity), Integer.parseInt(days[i]), types[i]);
                    }catch (NumberFormatException e) {
                        request.setAttribute("model", model);
                        request.setAttribute("user", model.getUser());
                        RequestDispatcher dispatcher = context.getRequestDispatcher("/VulnerableRequests.jsp");
                        dispatcher.forward(request, response);
                    }
                }
            }
        }
        String remove = request.getParameter("remove");
        if(remove != null) {
            int removeIndex = Integer.parseInt(remove);
            vulnerable.removeRequest(removeIndex);
        }
        model.saveVulnerables();
        model.saveVolunteers();
        request.setAttribute("model", model);
        request.setAttribute("user", model.getUser());
        RequestDispatcher dispatcher = context.getRequestDispatcher("/VulnerableRequests.jsp");
        dispatcher.forward(request, response);
    }
}
