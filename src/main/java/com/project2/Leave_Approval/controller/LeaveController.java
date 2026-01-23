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

    // ================= HOME =================
    @GetMapping("/")
    public String home() {
        return "index";
    }

    // ================= STUDENT FLOW =================

    @GetMapping("/student/home")
    public String studentHome() {
        return "student_home";
    }

    @GetMapping("/student/apply")
    public String applyLeave(Model model) {
        model.addAttribute("leave", new LeaveRequest());
        return "student_form";
    }

    @PostMapping("/save")
    public String saveLeave(@ModelAttribute LeaveRequest leave) {
        service.saveLeave(leave);
        return "redirect:/";
    }

    @GetMapping("/student/status")
    public String checkStatusPage() {
        return "student_status";
    }

    @PostMapping("/student/status")
    public String viewStatus(@RequestParam String rollNo, Model model) {
        model.addAttribute("leaves", service.getLeavesByRollNo(rollNo));
        return "student_status_list";
    }

    // ================= ADMIN FLOW =================

    @GetMapping("/admin")
    public String adminLogin() {
        return "admin_login";
    }

    @PostMapping("/adminLogin")
    public String adminAuth(@RequestParam String username,
                            @RequestParam String password,
                            HttpSession session,
                            Model model) {

        if ("hod".equals(username) && "Hod@123".equals(password)) {
            session.setAttribute("role", "ADMIN");
            return "redirect:/leaves";
        }

        model.addAttribute("error", "Invalid credentials");
        return "admin_login";
    }

    @GetMapping("/leaves")
    public String viewLeaves(Model model, HttpSession session) {

        if (!"ADMIN".equals(session.getAttribute("role"))) {
            return "redirect:/admin";
        }

        model.addAttribute("leaves", service.getAllLeaves());
        model.addAttribute("role", session.getAttribute("role"));
        return "leave_list";
    }

    @GetMapping("/approve/{id}")
    public String approve(@PathVariable Long id, HttpSession session) {

        if (!"ADMIN".equals(session.getAttribute("role"))) {
            return "redirect:/admin";
        }

        service.approve(id);
        return "redirect:/leaves";
    }

    @GetMapping("/reject/{id}")
    public String reject(@PathVariable Long id, HttpSession session) {

        if (!"ADMIN".equals(session.getAttribute("role"))) {
            return "redirect:/admin";
        }

        service.reject(id);
        return "redirect:/leaves";
    }
}
