<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.lostnfound.model.Item" %>
<%@ page import="java.util.List" %>
<%
    List<Item> items = (List<Item>) request.getAttribute("items");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Browse Items - Lost and Found Portal</title>
    <link rel="stylesheet" href="/LostAndFound/css/style.css">
</head>
<body>
<nav class="navbar">
    <a href="/LostAndFound/index.jsp" class="navbar-brand">Lost and Found</a>
    <ul class="navbar-links">
        <li><a href="/LostAndFound/index.jsp">Home</a></li>
        <li><a href="/LostAndFound/items" class="active">Browse Items</a></li>
        <li><a href="/LostAndFound/report-item.jsp">Report Item</a></li>
        <li><a href="/LostAndFound/admin/login" class="nav-admin">Admin</a></li>
    </ul>
</nav>
<div class="page-container">
    <h1 class="page-title">Browse Items</h1>
    <p class="page-subtitle">All reported lost and found items are listed below.</p>

    <% if (items == null || items.isEmpty()) { %>
        <div class="empty-state">
            <p class="empty-state-text">No items reported yet.</p>
            <a href="/LostAndFound/report-item.jsp" class="btn btn-accent">Report the First Item</a>
        </div>
    <% } else { %>
        <div class="items-grid">
        <% for (Item item : items) {
            String type   = item.getType()   != null ? item.getType().toLowerCase()   : "lost";
            String status = item.getStatus() != null ? item.getStatus().toLowerCase() : "open";
        %>
            <div class="item-card">
                <div class="item-card-badges">
                    <span class="badge badge-<%= type %>"><%= item.getType() %></span>
                    <span class="badge badge-<%= status %>"><%= item.getStatus() %></span>
                </div>
                <h3 class="item-card-title"><%= item.getTitle() %></h3>
                <% if (item.getLocationFound() != null && !item.getLocationFound().isEmpty()) { %>
                    <p class="item-card-meta"><%= item.getLocationFound() %></p>
                <% } %>
                <% if (item.getDescription() != null && !item.getDescription().isEmpty()) { %>
                    <p class="item-card-description"><%= item.getDescription() %></p>
                <% } %>
                <a href="/LostAndFound/item?id=<%= item.getItemId() %>" class="btn btn-accent btn-full">View Details</a>
            </div>
        <% } %>
        </div>
    <% } %>
</div>
</body>
</html>
