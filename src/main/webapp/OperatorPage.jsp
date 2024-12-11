<%@ page import="uz.pdp.hospital.entity.Patient" %>
<%@ page import="uz.pdp.hospital.servlets.Repositories" %>
<%@ page import="java.util.List" %>
<%@ page import="uz.pdp.hospital.entity.Doctor" %>
<%@ page import="uz.pdp.hospital.entity.Status" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <style>
        body {
            background-color: #f0f8ff;
        }

        h1 {
            color: #2e8b57;
        }

        .table thead {
            background-color: #4682b4;
            color: #ffffff;
        }

        .btn-dark {
            background-color: #2e8b57;
            border-color: #2e8b57;
        }

        .btn-dark:hover {
            background-color: #3cb371;
            border-color: #3cb371;
        }

        .form-control {
            border: 2px solid #4682b4;
        }

        .form-select {
            border: 2px solid #4682b4;
        }
    </style>
    <title>Operator Page</title>
</head>
<body>

<div class="container py-5">
    <div class="text-center mb-5">
        <h1 class="display-5">Operator Page</h1>
        <p class="text-muted">Manage patients and assign doctors efficiently</p>
    </div>

    <form action="" method="get" class="text-center mb-4">
        <div class="input-group w-50 mx-auto">
            <input type="text" class="form-control" placeholder="Search with phone number" name="search">
            <button class="btn btn-outline-secondary" type="submit">🔍</button>
        </div>
    </form>

    <% String search = request.getParameter("search");
        if (search == null || search.trim().isEmpty()) {
            search = "";
        }
        List<Patient> patients = Repositories.getPatients(search);
        List<Doctor> doctors = Repositories.getDoctors();
    %>

    <% if (patients.isEmpty()) { %>
    <div class="text-center mb-4">
        <a href="AddPatients.jsp">
            <button class="btn btn-dark">Add New Patient</button>
        </a>
    </div>
    <% } %>

    <div class="table-responsive">
        <table class="table table-bordered table-hover">
            <thead>
            <tr>
                <th>First Name</th>
                <th>Last Name</th>
                <th>Age</th>
                <th>Phone Number</th>
                <th>Assign Doctor</th>
            </tr>
            </thead>
            <tbody>
            <% for (Patient patient : patients) {
                if (patient.getStatus().equals(Status.NEW)) {
            %>
            <tr>
                <td><%= patient.getFirstName() %></td>
                <td><%= patient.getLastName() %></td>
                <td><%= patient.getAge() %></td>
                <td><%= patient.getPhoneNumber() %></td>
                <td>
                    <form action="/patient/send" method="post">
                        <input type="hidden" name="patientId" value="<%= patient.getId() %>">
                        <div class="input-group">
                            <select name="doctorId" class="form-select" required>
                                <option value="" disabled selected>Choose doctor</option>
                                <% for (Doctor doctor : doctors) { %>
                                <option value="<%= doctor.getId() %>"><%= doctor.getFirstName() + " " + doctor.getLastName() %></option>
                                <% } %>
                            </select>
                            <button class="btn btn-dark">Assign</button>
                        </div>
                    </form>
                </td>

            </tr>
            <%     }
            } %>
            </tbody>
        </table>
    </div>
</div>

</body>
</html>
