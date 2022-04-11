package dat.startcode.control;

import dat.startcode.model.entities.CupcakeOrder;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "RemoveCartItem", value = "/RemoveCartItem")
public class RemoveCartItem extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        ArrayList<CupcakeOrder> cupcakeOrderArrayList = (ArrayList<CupcakeOrder>) session.getAttribute("cupcakeOrderArrayList");

        int cartItem = Integer.parseInt(request.getParameter("cartItem"));
        if(cartItem < cupcakeOrderArrayList.size() && cartItem >= 0)
        {
        log("Bestilling: " + cupcakeOrderArrayList.get(cartItem) + " er fjernet fra kurven" );
        cupcakeOrderArrayList.remove(cartItem);
        }

       session.setAttribute("cupcakeOrderArrayList" , cupcakeOrderArrayList);

        request.getRequestDispatcher("WEB-INF/shoppingCart.jsp").forward(request, response);

    }
}
