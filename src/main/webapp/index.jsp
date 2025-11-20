<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% String ctx = request.getContextPath(); %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lost & Found Portal</title>
    <link rel="stylesheet" href="<%= ctx %>/css/style.css">
</head>

<body>

    <!-- ── Navbar ── -->
    <nav class="navbar">
        <a href="<%= ctx %>/index.jsp" class="navbar-brand">
            <span class="navbar-logo">🔍</span>
            Lost &amp; Found
        </a>
        <ul class="navbar-links">
            <li><a href="<%= ctx %>/index.jsp" class="active">Home</a></li>
            <li><a href="<%= ctx %>/items">Browse Items</a></li>
            <li><a href="<%= ctx %>/report-item.jsp">Report Item</a></li>
            <li><a href="<%= ctx %>/admin/login" class="nav-admin">Admin</a></li>
        </ul>
    </nav>

    <!-- ── Hero ── -->
    <div style="background: var(--primary); color: #fff; padding: 72px 24px; text-align: center;">
        <h1 style="font-size: 36px; font-weight: 600; margin-bottom: 14px;">
            Reuniting People with Their Belongings
        </h1>
        <p style="font-size: 17px; color: rgba(255,255,255,0.75); max-width: 520px; margin: 0 auto 32px;">
            Browse lost and found items, report something you've found, or claim something you've lost.
        </p>
        <div style="display: flex; gap: 14px; justify-content: center; flex-wrap: wrap;">
            <a href="<%= ctx %>/items" class="btn btn-accent" style="font-size: 15px; padding: 12px 28px;">
                Browse Items
            </a>
            <a href="<%= ctx %>/report-item.jsp" class="btn btn-outline"
                style="font-size: 15px; padding: 12px 28px; color: #fff; border-color: rgba(255,255,255,0.4);">
                Report an Item
            </a>
        </div>
    </div>

    <!-- ── How It Works ── -->
    <div class="page-container">
        <h2 class="page-title" style="text-align:center; margin-bottom: 8px;">How It Works</h2>
        <p class="page-subtitle" style="text-align:center; margin-bottom: 36px;">Three simple steps to recover your lost
            item</p>

        <div style="display: grid; grid-template-columns: repeat(auto-fit, minmax(220px, 1fr)); gap: 20px;">

            <div class="detail-card" style="text-align:center;">
                <div style="font-size: 36px; margin-bottom: 12px;">📋</div>
                <h3 style="font-size: 16px; font-weight: 600; margin-bottom: 8px;">1. Report</h3>
                <p style="font-size: 14px; color: var(--text-secondary);">
                    Found something? Submit it so the owner can find it.
                </p>
            </div>

            <div class="detail-card" style="text-align:center;">
                <div style="font-size: 36px; margin-bottom: 12px;">🔎</div>
                <h3 style="font-size: 16px; font-weight: 600; margin-bottom: 8px;">2. Browse</h3>
                <p style="font-size: 14px; color: var(--text-secondary);">
                    Lost something? Browse all reported items and find yours.
                </p>
            </div>

            <div class="detail-card" style="text-align:center;">
                <div style="font-size: 36px; margin-bottom: 12px;">✅</div>
                <h3 style="font-size: 16px; font-weight: 600; margin-bottom: 8px;">3. Claim</h3>
                <p style="font-size: 14px; color: var(--text-secondary);">
                    Submit proof of ownership and get your item back.
                </p>
            </div>

        </div>

        <div style="text-align:center; margin-top: 40px;">
            <a href="<%= ctx %>/items" class="btn btn-accent btn-lg" style="padding: 0 36px;">
                View All Items →
            </a>
        </div>
    </div>

</body>

</html>
