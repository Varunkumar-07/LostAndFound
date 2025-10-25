package com.lostnfound.servlet;

import com.lostnfound.dao.ItemDAO;
import com.lostnfound.model.Item;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class ViewItemsServlet extends HttpServlet {

    private final ItemDAO itemDAO = new ItemDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String category = req.getParameter("category");
        String type = req.getParameter("type");

        List<Item> items;

        if (category != null && !category.isEmpty() && type != null && !type.isEmpty() && !type.equals("all")) {
            items = itemDAO.getItemsByCategoryAndType(category, type);
        } else if (category != null && !category.isEmpty()) {
            items = itemDAO.getItemsByCategory(category);
        } else if (type != null && !type.isEmpty() && !type.equals("all")) {
            items = itemDAO.getItemsByType(type);
        } else {
            items = itemDAO.getAllItems();
        }

        req.setAttribute("items", items);
        req.setAttribute("selectedCategory", category != null ? category : "");
        req.setAttribute("selectedType", type != null ? type : "all");

        req.getRequestDispatcher("/view-items.jsp").forward(req, resp);
    }
}
