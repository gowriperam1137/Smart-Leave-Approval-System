package com.project2.Leave_Approval.controller;

import com.project2.Leave_Approval.entity.LeaveRequest;
import com.project2.Leave_Approval.service.LeaveService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LeaveController {

    @Autowired
    private LeaveService service;

    // Entry page
    @GetMapping("/")
    public String loginPage() {
        return "login";
    }

    // Student login
    @GetMapping("/student")
    public String student(HttpSession session) {
        session.setAttribute("role", "STUDENT");
        return "redirect:/apply";
    }

    // HOD login
    @PostMapping("/hodLogin")
    public String hodLogin(@RequestParam String username,
                           @RequestParam String password,
                           HttpSession session,
                           Model model) {

        if (username.equals("hod") && password.equals("admin123")) {
            session.setAttribute("role", "HOD");
            return "redirect:/leaves";
        }

        model.addAttribute("error", "Invalid HOD credentials");
        return "login";
    }

    // Apply leave
    @GetMapping("/apply")
    public String apply(Model model) {
        model.addAttribute("leave", new LeaveRequest());
        return "apply_leave";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute LeaveRequest leave) {
        service.saveLeave(leave);
        return "redirect:/apply";
    }

    // View leaves
    @GetMapping("/leaves")
    public String view(Model model, HttpSession session) {
        model.addAttribute("leaves", service.getAllLeaves());
        model.addAttribute("role", session.getAttribute("role"));
        return "leave_list";
    }

    @GetMapping("/approve/{id}")
    public String approve(@PathVariable Long id, HttpSession session) {
        if ("HOD".equals(session.getAttribute("role"))) {
            service.approve(id);
        }
        return "redirect:/leaves";
    }

    @GetMapping("/reject/{id}")
    public String reject(@PathVariable Long id, HttpSession session) {
        if ("HOD".equals(session.getAttribute("role"))) {
            service.reject(id);
        }
        return "redirect:/leaves";
    }
}
