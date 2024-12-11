package uz.pdp.hospital.servlets;

import uz.pdp.hospital.entity.Doctor;
import uz.pdp.hospital.entity.Operator;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebFilter("/*")
public class Filter extends HttpFilter {
    private static final List<String> doctorPages = new ArrayList<>(List.of(
            "/DoctorPage.jsp",
            "/Report.jsp"
            ));

    private static final List<String> operatorPages = new ArrayList<>(List.of(
            "/OperatorPage.jsp", "/AddPatients.jsp"));

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String requestURI = req.getRequestURI();
        HttpSession session = req.getSession(false);

        if (doctorPages.contains(requestURI)) {
            if (session != null) {
                Doctor loggedDoctor = (Doctor) session.getAttribute("doctor");
                if (loggedDoctor != null) {
                    chain.doFilter(req, res);
                    return;
                }
            }
            res.sendRedirect("/LoginPage.jsp");
            return;
        }

        if (operatorPages.contains(requestURI)) {
            if (session != null) {
                Operator loggedOperator = (Operator) session.getAttribute("operator");
                if (loggedOperator != null) {
                    chain.doFilter(req, res);
                    return;
                }
            }
            res.sendRedirect("/LoginPage.jsp");
            return;
        }

        chain.doFilter(req, res);
    }
}
