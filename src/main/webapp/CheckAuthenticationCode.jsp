<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Check Authentication Code</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body class="bg-light">
<div class="container d-flex justify-content-center align-items-center vh-100">
  <div class="card shadow-lg" style="width: 400px;">
    <div class="card-body text-center">
      <h3 class="card-title mb-4">Enter Authentication Code</h3>

      <%
        String error = request.getParameter("error");
        if ("true".equals(error)) {
      %>
      <div class="alert alert-danger" role="alert">
        Incorrect authentication code. Please try again.
      </div>
      <% } %>

      <form action="/verifyCode" method="post">
        <div class="mb-3">
          <input type="tel" class="form-control" name="authCode" placeholder="Enter Code" required id="authCode" pattern="\d+" title="Only numbers are allowed">
        </div>
        <button type="submit" class="btn btn-success w-100">Verify</button>
      </form>
    </div>
  </div>
</div>
</body>
</html>
