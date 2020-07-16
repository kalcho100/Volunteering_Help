package ucl.ac.uk.servlets;

import ucl.ac.uk.main.Government;
import ucl.ac.uk.main.Model;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.IOException;

@WebServlet("/GovernmentVulnerable")
public class GovernmentVulnerable extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ServletContext context = getServletContext();
        Model model = (Model) context.getAttribute("model");
        Government government = (Government) model.getUser();
        String index = request.getParameter("remove");
        if(index != null){
            model.removeRegisteredVulnerable(government, Integer.parseInt(index));
        }
        String[] first = request.getParameterValues("firstName");
        String[] last = request.getParameterValues("lastName");
        String[] address = request.getParameterValues("address");
        if (first != null) {
            for(int i = 0; i < first.length; i++){
                government.addVulnerable(first[i], last[i], address[i], government.getCity(), government.getCountry());
            }
        }
        model.saveGovernments();
        request.setAttribute("model", model);
        request.setAttribute("user", government);
        RequestDispatcher dispatcher = context.getRequestDispatcher("/GovernmentVulnerable.jsp");
        dispatcher.forward(request, response);
    }
}