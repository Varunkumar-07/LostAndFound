package com.lostnfound.dao;

import com.lostnfound.model.Admin;
import org.mindrot.jbcrypt.BCrypt;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class AdminDAO {

    public Admin validateAdmin(String username, String plainPassword) {
        String sql = "SELECT admin_id, username, password_hash, created_at FROM admin WHERE username = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("password_hash");
                boolean matched;

                if (isMd5Hash(storedHash)) {
                    matched = storedHash.equals(legacyMd5(plainPassword));
                    if (matched) {
                        String bcryptHash = BCrypt.hashpw(plainPassword, BCrypt.gensalt(12));
                        migrateHash(rs.getInt("admin_id"), bcryptHash);
                    }
                } else {
                    matched = BCrypt.checkpw(plainPassword, storedHash);
                }

                if (matched) {
                    Admin admin = new Admin();
                    admin.setAdminId(rs.getInt("admin_id"));
                    admin.setUsername(rs.getString("username"));
                    admin.setPasswordHash(storedHash);
                    admin.setCreatedAt(rs.getTimestamp("created_at"));
                    return admin;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(12));
    }

    private boolean isMd5Hash(String hash) {
        return hash != null && hash.matches("[a-f0-9]{32}");
    }

    private void migrateHash(int adminId, String bcryptHash) {
        String sql = "UPDATE admin SET password_hash = ? WHERE admin_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, bcryptHash);
            ps.setInt(2, adminId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static String legacyMd5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 not available", e);
        }
    }
}
