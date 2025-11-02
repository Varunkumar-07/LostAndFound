<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.lostnfound.model.Item" %>
<%
    Item item = (Item) request.getAttribute("item");
    String dateStr = (item != null && item.getDateReported() != null) ? item.getDateReported().toString() : "";
    String type    = (item != null && item.getType()   != null) ? item.getType().toLowerCase()   : "lost";
    String status  = (item != null && item.getStatus() != null) ? item.getStatus().toLowerCase() : "open";
    String ctx     = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Item Detail - Lost and Found Portal</title>
    <link rel="stylesheet" href="<%= ctx %>/css/style.css">
</head>
<body>
<nav class="navbar">
    <a href="<%= ctx %>/index.jsp" class="navbar-brand">Lost and Found</a>
    <ul class="navbar-links">
        <li><a href="<%= ctx %>/index.jsp">Home</a></li>
        <li><a href="<%= ctx %>/items">Browse Items</a></li>
        <li><a href="<%= ctx %>/report-item.jsp">Report Item</a></li>
        <li><a href="<%= ctx %>/admin/login" class="nav-admin">Admin</a></li>
    </ul>
</nav>
<div class="page-container">
<% if (item == null) { %>
    <p>Item not found. <a href="<%= ctx %>/items">Go back</a></p>
<% } else { %>
    <div class="detail-layout">
        <div class="detail-card">
            <div class="item-card-badges">
                <span class="badge badge-<%= type %>"><%= item.getType() %></span>
                <span class="badge badge-<%= status %>"><%= item.getStatus() %></span>
                <% if (item.getCategory() != null && !item.getCategory().isEmpty()) { %>
                    <span class="badge badge-category"><%= item.getCategory() %></span>
                <% } %>
            </div>
            <h1 class="page-title"><%= item.getTitle() %></h1>
            <% if (item.getDescription() != null && !item.getDescription().isEmpty()) { %>
                <p class="item-card-description"><%= item.getDescription() %></p>
            <% } %>
            <% if (item.getLocationFound() != null && !item.getLocationFound().isEmpty()) { %>
                <p class="item-card-meta">Location: <%= item.getLocationFound() %></p>
            <% } %>
            <% if (!dateStr.isEmpty()) { %>
                <p class="item-card-meta">Date: <%= dateStr %></p>
            <% } %>
            <% if (item.getReportedBy() != null && !item.getReportedBy().isEmpty()) { %>
                <p class="item-card-meta">Reported by: <%= item.getReportedBy() %></p>
            <% } %>
            <% if (item.getContact() != null && !item.getContact().isEmpty()) { %>
                <p class="item-card-meta">Contact: <%= item.getContact() %></p>
            <% } %>
        </div>
        <div class="detail-card">
            <h2 class="page-title" style="font-size:1.4rem;">Submit a Claim</h2>
            <form action="<%= ctx %>/claim" method="post">
                <input type="hidden" name="itemId" value="<%= item.getItemId() %>">
                <div class="form-group">
                    <label class="form-label">Your Name</label>
                    <input type="text" name="claimantName" class="form-input" required>
                </div>
                <div class="form-group">
                    <label class="form-label">Your Contact</label>
                    <input type="text" name="claimantContact" class="form-input" required>
                </div>
                <div class="form-group">
                    <label class="form-label">Proof of Ownership</label>
                    <textarea name="proofDescription" class="form-input" rows="4" required></textarea>
                </div>
                <button type="submit" class="btn btn-accent btn-full">Submit Claim</button>
            </form>
        </div>
    </div>
<% } %>
</div>
</body>
</html>
