package com.project2.Leave_Approval.service;

import com.project2.Leave_Approval.entity.LeaveRequest;
import com.project2.Leave_Approval.repository.LeaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LeaveService {

    @Autowired
    private LeaveRepository repository;

    /* ======================
       SAVE NEW LEAVE
       ====================== */
    public void saveLeave(LeaveRequest leave) {
        leave.setStatus("PENDING");   // default
        repository.save(leave);
    }

    /* ======================
       FETCH ALL LEAVES
       ====================== */
    public List<LeaveRequest> getAllLeaves() {
        return repository.findAll();
    }

    /* ======================
       APPROVE LEAVE
       ====================== */
    public void approve(Long id) {
        Optional<LeaveRequest> optionalLeave = repository.findById(id);

        if (optionalLeave.isPresent()) {
            LeaveRequest leave = optionalLeave.get();

            // Prevent re-approval
            if ("PENDING".equals(leave.getStatus())) {
                leave.setStatus("APPROVED");
                repository.save(leave);
            }
        }
    }

    /* ======================
       REJECT LEAVE
       ====================== */
    public void reject(Long id) {
        Optional<LeaveRequest> optionalLeave = repository.findById(id);

        if (optionalLeave.isPresent()) {
            LeaveRequest leave = optionalLeave.get();

            // Prevent re-reject
            if ("PENDING".equals(leave.getStatus())) {
                leave.setStatus("REJECTED");
                repository.save(leave);
            }
        }
    }
    public List<LeaveRequest> getLeavesByRollNo(String rollNo) {
        return repository.findByRollNo(rollNo);
    }

}
