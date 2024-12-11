package uz.pdp.hospital.servlets;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import lombok.SneakyThrows;
import uz.pdp.hospital.entity.Doctor;
import uz.pdp.hospital.entity.Operator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;


@WebServlet("/login/check")
public class CheckLoginServlet extends HttpServlet {

    public static Integer code;
    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String phoneNumber = req.getParameter("phoneNumber");
        String email = req.getParameter("email");

        Random rand = new Random();
        code = 100000 + rand.nextInt(900000);

        List<Doctor> doctors = Repositories.getDoctors();
        List<Operator> operators = Repositories.getOperators();
        HttpSession httpSession = req.getSession();

        boolean isAuthenticated = false;


        for (Doctor doctor : doctors) {
            if (doctor.getPhoneNumber().equals(phoneNumber) && doctor.getEmail().equals(email)) {
                httpSession.setAttribute("doctor", doctor);
                isAuthenticated = true;
                break;
            }
        }

        if (!isAuthenticated) {
            for (Operator operator : operators) {
                if (operator.getPhoneNumber().equals(phoneNumber) && operator.getEmail().equals(email)) {
                    httpSession.setAttribute("operator", operator);
                    isAuthenticated = true;
                    break;
                }
            }

    }

        if (isAuthenticated) {
            sendCodeToEmail(email, code);
            httpSession.setAttribute("authCode", code);
            resp.sendRedirect("/CheckAuthenticationCode.jsp");
        } else {
            resp.sendRedirect(req.getContextPath() + "/LoginPage.jsp");

        }
    }

    private void sendCodeToEmail(String email, int code) {
        String htmlContent = """
            <html>
                <head>
                    <style>
                        .button {
                            background-color: green;
                            border: none;
                            color: white;
                            padding: 10px 20px;
                            text-align: center;
                            text-decoration: none;
                            display: inline-block;
                            font-size: 16px;
                            border-radius: 5px;
                            cursor: pointer;
                        }
                    </style>
                </head>
                <body>
                    <h2>Hospital Authentication</h2>
                    <p>Your authentication code is:</p>
                    <button class="button">%d</button>
                    <p>Please use this code to complete your login process.</p>
                </body>
            </html>
        """.formatted(code);

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.auth", "true");

        String userName = "munisaabdurahimova3@gmail.com";
        String password = "hdrmbvyvqcnkcwtv";

        Session session = getSession(props, userName, password);

        try {
            Message message = new MimeMessage(session);
            message.setSubject("Hospital Authentication");
            message.setFrom(new InternetAddress(userName));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.setContent(htmlContent, "text/html");

            Transport.send(message);
            System.out.println("Email sent successfully!");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private static Session getSession(Properties props, String userName, String password) {
        return Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        });
    }
}
