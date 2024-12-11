package uz.pdp.hospital.servlets;

import jakarta.persistence.EntityManager;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import uz.pdp.hospital.entity.Patient;
import uz.pdp.hospital.entity.Status;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static uz.pdp.hospital.MyListener.EMF;

@WebServlet("/patients/add")
public class AddPatientsServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String ageStr = req.getParameter("age");
        String phoneNumber = req.getParameter("phoneNumber");

        Map<String, String> errors = new HashMap<>();

        Integer age = null;
        try {
            age = Integer.parseInt(ageStr);
            if (age <= 0) {
                errors.put("age", "Age must be a positive number");
            }
        } catch (NumberFormatException e) {
            errors.put("age", "Age must be a valid number");
        }

        Patient patient = new Patient(
                firstName,
                lastName,
                age,
                phoneNumber,
                Status.NEW
        );

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Patient>> validate = validator.validate(patient);

        for (ConstraintViolation<Patient> violation : validate) {
            errors.put(violation.getPropertyPath().toString(), violation.getMessage());
        }

        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            req.getRequestDispatcher("/AddPatients.jsp").forward(req, resp);
            return;
        }

        try (EntityManager entityManager = EMF.createEntityManager()) {
            entityManager.getTransaction().begin();
            entityManager.persist(patient);
            entityManager.getTransaction().commit();
            resp.sendRedirect("/OperatorPage.jsp");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
