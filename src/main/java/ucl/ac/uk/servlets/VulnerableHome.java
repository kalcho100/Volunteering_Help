package ucl.ac.uk.servlets;

import ucl.ac.uk.main.Model;
import ucl.ac.uk.main.Vulnerable;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.IOException;

@WebServlet("/VulnerableHome")
public class VulnerableHome extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ServletContext context = getServletContext();
        Model model = (Model) context.getAttribute("model");
        Vulnerable vulnerable = (Vulnerable) model.getUser();
        request.setAttribute("model", model);
        request.setAttribute("user", vulnerable);
        RequestDispatcher dispatcher = context.getRequestDispatcher("/VulnerableHome.jsp");
        dispatcher.forward(request, response);
    }
}