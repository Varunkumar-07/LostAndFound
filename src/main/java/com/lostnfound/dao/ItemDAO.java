package com.lostnfound.dao;

import com.lostnfound.model.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO {

    private static final Logger log = LoggerFactory.getLogger(ItemDAO.class);

    public boolean insertItem(Item item) {
        String sql = "INSERT INTO item (title, category, description, location_found, date_reported, type, status, reported_by, contact) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, item.getTitle());
            stmt.setString(2, item.getCategory());
            stmt.setString(3, item.getDescription());
            stmt.setString(4, item.getLocationFound());
            stmt.setDate(5, item.getDateReported() != null ? Date.valueOf(item.getDateReported()) : null);
            stmt.setString(6, item.getType());
            stmt.setString(7, item.getStatus() != null ? item.getStatus() : "open");
            stmt.setString(8, item.getReportedBy());
            stmt.setString(9, item.getContact());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            log.error("Error inserting item '{}'", item.getTitle(), e);
            return false;
        }
    }

    public List<Item> getAllItems() {
        String sql = "SELECT * FROM item WHERE status = 'open' ORDER BY created_at DESC";
        return queryItems(sql);
    }

    public List<Item> getItemsByCategory(String category) {
        String sql = "SELECT * FROM item WHERE status = 'open' AND category = ? ORDER BY created_at DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, category);
            return executeQuery(stmt);
        } catch (SQLException e) {
            log.error("Error fetching items by category '{}'", category, e);
            return new ArrayList<>();
        }
    }

    public List<Item> getItemsByType(String type) {
        String sql = "SELECT * FROM item WHERE status = 'open' AND type = ? ORDER BY created_at DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, type);
            return executeQuery(stmt);
        } catch (SQLException e) {
            log.error("Error fetching items by type '{}'", type, e);
            return new ArrayList<>();
        }
    }

    public List<Item> getItemsByCategoryAndType(String category, String type) {
        String sql = "SELECT * FROM item WHERE status = 'open' AND category = ? AND type = ? ORDER BY created_at DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, category);
            stmt.setString(2, type);
            return executeQuery(stmt);
        } catch (SQLException e) {
            log.error("Error fetching items by category '{}' and type '{}'", category, type, e);
            return new ArrayList<>();
        }
    }

    public Item getItemById(int id) {
        String sql = "SELECT * FROM item WHERE item_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToItem(rs);
                }
            }
        } catch (SQLException e) {
            log.error("Error fetching item {}", id, e);
        }
        return null;
    }

    private List<Item> queryItems(String sql) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            return executeQuery(stmt);
        } catch (SQLException e) {
            log.error("Error querying items", e);
            return new ArrayList<>();
        }
    }

    private List<Item> executeQuery(PreparedStatement stmt) throws SQLException {
        List<Item> items = new ArrayList<>();
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                items.add(mapResultSetToItem(rs));
            }
        }
        return items;
    }

    private Item mapResultSetToItem(ResultSet rs) throws SQLException {
        Item item = new Item();
        item.setItemId(rs.getInt("item_id"));
        item.setTitle(rs.getString("title"));
        item.setCategory(rs.getString("category"));
        item.setDescription(rs.getString("description"));
        item.setLocationFound(rs.getString("location_found"));

        Date dateReported = rs.getDate("date_reported");
        item.setDateReported(dateReported != null ? dateReported.toLocalDate() : null);

        item.setType(rs.getString("type"));
        item.setStatus(rs.getString("status"));
        item.setReportedBy(rs.getString("reported_by"));
        item.setContact(rs.getString("contact"));
        item.setCreatedAt(rs.getTimestamp("created_at"));

        return item;
    }
}
