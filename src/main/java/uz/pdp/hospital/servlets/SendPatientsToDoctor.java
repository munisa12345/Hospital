package uz.pdp.hospital.servlets;

import jakarta.persistence.EntityManager;
import uz.pdp.hospital.entity.Doctor;
import uz.pdp.hospital.entity.Patient;
import uz.pdp.hospital.entity.Status;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import static uz.pdp.hospital.MyListener.EMF;


@WebServlet("/patient/send")
public class SendPatientsToDoctor extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer patientId = Integer.parseInt(req.getParameter("patientId"));
        Integer doctorId = Integer.parseInt(req.getParameter("doctorId"));

        try (
                EntityManager entityManager = EMF.createEntityManager();
        ){
            entityManager.getTransaction().begin();

            Patient patient = entityManager.find(Patient.class, patientId);
            Doctor doctor = entityManager.find(Doctor.class, doctorId);

            patient.setDoctor(doctor);
            patient.setStatus(Status.CHECKED);
            patient.setCheckedDate(LocalDateTime.now());
            entityManager.merge(patient);

            entityManager.getTransaction().commit();
            resp.sendRedirect("/OperatorPage.jsp");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
