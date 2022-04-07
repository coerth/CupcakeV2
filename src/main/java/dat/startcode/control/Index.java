package dat.startcode.control;

import dat.startcode.model.config.ApplicationStart;
import dat.startcode.model.entities.Bottom;
import dat.startcode.model.entities.Topping;
import dat.startcode.model.exceptions.DatabaseException;
import dat.startcode.model.persistence.BottomMapper;
import dat.startcode.model.persistence.ConnectionPool;
import dat.startcode.model.persistence.ToppingMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "IIndex", value = "")
public class Index extends HttpServlet
{

    private BottomMapper bottomMapper;
    private ToppingMapper toppingMapper;

    @Override
    public void init() throws ServletException
    {
        ConnectionPool connectionPool = ApplicationStart.getConnectionPool();
        bottomMapper = new BottomMapper(connectionPool);
        toppingMapper = new ToppingMapper(connectionPool);




        try {


            ArrayList<Bottom> bottomArrayList = new ArrayList<>();
            ArrayList<Topping> toppingArrayList = new ArrayList<>();

            log("bottomArrayList size er: "+bottomArrayList.size()+ " Før database pull");
            log("toppingArrayList size er: "+toppingArrayList.size()+ " Før database pull");

            bottomArrayList = bottomMapper.getAllBottoms();
            toppingArrayList = toppingMapper.getAllToppings();

            getServletContext().setAttribute("bottomArrayList", bottomArrayList);
            getServletContext().setAttribute("toppingArrayList", toppingArrayList);

            log("bottomArrayList size er: "+bottomArrayList.size()+ " Efter database pull");
            log("toppingArrayList size er: "+toppingArrayList.size()+ " Efter database pull");

        } catch (DatabaseException e) {
            e.printStackTrace();
        }





    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
            request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

    }
}

