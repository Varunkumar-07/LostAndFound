package com.lostnfound.servlet;

import com.lostnfound.dao.AdminDAO;
import com.lostnfound.model.Admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class AdminLoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/admin-login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (username == null || password == null || username.isBlank() || password.isBlank()) {
            resp.sendRedirect(req.getContextPath() + "/admin/login?error=true");
            return;
        }

        AdminDAO adminDAO = new AdminDAO();
        Admin admin = adminDAO.validateAdmin(username, password);

        if (admin != null) {
            HttpSession session = req.getSession(true);
            session.setAttribute("adminUser", admin);
            session.setMaxInactiveInterval(30 * 60);
            resp.sendRedirect(req.getContextPath() + "/admin/dashboard");
        } else {
            resp.sendRedirect(req.getContextPath() + "/admin/login?error=true");
        }
    }
}
