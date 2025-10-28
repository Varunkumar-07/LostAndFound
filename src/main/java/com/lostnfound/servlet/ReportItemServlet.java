package com.lostnfound.servlet;

import com.lostnfound.dao.ItemDAO;
import com.lostnfound.model.Item;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ReportItemServlet extends HttpServlet {

    private final ItemDAO itemDAO = new ItemDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/report-item.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String title = req.getParameter("title");
        String type  = req.getParameter("type");

        if (title == null || title.isBlank() || type == null || type.isBlank()) {
            resp.sendRedirect(req.getContextPath() + "/report?error=1");
            return;
        }

        Item item = new Item();
        item.setTitle(title.trim());
        item.setCategory(req.getParameter("category"));
        item.setDescription(req.getParameter("description"));
        item.setLocationFound(req.getParameter("location"));
        item.setType(type.trim());
        item.setContact(req.getParameter("contactInfo"));

        boolean success = itemDAO.insertItem(item);
        if (success) {
            resp.sendRedirect(req.getContextPath() + "/items?success=1");
        } else {
            resp.sendRedirect(req.getContextPath() + "/report?error=1");
        }
    }
}
