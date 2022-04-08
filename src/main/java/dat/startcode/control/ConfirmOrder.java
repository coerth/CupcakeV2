package dat.startcode.control;

import dat.startcode.model.config.ApplicationStart;
import dat.startcode.model.entities.CupcakeOrder;
import dat.startcode.model.entities.Customer;
import dat.startcode.model.exceptions.DatabaseException;
import dat.startcode.model.persistence.ConnectionPool;
import dat.startcode.model.persistence.OrderMapper;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "ConfirmOrder", value = "/ConfirmOrder")
public class ConfirmOrder extends HttpServlet {

    private ConnectionPool connectionPool;

    @Override
    public void init() throws ServletException
    {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        OrderMapper ordermapper = new OrderMapper(connectionPool);

        Customer customer = (Customer) session.getAttribute("customer");
        LocalDateTime localDateTime = LocalDateTime.now();



        if (customer == null) {

            Logger.getLogger("web").log(Level.SEVERE, "Bruger ikke logget ind");
            request.setAttribute("errormessage", "Du er ikke logget ind, prøv igen");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
        else {
            ArrayList<CupcakeOrder> cupcakeOrderArrayList = (ArrayList<CupcakeOrder>) session.getAttribute("cupcakeOrderArrayList");
            int orderAmount = 0;

            for (CupcakeOrder cupcakeOrder : cupcakeOrderArrayList)
            {
                orderAmount += cupcakeOrder.getAmount();
            }

            try {


                int orderID = ordermapper.createOrder(customer.getCustomerID(),cupcakeOrderArrayList, localDateTime);

                    request.setAttribute("orderAmount",orderAmount);
                    request.setAttribute("order", ordermapper.getOrderWithOrderID(orderID));
                    request.getRequestDispatcher("WEB-INF/orderConfirmed.jsp").forward(request, response);



            } catch (DatabaseException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
