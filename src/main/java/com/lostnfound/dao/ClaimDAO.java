package com.lostnfound.dao;

import com.lostnfound.model.Claim;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClaimDAO {

    public boolean insertClaim(Claim claim) {
        String insertSql = "INSERT INTO claim (item_id, claimant_name, claimant_contact, proof_description, status) VALUES (?, ?, ?, ?, 'pending')";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(insertSql)) {
            ps.setInt(1, claim.getItemId());
            ps.setString(2, claim.getClaimantName());
            ps.setString(3, claim.getClaimantContact());
            ps.setString(4, claim.getProofDescription());
            if (ps.executeUpdate() > 0) {
                String updateSql = "UPDATE item SET status = 'claimed' WHERE item_id = ?";
                try (PreparedStatement ups = conn.prepareStatement(updateSql)) {
                    ups.setInt(1, claim.getItemId());
                    ups.executeUpdate();
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
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
        String sql = "UPDATE claim SET status = ?, reviewed_by = ?, reviewed_at = NOW() WHERE claim_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, adminId);
            ps.setInt(3, claimId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
