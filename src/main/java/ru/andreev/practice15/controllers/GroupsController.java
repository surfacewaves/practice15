package ru.andreev.practice15.controllers;

import ru.andreev.practice15.models.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.andreev.practice15.services.AdminService;
import ru.andreev.practice15.services.GroupsService;

@Controller
@RequestMapping("/groups")
public class GroupsController {
    private final GroupsService groupsService;
    private final AdminService adminService;

    @Autowired
    public GroupsController(GroupsService groupsService, AdminService adminService) {
        this.groupsService = groupsService;
        this.adminService = adminService;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("groups", groupsService.findAll());
        return "/groups/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("group", groupsService.findOne(id));
        model.addAttribute("students", groupsService.getStudentsByGroupId(id));
        return "/groups/show";
    }

    @GetMapping("/new")
    public String newGroup(@ModelAttribute("group") Group group) {
        return "groups/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("group") Group group) {
        groupsService.save(group);
        return "redirect:/groups";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("group", groupsService.findOne(id));
        return "groups/edit";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id, @ModelAttribute("group") Group group) {
        groupsService.update(id, group);
        return "redirect:/groups";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        groupsService.delete(id);
        return "redirect:/groups";
    }

    @GetMapping("/admin")
    public String adminPage() {
        adminService.doAdminStaff();
        return "groups/admin";
    }
}
