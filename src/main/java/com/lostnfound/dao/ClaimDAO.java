package com.lostnfound.dao;

import com.lostnfound.model.Claim;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClaimDAO {

    public boolean insertClaim(Claim claim) {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            String checkSql = "SELECT status FROM item WHERE item_id = ? FOR UPDATE";
            try (PreparedStatement ps = conn.prepareStatement(checkSql)) {
                ps.setInt(1, claim.getItemId());
                ResultSet rs = ps.executeQuery();
                if (!rs.next() || !"open".equals(rs.getString("status"))) {
                    conn.rollback();
                    return false;
                }
            }

            String insertSql = "INSERT INTO claim (item_id, claimant_name, claimant_contact, proof_description, status) VALUES (?, ?, ?, ?, 'pending')";
            try (PreparedStatement ps = conn.prepareStatement(insertSql)) {
                ps.setInt(1, claim.getItemId());
                ps.setString(2, claim.getClaimantName());
                ps.setString(3, claim.getClaimantContact());
                ps.setString(4, claim.getProofDescription());
                int rows = ps.executeUpdate();
                if (rows == 0) {
                    conn.rollback();
                    return false;
                }
            }

            String updateSql = "UPDATE item SET status = 'claimed' WHERE item_id = ?";
            try (PreparedStatement ps = conn.prepareStatement(updateSql)) {
                ps.setInt(1, claim.getItemId());
                ps.executeUpdate();
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            return false;
        } finally {
            if (conn != null) {
                try { conn.setAutoCommit(true); conn.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
    }

    public List<Claim> getPendingClaims() {
        List<Claim> claims = new ArrayList<>();
        String sql = "SELECT c.claim_id, c.item_id, i.title AS item_title, " +
                     "c.claimant_name, c.claimant_contact, c.proof_description, " +
                     "c.status, c.claimed_at " +
                     "FROM claim c " +
                     "JOIN item i ON c.item_id = i.item_id " +
                     "WHERE c.status = 'pending' " +
                     "ORDER BY c.claimed_at DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Claim c = new Claim();
                c.setClaimId(rs.getInt("claim_id"));
                c.setItemId(rs.getInt("item_id"));
                c.setItemTitle(rs.getString("item_title"));
                c.setClaimantName(rs.getString("claimant_name"));
                c.setClaimantContact(rs.getString("claimant_contact"));
                c.setProofDescription(rs.getString("proof_description"));
                c.setStatus(rs.getString("status"));
                c.setClaimedAt(rs.getTimestamp("claimed_at"));
                claims.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return claims;
    }

    public boolean updateClaimStatus(int claimId, String status, int adminId) {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            String updateClaim = "UPDATE claim SET status = ?, reviewed_by = ?, reviewed_at = NOW() WHERE claim_id = ?";
            try (PreparedStatement ps = conn.prepareStatement(updateClaim)) {
                ps.setString(1, status);
                ps.setInt(2, adminId);
                ps.setInt(3, claimId);
                int rows = ps.executeUpdate();
                if (rows == 0) { conn.rollback(); return false; }
            }

            int itemId = -1;
            String getItemId = "SELECT item_id FROM claim WHERE claim_id = ?";
            try (PreparedStatement ps = conn.prepareStatement(getItemId)) {
                ps.setInt(1, claimId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) itemId = rs.getInt("item_id");
            }

            if (itemId != -1) {
                if ("approved".equals(status)) {
                    String updateItem = "UPDATE item SET status = 'verified' WHERE item_id = ?";
                    try (PreparedStatement ps = conn.prepareStatement(updateItem)) {
                        ps.setInt(1, itemId); ps.executeUpdate();
                    }
                    String rejectOthers = "UPDATE claim SET status = 'rejected', reviewed_by = ?, reviewed_at = NOW() " +
                                          "WHERE item_id = ? AND claim_id != ? AND status = 'pending'";
                    try (PreparedStatement ps = conn.prepareStatement(rejectOthers)) {
                        ps.setInt(1, adminId); ps.setInt(2, itemId); ps.setInt(3, claimId);
                        ps.executeUpdate();
                    }
                } else {
                    String updateItem = "UPDATE item SET status = 'open' WHERE item_id = ?";
                    try (PreparedStatement ps = conn.prepareStatement(updateItem)) {
                        ps.setInt(1, itemId); ps.executeUpdate();
                    }
                }
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            return false;
        } finally {
            if (conn != null) {
                try { conn.setAutoCommit(true); conn.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        }
    }
}
