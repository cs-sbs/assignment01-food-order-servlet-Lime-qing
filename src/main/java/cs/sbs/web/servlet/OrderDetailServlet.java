package cs.sbs.web.servlet;

import cs.sbs.web.model.Order;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class OrderDetailServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/plain; charset=UTF-8");
        PrintWriter out = resp.getWriter();

        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            resp.setStatus(404);
            out.println("Error: order not found");
            return;
        }

        int orderId;
        try {
            orderId = Integer.parseInt(pathInfo.substring(1));
        } catch (NumberFormatException e) {
            resp.setStatus(404);
            out.println("Error: order not found");
            return;
        }

        ServletContext ctx = getServletContext();
        @SuppressWarnings("unchecked")
        List<Order> orders = (List<Order>) ctx.getAttribute("orders");

        if (orders != null) {
            for (Order order : orders) {
                if (order.getId() == orderId) {
                    out.println("Order Detail");
                    out.println();
                    out.println("Order ID: " + order.getId());
                    out.println("Customer: " + order.getCustomer());
                    out.println("Food: " + order.getFood());
                    out.println("Quantity: " + order.getQuantity());
                    return;
                }
            }
        }

        resp.setStatus(404);
        out.println("Error: order not found");
    }
}
