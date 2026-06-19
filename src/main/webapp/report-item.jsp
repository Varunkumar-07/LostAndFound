<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% String ctx = request.getContextPath(); %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Report an Item — Lost &amp; Found Portal</title>
    <link rel="stylesheet" href="<%= ctx %>/css/style.css">
</head>
<body>
    <nav class="navbar">
        <a href="<%= ctx %>/index.jsp" class="navbar-brand">Lost &amp; Found</a>
        <ul class="navbar-links">
            <li><a href="<%= ctx %>/index.jsp">Home</a></li>
            <li><a href="<%= ctx %>/items">Browse Items</a></li>
            <li><a href="<%= ctx %>/report-item.jsp" class="active">Report Item</a></li>
            <li><a href="<%= ctx %>/admin/login" class="nav-admin">Admin</a></li>
        </ul>
    </nav>
    <div class="page-container">
        <h1 class="page-title">Report an Item</h1>
        <p class="page-subtitle">Fill in the details below to report a lost or found item.</p>
        <div class="form-card">
            <h2 class="form-card-title">Item Details</h2>
            <form method="POST" action="<%= ctx %>/report">
                <div class="form-group">
                    <label class="form-label">Item Type</label>
                    <div class="form-radio-group">
                        <label class="form-radio-label"><input type="radio" name="type" value="lost" required> Lost</label>
                        <label class="form-radio-label"><input type="radio" name="type" value="found"> Found</label>
                    </div>
                </div>
                <div class="form-group">
                    <label for="title" class="form-label">Item Title</label>
                    <input type="text" id="title" name="title" class="form-input" placeholder="e.g. Blue backpack with laptop" required>
                </div>
                <div class="form-group">
                    <label for="category" class="form-label">Category</label>
                    <select id="category" name="category" class="form-select" required>
                        <option value="" disabled selected>Select a category</option>
                        <option value="Electronics">Electronics</option>
                        <option value="Clothing">Clothing</option>
                        <option value="Bags">Bags &amp; Luggage</option>
                        <option value="Jewellery">Jewellery &amp; Accessories</option>
                        <option value="Documents">Documents &amp; IDs</option>
                        <option value="Keys">Keys</option>
                        <option value="Pets">Pets</option>
                        <option value="Other">Other</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="description" class="form-label">Description</label>
                    <textarea id="description" name="description" class="form-textarea" placeholder="Describe the item..." required></textarea>
                </div>
                <div class="form-group">
                    <label for="location" class="form-label">Location</label>
                    <input type="text" id="location" name="location" class="form-input" placeholder="e.g. Library, Block A" required>
                </div>
                <div class="form-group">
                    <label for="reportedBy" class="form-label">Reported By</label>
                    <input type="text" id="reportedBy" name="reportedBy" class="form-input" placeholder="Your name">
                </div>
                <div class="form-group">
                    <label for="contact" class="form-label">Your Contact Info</label>
                    <input type="text" id="contact" name="contact" class="form-input" placeholder="Phone number or email address" required>
                </div>
                <button type="submit" class="btn btn-accent btn-full btn-lg" style="margin-top: 8px;">Submit Report</button>
            </form>
        </div>
    </div>
</body>
</html>
