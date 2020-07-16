package ucl.ac.uk.servlets;

import ucl.ac.uk.main.Model;
import ucl.ac.uk.main.Shop;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/ShopProducts")
public class ShopProducts extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ServletContext context = getServletContext();
        Model model = (Model) context.getAttribute("model");
        Shop shop = (Shop) model.getUser();
        ArrayList<String> forRemove = new ArrayList<>();
        for(String a : shop.getProducts()){
            String product = request.getParameter(a);
            if(product != null){
                int quantity = Integer.parseInt(product);
                if(quantity == 0){
                    forRemove.add(a);
                }
                else {
                    shop.setQuantity(a, quantity);
                }
            }
        }
        for(String a : forRemove){
            shop.removeProduct(a);
        }
        String[] products = request.getParameterValues("newProduct");
        String[] quantities = request.getParameterValues("newQuantity");
        if(products != null && quantities != null) {
            int num = products.length;
            for (int i = 0; i < num; i++) {
                if (!quantities[i].equals("0") && !products[i].equals("")) {
                    shop.addNewProduct(products[i], Integer.parseInt(quantities[i]));
                }
            }
        }
        model.saveShops();
        request.setAttribute("model", model);
        request.setAttribute("user", model.getUser());
        RequestDispatcher dispatcher = context.getRequestDispatcher("/ShopProducts.jsp");
        dispatcher.forward(request, response);
    }
}