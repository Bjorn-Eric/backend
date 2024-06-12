package org.example.backend.web;

import org.springframework.stereotype.Controller;
import org.example.backend.domain.Student;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;

@Controller
public class StudentController {

    // En ihan ymmärtäny miten toi lista piti ottaa sisään, niin tein vaan kovakoodatun listan, jonka näytän

    @RequestMapping(path = "/hello", method = RequestMethod.GET)
    public String helloStudents(Model model) {
        ArrayList<Student> students = new ArrayList<>();
        students.add(new Student("John", "Wick"));
        students.add(new Student("Helly", "Hansen"));
        students.add(new Student("John", "Smith"));
        students.add(new Student("Kelly", "Rogers"));
        model.addAttribute("students", students);
        return "studentlist";
    }
}
