package com.project2.Leave_Approval.service;

import com.project2.Leave_Approval.entity.LeaveRequest;
import com.project2.Leave_Approval.repository.LeaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaveService {

    @Autowired
    private LeaveRepository repository;

    public void saveLeave(LeaveRequest leave) {
        leave.setStatus("PENDING");
        repository.save(leave);
    }

    public List<LeaveRequest> getAllLeaves() {
        return repository.findAll();
    }

    public void approve(Long id) {
        LeaveRequest leave = repository.findById(id).get();
        leave.setStatus("APPROVED");
        repository.save(leave);
    }

    public void reject(Long id) {
        LeaveRequest leave = repository.findById(id).get();
        leave.setStatus("REJECTED");
        repository.save(leave);
    }
}
