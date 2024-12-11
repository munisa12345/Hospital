package uz.pdp.hospital.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static uz.pdp.hospital.servlets.CheckLoginServlet.code;

@WebServlet("/verifyCode")
public class VerifyCode extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int authCode = Integer.parseInt(req.getParameter("authCode"));
        HttpSession session = req.getSession();

        if (authCode == code) {
            Object doctor = session.getAttribute("doctor");
            Object operator = session.getAttribute("operator");

            if (doctor != null) {
                resp.sendRedirect("/DoctorPage.jsp");
                return;
            } else if (operator != null) {
                resp.sendRedirect("/OperatorPage.jsp");
                return;
            }
        } else {
            resp.sendRedirect(req.getContextPath() + "/CheckAuthenticationCode.jsp?error=true");
        }
    }
}
