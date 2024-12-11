<%-- Created by IntelliJ IDEA.
User: user
Date: 12/10/2024
Time: 2:15 PM
To change this template use File | Settings | File Templates. --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
  <title>Login</title>
</head>
<body class="bg-light d-flex justify-content-center align-items-center vh-100">

<div class="card shadow-lg" style="width: 24rem;">
  <div class="card-body">
    <h3 class="card-title text-center mb-4">Login</h3>
    <form action="/login/check" method="post">
      <div class="mb-3">
        <label for="phoneNumber" class="form-label">Phone Number</label>
        <input type="text" class="form-control" id="phoneNumber" name="phoneNumber"
               placeholder="Enter your phone number"
               required
               pattern="^9989[0-9]{8}$"
               title="Please enter correct phone number">
      </div>
      <div class="mb-3">
        <label for="email" class="form-label">Email</label>
        <input type="email" class="form-control" id="email" name="email"
               placeholder="Enter your email"
               required
               title="Please enter a valid email address. Example: example@gmail.com">
      </div>
      <button type="submit" class="btn btn-primary w-100">Enter</button>
    </form>
  </div>
</div>

</body>
</html>
