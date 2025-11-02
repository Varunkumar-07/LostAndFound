package com.lostnfound.servlet;

import com.lostnfound.dao.ItemDAO;
import com.lostnfound.model.Item;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ItemDetailServlet extends HttpServlet {

    private final ItemDAO itemDAO = new ItemDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String idParam = req.getParameter("id");

        if (idParam == null || idParam.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/items");
            return;
        }

        int itemId;
        try {
            itemId = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/items");
            return;
        }

        Item item = itemDAO.getItemById(itemId);

        if (item == null) {
            resp.sendRedirect(req.getContextPath() + "/items");
            return;
        }

        req.setAttribute("item", item);
        req.getRequestDispatcher("/item-detail.jsp").forward(req, resp);
    }
}
