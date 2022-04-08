package dat.startcode.control;

import dat.startcode.model.entities.CupcakeOrder;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "Checkout", value = "/Checkout")
public class Checkout extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

       HttpSession session = request.getSession();
       int orderTotal = 0;
       ArrayList<CupcakeOrder> cupcakeOrderArrayList = (ArrayList<CupcakeOrder>) session.getAttribute("cupcakeOrderArrayList");

        for (CupcakeOrder cupcakeOrder : cupcakeOrderArrayList)
        {
            orderTotal += cupcakeOrder.getTotal();
        }



        request.getRequestDispatcher("WEB-INF/shoppingCart.jsp").forward(request, response);
        }



    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
