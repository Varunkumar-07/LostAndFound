<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% String ctx = request.getContextPath(); %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Login — Lost &amp; Found Portal</title>
    <link rel="stylesheet" href="<%= ctx %>/css/style.css">
</head>

<body class="admin-login-page">

    <div class="admin-login-wrapper">
        <div class="admin-login-card">
            <div class="admin-login-header">
                <div class="admin-logo">🔍</div>
                <h1 class="admin-login-title">Lost &amp; Found</h1>
                <p class="admin-login-subtitle">Admin Portal</p>
            </div>

            <!-- Error banner (shown via JS if ?error=true) -->
            <div class="alert alert-error" id="errorBanner" style="display:none;">
                Invalid username or password. Please try again.
            </div>

            <form class="admin-login-form" method="POST" action="<%= ctx %>/admin/login">
                <div class="form-group">
                    <label for="username" class="form-label">Username</label>
                    <input type="text" id="username" name="username" class="form-input" placeholder="Enter username"
                        required autocomplete="username">
                </div>

                <div class="form-group">
                    <label for="password" class="form-label">Password</label>
                    <input type="password" id="password" name="password" class="form-input" placeholder="Enter password"
                        required autocomplete="current-password">
                </div>

                <button type="submit" class="btn btn-accent btn-full btn-lg">
                    Sign In
                </button>
            </form>

            <p class="admin-login-back">
                <a href="<%= ctx %>/index.jsp">← Back to public portal</a>
            </p>
        </div>
    </div>

    <script>
        const params = new URLSearchParams(window.location.search);
        if (params.get('error') === 'true') {
            document.getElementById('errorBanner').style.display = 'block';
        }
    </script>
</body>

</html>
