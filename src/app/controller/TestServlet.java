package app.controller;

import app.model.Lecturer;
import app.repository.LecturerRepository;
import app.repository.SessionRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "TestServlet", urlPatterns = "/test")
public class TestServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SessionRepository sessionRepository = new SessionRepository();
        try {
            sessionRepository.findById(1).getLearnerList().forEach(
                    learner -> System.out.println(learner.getFirstName() + " " + learner.getLastName()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
