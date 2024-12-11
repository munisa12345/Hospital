package uz.pdp.hospital.servlets;


import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import uz.pdp.hospital.entity.Doctor;
import uz.pdp.hospital.entity.Operator;
import uz.pdp.hospital.entity.Patient;
import uz.pdp.hospital.entity.Status;

import java.util.List;

import static uz.pdp.hospital.MyListener.EMF;


public class Repositories {

    public static List<Patient> getPatients(String search) {
        if (search == null || search.trim().isEmpty()) {
            String query = "select p from Patient p where p.status = :status";
            try (EntityManager entityManager = EMF.createEntityManager()) {
                return entityManager.createQuery(query, Patient.class)
                        .setParameter("status", Status.NEW)
                        .getResultList();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        String query = "select p from Patient p where p.phoneNumber = :search and p.status = :status";
        try (EntityManager entityManager = EMF.createEntityManager()) {
            return entityManager.createQuery(query, Patient.class)
                    .setParameter("search", search)
                    .setParameter("status", Status.NEW)
                    .getResultList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static List<Doctor> getDoctors() {
        try (
                EntityManager entityManager = EMF.createEntityManager();
        ) {
            return entityManager.createNativeQuery(
                    "select * from doctor", Doctor.class
            ).getResultList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Operator> getOperators() {
        try (
                EntityManager entityManager = EMF.createEntityManager();
            ){
          return entityManager.createNativeQuery(
                  "select * from operator", Operator.class
          ).getResultList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static List<Patient> getPatientsForDoctor(Integer id) {
        try (EntityManager entityManager = EMF.createEntityManager()) {
            return entityManager.createNativeQuery(
                            "select * from patient p where p.doctor_id = :id and p.status = :status",
                            Patient.class)
                    .setParameter("id", id)
                    .setParameter("status", Status.CHECKED)
                    .getResultList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Patient> getPatientsForReport(Integer doctorId, int limit, int offset) {
        try (EntityManager entityManager = EMF.createEntityManager()) {
            Query query = entityManager.createNativeQuery(
                    "SELECT * FROM patient p WHERE p.doctor_id = :doctorId AND p.status = :status LIMIT :limit OFFSET :offset",
                    Patient.class);
            query.setParameter("doctorId", doctorId);
            query.setParameter("status", Status.COMPLETED);
            query.setParameter("limit", limit);
            query.setParameter("offset", offset);

            return query.getResultList();
        }
    }


    public static Long countPatients(Integer id) {
        try (
                EntityManager entityManager = EMF.createEntityManager()
        ){
            Query nativeQuery = entityManager.createNativeQuery(
                            "select count(*) from patient p where p.doctor_id = :id and p.status = :status",
                            Long.class)
                    .setParameter("id", id)
                    .setParameter("status", Status.COMPLETED);

            return (Long) nativeQuery.getSingleResult();
        }
    }
}
