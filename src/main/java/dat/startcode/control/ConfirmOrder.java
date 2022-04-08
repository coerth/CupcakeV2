package dat.startcode.control;

import dat.startcode.model.config.ApplicationStart;
import dat.startcode.model.entities.Customer;
import dat.startcode.model.entities.Order;
import dat.startcode.model.persistence.ConnectionPool;
import dat.startcode.model.persistence.OrderMapper;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
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
        if (customer == null) {

            Logger.getLogger("web").log(Level.SEVERE, "Bruger ikke logget ind");
            request.setAttribute("errormessage", "Du er ikke logget ind, prøv igen");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
        else {

        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
