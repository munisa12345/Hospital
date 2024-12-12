package uz.pdp.hospital;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import uz.pdp.hospital.entity.Doctor;
import uz.pdp.hospital.entity.Operator;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.time.LocalTime;

@WebListener
public class MyListener implements ServletContextListener {

    public static EntityManagerFactory EMF = null;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        EMF = Persistence.createEntityManagerFactory("hospital");

        try (EntityManager entityManager = EMF.createEntityManager()) {
            entityManager.getTransaction().begin();

            // Operator ma'lumotlari mavjudligini tekshirish va qo'shish
            Long operatorCount = entityManager.createQuery(
                            "SELECT COUNT(o) FROM Operator o WHERE o.email = :email", Long.class)
                    .setParameter("email", "operatorjon@gmail.com")
                    .getSingleResult();

            if (operatorCount == 0) {
                Operator operator = new Operator();
                operator.setEmail("operatorjon@gmail.com");
                operator.setFirstName("Hakim");
                operator.setLastName("Hakimov");
                operator.setPhoneNumber("998900000000");
                entityManager.persist(operator);
            }

            // Doctor ma'lumotlari mavjudligini tekshirish va qo'shish
            Long doctor1Count = entityManager.createQuery(
                            "SELECT COUNT(d) FROM Doctor d WHERE d.email = :email", Long.class)
                    .setParameter("email", "eshmat@gmail.com")
                    .getSingleResult();

            if (doctor1Count == 0) {
                Doctor doctor = new Doctor();
                doctor.setEmail("eshmat@gmail.com");
                doctor.setAge(28);
                doctor.setFirstName("Eshmat");
                doctor.setLastName("Toshmatov");
                doctor.setPhoneNumber("998911111111");
                doctor.setGender("M");
                doctor.setStartTime(LocalTime.of(8, 0));
                doctor.setEndTime(LocalTime.of(20, 0));
                doctor.setSpecialization("Cardiologist");
                doctor.setPhotoUrl("/webapp/images/profilePhoto.jpg");
                entityManager.persist(doctor);
            }

            Long doctor2Count = entityManager.createQuery(
                            "SELECT COUNT(d) FROM Doctor d WHERE d.email = :email", Long.class)
                    .setParameter("email", "madina@gmail.com")
                    .getSingleResult();

            if (doctor2Count == 0) {
                Doctor doctor2 = new Doctor();
                doctor2.setEmail("madina@gmail.com");
                doctor2.setAge(28);
                doctor2.setFirstName("Madina");
                doctor2.setLastName("Qudratova");
                doctor2.setPhoneNumber("998922222222");
                doctor2.setGender("F");
                doctor2.setStartTime(LocalTime.of(10, 0));
                doctor2.setEndTime(LocalTime.of(19, 0));
                doctor2.setSpecialization("Pediatrician");
                doctor2.setPhotoUrl("/webapp/images/profilePhoto.jpg");
                entityManager.persist(doctor2);
            }

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize database data", e);
        }
    }
}
