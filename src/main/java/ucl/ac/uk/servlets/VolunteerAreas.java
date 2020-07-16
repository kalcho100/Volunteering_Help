package ucl.ac.uk.servlets;

import ucl.ac.uk.main.Area;
import ucl.ac.uk.main.Model;
import ucl.ac.uk.main.Shop;
import ucl.ac.uk.main.Volunteer;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/VolunteerAreas")
public class VolunteerAreas extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ServletContext context = getServletContext();
        Model model = (Model) context.getAttribute("model");
        Volunteer volunteer = (Volunteer) model.getUser();
        String remove = request.getParameter("remove");
        if(remove != null){
            model.removeArea(volunteer, volunteer.getAreas().get(Integer.parseInt(remove)));
        }
        String[] address = request.getParameterValues("address");
        String[] city = request.getParameterValues("city");
        String[] country = request.getParameterValues("country");
        String[] radius = request.getParameterValues("radius");
        String[] start = request.getParameterValues("start");
        String[] finish = request.getParameterValues("finish");
        if(address != null){
            int num = address.length;
            for(int i = 0; i < num; i++){
                if(!address[i].equals("") && !city[i].equals("") && !country[i].equals("") && !radius[i].equals("") && !start[i].equals("") && !finish[i].equals("")){
                    String[] starts = start[i].split("T");
                    String[] finishes = finish[i].split("T");
                            model.addArea(volunteer, address[i], city[i], country[i], Integer.parseInt(radius[i]), starts[0], starts[1], finishes[0], finishes[1]);
                }
            }
        }
        model.saveVolunteers();
        request.setAttribute("model", model);
        request.setAttribute("user", model.getUser());
        RequestDispatcher dispatcher = context.getRequestDispatcher("/VolunteerAreas.jsp");
        dispatcher.forward(request, response);
    }
}