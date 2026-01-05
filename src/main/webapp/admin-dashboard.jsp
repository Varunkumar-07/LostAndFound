<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.lostnfound.model.Claim" %>
<%@ page import="com.lostnfound.model.Admin" %>
<%@ page import="com.lostnfound.util.HtmlUtils" %>
<%@ page import="java.util.List" %>
<%
    Admin adminUser = (Admin) session.getAttribute("adminUser");
    List<Claim> pendingClaims = (List<Claim>) request.getAttribute("pendingClaims");
    int pendingCount = pendingClaims != null ? pendingClaims.size() : 0;
    String ctx = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard — Lost &amp; Found Portal</title>
    <link rel="stylesheet" href="<%= ctx %>/css/style.css">
</head>
<body class="admin-body">
    <div class="admin-layout">
        <aside class="admin-sidebar">
            <div class="sidebar-brand">
                <span class="sidebar-logo">🔍</span>
                <span class="sidebar-title">L&amp;F Admin</span>
            </div>
            <nav class="sidebar-nav">
                <a href="<%= ctx %>/admin/dashboard" class="sidebar-link active">📋 Dashboard</a>
                <a href="<%= ctx %>/index.jsp" class="sidebar-link" target="_blank">🌐 Public Portal</a>
                <a href="<%= ctx %>/admin/logout" class="sidebar-link sidebar-logout">🚪 Logout</a>
            </nav>
            <div class="sidebar-user">
                <span class="sidebar-user-label">Signed in as</span>
                <span class="sidebar-user-name"><%= HtmlUtils.e(adminUser != null ? adminUser.getUsername() : "Admin") %></span>
            </div>
        </aside>
        <main class="admin-main">
            <div class="admin-page-header">
                <div>
                    <h1 class="admin-page-title">Dashboard</h1>
                    <p class="admin-page-subtitle">Review and manage claim submissions</p>
                </div>
            </div>
            <div class="stats-row">
                <div class="stat-card">
                    <div class="stat-number"><%= pendingCount %></div>
                    <div class="stat-label">Pending Claims</div>
                </div>
                <div class="stat-card">
                    <div class="stat-icon">⏳</div>
                    <div class="stat-label">Awaiting Review</div>
                </div>
                <div class="stat-card">
                    <div class="stat-icon">✅</div>
                    <div class="stat-label">Approve or Reject Below</div>
                </div>
            </div>
            <div class="admin-card">
                <div class="admin-card-header">
                    <h2 class="admin-card-title">Pending Claims</h2>
                    <span class="badge badge-pending"><%= pendingCount %> pending</span>
                </div>
                <% if (pendingClaims == null || pendingClaims.isEmpty()) { %>
                    <div class="empty-state">
                        <div class="empty-state-icon">🎉</div>
                        <p class="empty-state-text">All caught up — no pending claims.</p>
                    </div>
                <% } else { %>
                    <div class="table-wrapper">
                        <table class="claims-table">
                            <thead>
                                <tr>
                                    <th>Claim ID</th>
                                    <th>Item</th>
                                    <th>Claimant</th>
                                    <th>Contact</th>
                                    <th>Proof / Description</th>
                                    <th>Submitted At</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <% for (Claim claim : pendingClaims) {
                                    String proof = claim.getProofDescription();
                                    String proofPreview = (proof != null && proof.length() > 60)
                                        ? HtmlUtils.e(proof.substring(0, 60)) + "…"
                                        : HtmlUtils.e(proof);
                                %>
                                <tr>
                                    <td class="td-id">#<%= claim.getClaimId() %></td>
                                    <td class="td-item"><strong><%= HtmlUtils.e(claim.getItemTitle()) %></strong></td>
                                    <td><%= HtmlUtils.e(claim.getClaimantName()) %></td>
                                    <td><%= HtmlUtils.e(claim.getClaimantContact()) %></td>
                                    <td class="td-proof">
                                        <span title="<%= HtmlUtils.e(proof) %>">
                                            <%= proofPreview %>
                                        </span>
                                    </td>
                                    <td class="td-date">
                                        <%= claim.getClaimedAt() != null
                                            ? new java.text.SimpleDateFormat("dd MMM yyyy, HH:mm").format(claim.getClaimedAt())
                                            : "—" %>
                                    </td>
                                    <td class="td-actions">
                                        <form method="POST" action="<%= ctx %>/admin/dashboard" style="display:inline;">
                                            <input type="hidden" name="claimId" value="<%= claim.getClaimId() %>">
                                            <input type="hidden" name="action" value="approve">
                                            <button type="submit" class="btn-action btn-approve">Approve</button>
                                        </form>
                                        <form method="POST" action="<%= ctx %>/admin/dashboard" style="display:inline;">
                                            <input type="hidden" name="claimId" value="<%= claim.getClaimId() %>">
                                            <input type="hidden" name="action" value="reject">
                                            <button type="submit" class="btn-action btn-reject">Reject</button>
                                        </form>
                                    </td>
                                </tr>
                                <% } %>
                            </tbody>
                        </table>
                    </div>
                <% } %>
            </div>
        </main>
    </div>
</body>
</html>
