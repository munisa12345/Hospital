<%@ page import="jakarta.mail.Session" %>
<%@ page import="uz.pdp.hospital.entity.Doctor" %>
<%@ page import="uz.pdp.hospital.servlets.Repositories" %>
<%@ page import="uz.pdp.hospital.entity.Patient" %>
<%@ page import="java.util.List" %>
<%@ page import="uz.pdp.hospital.entity.Status" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Doctor Page</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css" rel="stylesheet">

    <style>
        body {
            background-color: #f0f8ff; 
        }

        .profile-img {
            border-radius: 50%;
            width: 150px;
            height: 150px;
            object-fit: cover;
        }

        .doctor-info {
            background-color: #eef0fa;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }

        .doctor-info h3 {
            color: #28a745;
            font-style: italic;
        }

        .table th, .table td {
            text-align: center;
        }

        .table {
            margin-top: 20px;
        }

        .btn-accept {
            background-color: #28a745;
            color: white;
            border: none;
            padding: 5px 10px;
            border-radius: 5px;
            cursor: pointer;
        }

        .btn-accept:hover {
            background-color: #218838;
        }
    </style>
</head>
<body>

<div class="container mt-5">
    <%
        HttpSession session1 = request.getSession();
        Doctor doctor = (Doctor)session1.getAttribute("doctor");
        List<Patient> patients = Repositories.getPatientsForDoctor(doctor.getId());
    %>

    <div class="row">
        <div class="col-md-4 text-center">
            <img src="<%= request.getContextPath() %>/images/profilePhoto.jpg" alt="profile photo" class="profile-img">
        </div>
        <div class="col-md-8">
            <div class="doctor-info">
                <h1><%= doctor.getFirstName() + " " + doctor.getLastName() %></h1>
                <h3><%= doctor.getSpecialization() %></h3>
                <p><strong>Age:</strong> <%= doctor.getAge() %></p>
                <p><strong>Start Time:</strong> <%= doctor.getStartTime() %></p>
                <p><strong>End Time:</strong> <%= doctor.getEndTime() %></p>
                <div class="gender-info mt-3">
                    <strong>Gender:</strong>
                    <%
                        if (doctor.getGender().equals("M")) {
                    %>
                    <span class="text-primary">
            <i class="bi bi-gender-male" style="font-size: 24px;"></i>
        </span>
                    <%
                    } else if (doctor.getGender().equals("F")) {
                    %>
                    <span class="text-danger">
            <i class="bi bi-gender-female" style="font-size: 24px;"></i>
        </span>
                    <%
                        }
                    %>
                </div>

                <div class="d-flex justify-content-end">
                    <form action="/logout" method="post">
                        <button class="btn btn-primary mx-2">Logout</button>
                    </form>
                    <form action="Report.jsp" method="post">
                        <button class="btn btn-primary mx-2">Report</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <h2 class="mt-5">Patients</h2>
    <table class="table table-bordered table-striped">
        <thead>
        <tr>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Age</th>
            <th>Phone Number</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
        <%
            for (Patient patient : patients) {
                if (patient.getStatus().equals(Status.CHECKED)) {
        %>
        <tr>
            <td><%= patient.getFirstName() %></td>
            <td><%= patient.getLastName() %></td>
            <td><%= patient.getAge() %></td>
            <td><%= patient.getPhoneNumber() %></td>
            <td>
                <form action="/acceptance" method="post">
                    <input type="hidden" name="patientId" value="<%=patient.getId()%>">
                    <button class="btn-accept">Acceptance</button>
                </form>
            </td>
        </tr>
        <%
                }
            }
        %>
        </tbody>
    </table>
</div>

</body>
</html>