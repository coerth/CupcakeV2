package dat.startcode.control;

import dat.startcode.model.config.ApplicationStart;
import dat.startcode.model.entities.Customer;
import dat.startcode.model.exceptions.DatabaseException;
import dat.startcode.model.persistence.ConnectionPool;
import dat.startcode.model.persistence.CustomerMapper;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "CreateUser", value = "/CreateUser")
public class CreateUser extends HttpServlet {

    private ConnectionPool connectionPool;

    @Override
    public void init() throws ServletException {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
// You shouldn't end up here with a GET-request, thus you get sent back to frontpage
        doPost(request, response);
        response.sendRedirect("index.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        HttpSession session = request.getSession();
        session.setAttribute("customer", null); // adding empty user object to session scope
        CustomerMapper customerMapper = new CustomerMapper(connectionPool);
        Customer customer = null;

        String name = request.getParameter("name");
        String email = request.getParameter("newEmail");
        String password = request.getParameter("newPassword");
        int address_id = Integer.parseInt(request.getParameter("address"));
        int streetNumber = Integer.parseInt(request.getParameter("streetNumber"));
        int zipcode = Integer.parseInt(request.getParameter("zipcode"));
        String city = request.getParameter("city");

        /*try {
            customerMapper.createCustomer(name, email,password,address_id);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }*/
    }
}
