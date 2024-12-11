<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
  <title>Add Patients</title>
  <style>
    body {
      background-color: #f2f9f3;
    }

    .card {
      border: 2px solid #4caf50;
      border-radius: 10px;
      box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    }

    .btn-primary {
      background-color: #4caf50;
      border-color: #4caf50;
    }

    .btn-primary:hover {
      background-color: #388e3c;
      border-color: #388e3c;
    }

    .text-primary {
      color: #388e3c !important;
    }

    .form-control.is-invalid {
      border-color: #ff5252;
      background-color: #ffe6e6;
    }

    .text-danger {
      color: #d32f2f !important;
    }
  </style>
</head>
<body>
<div class="container d-flex justify-content-center align-items-center" style="min-height: 100vh;">
  <div class="card p-4" style="width: 100%; max-width: 500px;">
    <h2 class="text-center mb-4 text-primary">Add New Patient</h2>

    <%
      Object object = request.getAttribute("errors");
      Map<String, String> errors = new HashMap<>();
      if (object != null) {
        errors = (Map<String, String>) object;
      }
    %>

    <form action="/patients/add" method="post">
      <div class="mb-3">
        <input type="text"
               class="form-control <%= errors.containsKey("firstName") ? "is-invalid" : "" %>"
               id="firstName"
               name="firstName"
               placeholder="First Name"
               value="<%= request.getParameter("firstName") != null ? request.getParameter("firstName") : "" %>">
        <span class="text-danger small d-block mt-1"><%= errors.get("firstName") %></span>
      </div>


      <div class="mb-3">
        <input type="text" class="form-control <%= errors.containsKey("lastName") ? "is-invalid" : "" %>"
               id="lastName" name="lastName" placeholder="Last Name">
        <span class="text-danger small d-block mt-1"><%= errors.get("lastName") %></span>
      </div>

      <div class="mb-3">
        <input type="text" class="form-control <%= errors.containsKey("age") ? "is-invalid" : "" %>"
               id="age" name="age" placeholder="Age">
        <span class="text-danger small d-block mt-1"><%= errors.get("age") %></span>
      </div>

      <div class="mb-3">
        <input type="text" class="form-control <%= errors.containsKey("phoneNumber") ? "is-invalid" : "" %>"
               id="phoneNumber" name="phoneNumber" placeholder="Phone Number">
        <span class="text-danger small d-block mt-1"><%= errors.get("phoneNumber") %></span>
      </div>

      <button type="submit" class="btn btn-primary w-100">Enter</button>
    </form>
  </div>
</div>
</body>
</html>
