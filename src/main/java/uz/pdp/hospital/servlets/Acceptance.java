package uz.pdp.hospital.servlets;

import jakarta.persistence.EntityManager;
import uz.pdp.hospital.entity.Patient;
import uz.pdp.hospital.entity.Status;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static uz.pdp.hospital.MyListener.EMF;

@WebServlet("/acceptance")
public class Acceptance extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int patientId = Integer.parseInt(req.getParameter("patientId"));

        try (
                EntityManager entityManager = EMF.createEntityManager()
            ){
           entityManager.getTransaction().begin();
            Patient patient = entityManager.find(Patient.class, patientId);
            patient.setStatus(Status.COMPLETED);
            entityManager.getTransaction().commit();
            resp.sendRedirect("/DoctorPage.jsp");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
