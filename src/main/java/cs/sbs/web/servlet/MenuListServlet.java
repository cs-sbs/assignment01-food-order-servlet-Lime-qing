package cs.sbs.web.servlet;

import cs.sbs.web.model.MenuItem;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class MenuListServlet extends HttpServlet {

    private static final List<MenuItem> menu = new ArrayList<>();

    static {
        menu.add(new MenuItem("Fried Rice", 8));
        menu.add(new MenuItem("Fried Noodles", 9));
        menu.add(new MenuItem("Burger", 10));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/plain; charset=UTF-8");
        PrintWriter out = resp.getWriter();

        String name = req.getParameter("name");
        List<MenuItem> results = new ArrayList<>();

        if (name == null || name.isEmpty()) {
            results.addAll(menu);
        } else {
            for (MenuItem item : menu) {
                if (item.getName().toLowerCase().contains(name.toLowerCase())) {
                    results.add(item);
                }
            }
        }

        if (results.isEmpty()) {
            out.println("No menu items found.");
            return;
        }

        out.println("Menu List:");
        out.println();
        int index = 1;
        for (MenuItem item : results) {
            out.println(index + ". " + item.getName() + " - $" + item.getPrice());
            index++;
        }
    }
}
