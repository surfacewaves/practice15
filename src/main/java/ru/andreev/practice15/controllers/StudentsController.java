package ru.andreev.practice15.controllers;

import ru.andreev.practice15.models.Group;
import ru.andreev.practice15.models.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.andreev.practice15.services.GroupsService;
import ru.andreev.practice15.services.StudentsService;

@Controller
@RequestMapping("/students")
public class StudentsController {
    private StudentsService studentsService;
    private GroupsService groupService;

    @Autowired
    public StudentsController(StudentsService studentsService, GroupsService groupService) {
        this.studentsService = studentsService;
        this.groupService = groupService;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("students", studentsService.findAll());
        return "students/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("group") Group group) {
        model.addAttribute("student", studentsService.findOne(id));
        Group owner = studentsService.getStudentOwner(id);

        if (owner != null)
            model.addAttribute("owner", owner);
        else
            model.addAttribute("groups", groupService.findAll());
        return "students/show";
    }

    @GetMapping("/new")
    public String newStudent(@ModelAttribute("student") Student student) {
        return "students/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("student") Student student) {
        studentsService.save(student);
        return "redirect:/students";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("student", studentsService.findOne(id));
        return "students/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("student") Student student, @PathVariable("id") int id) {
        studentsService.update(id, student);
        return "redirect:/students";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        studentsService.delete(id);
        return "redirect:/students";
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("group") Group group) {
        studentsService.assign(id, group);
        return "redirect:/students/" + id;
    }
}
