package app.controller;

import app.model.Lecturer;
import app.repository.LecturerRepository;

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
        LecturerRepository lecturerRepository = new LecturerRepository();
        try {
            Lecturer lecturer = lecturerRepository.findById(1);
            System.out.println(lecturer.getLastName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
