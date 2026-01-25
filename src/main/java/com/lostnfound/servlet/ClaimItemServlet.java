package com.lostnfound.servlet;

import com.lostnfound.dao.ClaimDAO;
import com.lostnfound.model.Claim;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ClaimItemServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String itemIdStr     = req.getParameter("itemId");
        String claimantName  = req.getParameter("claimantName");
        String claimantContact = req.getParameter("claimantContact");
        String proofDescription = req.getParameter("proofDescription");

        if (itemIdStr == null || claimantName == null || claimantContact == null
                || claimantName.isBlank() || claimantContact.isBlank()) {
            resp.sendRedirect(req.getContextPath() + "/items");
            return;
        }

        int itemId;
        try {
            itemId = Integer.parseInt(itemIdStr);
        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/items");
            return;
        }

        if (proofDescription == null || proofDescription.isBlank()) {
            resp.sendRedirect(req.getContextPath() + "/item?id=" + itemId + "&error=true");
            return;
        }

        Claim claim = new Claim();
        claim.setItemId(itemId);
        claim.setClaimantName(claimantName.trim());
        claim.setClaimantContact(claimantContact.trim());
        claim.setProofDescription(proofDescription.trim());

        boolean success = new ClaimDAO().insertClaim(claim);

        if (success) {
            resp.sendRedirect(req.getContextPath() + "/item?id=" + itemId + "&claimed=true");
        } else {
            resp.sendRedirect(req.getContextPath() + "/item?id=" + itemId + "&error=true");
        }
    }
}
