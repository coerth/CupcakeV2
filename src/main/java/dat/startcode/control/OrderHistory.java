package dat.startcode.control;

import dat.startcode.model.config.ApplicationStart;
import dat.startcode.model.entities.Customer;
import dat.startcode.model.entities.Order;
import dat.startcode.model.entities.Orderline;
import dat.startcode.model.persistence.ConnectionPool;
import dat.startcode.model.persistence.OrderMapper;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "Order", value = "/Order")
public class OrderHistory extends HttpServlet {


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
        ArrayList<Order> orderArrayList = new ArrayList<>();

        Customer customer = (Customer) session.getAttribute("customer");
                if (customer == null) {

        Logger.getLogger("web").log(Level.SEVERE, "Bruger ikke logget ind");
        request.setAttribute("errormessage", "Du er ikke logget ind, prøv igen");
        request.getRequestDispatcher("error.jsp").forward(request, response);
                }

                if(customer.getRole().equals("admin"))
                {
                   orderArrayList = ordermapper.getAllOrders();
                   request.setAttribute("orderArrayList", orderArrayList);
                   request.getRequestDispatcher("WEB-INF/orderOverview.jsp").forward(request,response);
                }

                else
                {
                    orderArrayList = ordermapper.getOrdersWithSpecificCustomerID(customer.getCustomerID());
                    request.setAttribute("orderArrayList", orderArrayList);
                    request.getRequestDispatcher("WEB-INF/orderOverview.jsp").forward(request,response);
                }


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
