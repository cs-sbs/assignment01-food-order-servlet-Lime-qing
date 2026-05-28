package cs.sbs.web.servlet;

import cs.sbs.web.model.Order;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class OrderCreateServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/plain; charset=UTF-8");
        PrintWriter out = resp.getWriter();

        String customer = req.getParameter("customer");
        String food = req.getParameter("food");
        String quantityStr = req.getParameter("quantity");

        if (customer == null || customer.isEmpty()
                || food == null || food.isEmpty()
                || quantityStr == null || quantityStr.isEmpty()) {
            resp.setStatus(400);
            out.println("Error: missing required parameter");
            return;
        }

        int quantity;
        try {
            quantity = Integer.parseInt(quantityStr);
        } catch (NumberFormatException e) {
            resp.setStatus(400);
            out.println("Error: quantity must be a valid number");
            return;
        }

        ServletContext ctx = getServletContext();
        synchronized (ctx) {
            @SuppressWarnings("unchecked")
            List<Order> orders = (List<Order>) ctx.getAttribute("orders");
            if (orders == null) {
                orders = new ArrayList<>();
                ctx.setAttribute("orders", orders);
            }

            Integer counter = (Integer) ctx.getAttribute("orderCounter");
            if (counter == null) {
                counter = 1000;
            }
            counter = counter + 1;
            ctx.setAttribute("orderCounter", counter);

            Order order = new Order(counter, customer, food, quantity);
            orders.add(order);

            out.println("Order Created: " + counter);
        }
    }
}
