<%@ page import="uz.pdp.hospital.entity.Doctor" %>
<%@ page import="uz.pdp.hospital.entity.Patient" %>
<%@ page import="uz.pdp.hospital.servlets.Repositories" %>
<%@ page import="java.util.List" %>
<%@ page import="uz.pdp.hospital.entity.Status" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.time.LocalDateTime" %>

<%@ page import="java.text.SimpleDateFormat" %><%--
  Created by IntelliJ IDEA.
  User: user
  Date: 12/11/2024
  Time: 3:24 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <title>Report</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <style>
        body {
            background-color: #f0f9ff;
            font-family: Arial, sans-serif;
        }
        .container {
            margin-top: 30px;
        }
        .table {
            background-color: #ffffff;
            border-radius: 10px;
            overflow: hidden;
        }
        .table th {
            background-color: #d0e8f2;
            color: #003366;
        }
        .table td {
            color: #003366;
        }
        .btn-clear {
            background-color: #28a745;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 5px;
            font-weight: bold;
            cursor: pointer;
        }
        .btn-clear:hover {
            background-color: #218838;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h1 class="text-primary">Doctor's Report</h1>

        <form action="DoctorPage.jsp">
            <button class="btn btn-clear">Back</button>
        </form>


    </div>
    <%
        HttpSession session1 = request.getSession();
        Doctor doctor = (Doctor) session1.getAttribute("doctor");
        int page1 = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
        int limit = 10;
        int offset = (page1 - 1) * limit;
        List<Patient> patients = Repositories.getPatientsForReport(doctor.getId(), limit, offset);
    %>

    <table class="table table-bordered table-striped">
        <thead>
        <tr>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Age</th>
            <th>Phone Number</th>
            <th>Checked date</th>
        </tr>
        </thead>
        <tbody>
        <%
            for (Patient patient : patients) {
                if (patient.getStatus().equals(Status.COMPLETED)) {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        %>
        <tr>
            <td><%= patient.getFirstName() %></td>
            <td><%= patient.getLastName() %></td>
            <td><%= patient.getAge() %></td>
            <td><%= patient.getPhoneNumber() %></td>
            <td><%= patient.getCheckedDate() != null ? patient.getCheckedDate().format(dateFormatter) : "N/A" %></td>
        </tr>


        <%
                }
            }
        %>
        </tbody>
    </table>
</div>
<%
    Long count = Repositories.countPatients(doctor.getId());
    double pageCount = Math.ceil(count / 10.0);
%>
<div class="container mt-4">
    <nav aria-label="Page navigation">
        <ul class="pagination justify-content-center">
            <% for (int i = 1; i <= pageCount; i++) { %>
            <li class="page-item <%= (i == 1 ? "active" : "") %>">
                <a class="page-link" href="?page=<%= i %>"><%= i %></a>
            </li>
            <% } %>
        </ul>
    </nav>
</div>

</body>
</html>
