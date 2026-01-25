package com.lostnfound.servlet;

import com.lostnfound.dao.ItemDAO;
import com.lostnfound.model.Item;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ReportItemServlet extends HttpServlet {

    private static final Set<String> VALID_CATEGORIES = new HashSet<>(Arrays.asList(
        "Electronics", "Clothing", "Bags", "Jewellery", "Documents", "Keys", "Pets", "Other"
    ));

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
        String type = req.getParameter("type");
        String category = req.getParameter("category");

        if (title == null || title.isBlank() || type == null || type.isBlank()) {
            resp.sendRedirect(req.getContextPath() + "/report?error=1");
            return;
        }

        if (!type.equals("lost") && !type.equals("found")) {
            resp.sendRedirect(req.getContextPath() + "/report?error=1");
            return;
        }

        if (category == null || !VALID_CATEGORIES.contains(category)) {
            resp.sendRedirect(req.getContextPath() + "/report?error=1");
            return;
        }

        Item item = new Item();
        item.setTitle(title.trim());
        item.setCategory(category);
        item.setDescription(req.getParameter("description"));
        item.setLocationFound(req.getParameter("location"));
        item.setType(type.trim());
        item.setReportedBy(req.getParameter("reportedBy"));
        item.setContact(req.getParameter("contact"));

        String dateStr = req.getParameter("date");
        if (dateStr != null && !dateStr.isBlank()) {
            try {
                item.setDateReported(LocalDate.parse(dateStr));
            } catch (DateTimeParseException e) {
                item.setDateReported(null);
            }
        }

        boolean success = itemDAO.insertItem(item);

        if (success) {
            resp.sendRedirect(req.getContextPath() + "/items?success=1");
        } else {
            resp.sendRedirect(req.getContextPath() + "/report?error=1");
        }
    }
}
