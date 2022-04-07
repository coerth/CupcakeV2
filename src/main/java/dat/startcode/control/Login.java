package dat.startcode.control;

import dat.startcode.model.config.ApplicationStart;
import dat.startcode.model.entities.Bottom;
import dat.startcode.model.entities.Customer;
import dat.startcode.model.entities.Topping;
import dat.startcode.model.exceptions.DatabaseException;
import dat.startcode.model.persistence.BottomMapper;
import dat.startcode.model.persistence.CustomerMapper;
import dat.startcode.model.persistence.ConnectionPool;
import dat.startcode.model.persistence.ToppingMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "login", urlPatterns = {"/login"} )
public class Login extends HttpServlet
{
    private ConnectionPool connectionPool;

    @Override
    public void init() throws ServletException
    {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        // You shouldn't end up here with a GET-request, thus you get sent back to frontpage
        doPost(request, response);
        response.sendRedirect("index.jsp");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        response.setContentType("text/html");
        HttpSession session = request.getSession();
        session.setAttribute("customer", null); // adding empty user object to session scope
        CustomerMapper userMapper = new CustomerMapper(connectionPool);
        Customer customer = null;


        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try
        {
            customer = userMapper.login(email, password);
            session = request.getSession();
            session.setAttribute("customer", customer); // adding user object to session scope
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
        catch (DatabaseException e)
        {
            Logger.getLogger("web").log(Level.SEVERE, e.getMessage());
            request.setAttribute("errormessage", e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    public void destroy()
    {

    }
}