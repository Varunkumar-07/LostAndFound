package com.lostnfound.servlet;

import com.lostnfound.dao.ClaimDAO;
import com.lostnfound.model.Admin;
import com.lostnfound.model.Claim;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

public class AdminDashboardServlet extends HttpServlet {

    private boolean isAuthenticated(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        return session != null && session.getAttribute("adminUser") != null;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if (!isAuthenticated(req)) {
            resp.sendRedirect(req.getContextPath() + "/admin/login");
            return;
        }

        ClaimDAO claimDAO = new ClaimDAO();
        List<Claim> pendingClaims = claimDAO.getPendingClaims();
        req.setAttribute("pendingClaims", pendingClaims);
        req.setAttribute("pendingCount", pendingClaims.size());

        req.getRequestDispatcher("/admin-dashboard.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if (!isAuthenticated(req)) {
            resp.sendRedirect(req.getContextPath() + "/admin/login");
            return;
        }

        // CSRF validation
        HttpSession session = req.getSession(false);
        String csrfToken   = (String) session.getAttribute("csrfToken");
        String requestToken = req.getParameter("_csrf");
        if (csrfToken == null || !csrfToken.equals(requestToken)) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "CSRF validation failed");
            return;
        }

        String claimIdStr = req.getParameter("claimId");
        String action     = req.getParameter("action");

        if (claimIdStr != null && action != null) {
            int claimId;
            try {
                claimId = Integer.parseInt(claimIdStr);
            } catch (NumberFormatException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid claim ID");
                return;
            }

            String status = "approve".equals(action) ? "approved" : "rejected";
            Admin admin = (Admin) session.getAttribute("adminUser");
            new ClaimDAO().updateClaimStatus(claimId, status, admin.getAdminId());
        }

        resp.sendRedirect(req.getContextPath() + "/admin/dashboard");
    }
}
