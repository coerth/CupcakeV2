package dat.startcode.control;

import dat.startcode.model.config.ApplicationStart;

import dat.startcode.model.entities.Bottom;
import dat.startcode.model.entities.CupcakeOrder;
import dat.startcode.model.entities.Topping;
import dat.startcode.model.persistence.ConnectionPool;
import dat.startcode.model.persistence.OrderMapper;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "AddToCart", value = "/AddToCart")
public class AddToCart extends HttpServlet {

    private ConnectionPool connectionPool;

    private OrderMapper orderMapper;

    @Override
    public void init() throws ServletException
    {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {




    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();

        ArrayList<CupcakeOrder> cupcakeOrderArrayList = (ArrayList<CupcakeOrder>) session.getAttribute("cupcakeOrderArrayList");

        ArrayList<Bottom> bottomArrayList = (ArrayList<Bottom>) getServletContext().getAttribute("bottomArrayList");
        ArrayList<Topping> toppingArrayList = (ArrayList<Topping>) getServletContext().getAttribute("toppingArrayList");



        if(cupcakeOrderArrayList == null) {
            cupcakeOrderArrayList = new ArrayList<>();
        }

        int bottom_id = Integer.parseInt(request.getParameter("bottomID"));
        int topping_id = Integer.parseInt(request.getParameter("toppingID"));
        int amount = Integer.parseInt(request.getParameter("amount"));


        Bottom bottom = null;
        Topping topping = null;

        for (Bottom b  : bottomArrayList) {
            if(b.getBottomID() == bottom_id) {
                bottom =b;
            }

        }


        for (Topping t  : toppingArrayList) {
            if(t.getToppingID() == topping_id) {
                topping =t;
            }

        }



        CupcakeOrder cupcakeOrder = new CupcakeOrder(amount,bottom,topping);


        cupcakeOrderArrayList.add(cupcakeOrder);

        String msg ="Dine cupcakes er nu lagt i kurven!";

        session.setAttribute("cupcakeOrderArrayList", cupcakeOrderArrayList);

        request.setAttribute("msg", msg);
        request.getRequestDispatcher("index.jsp").forward(request,response);


    }

}
