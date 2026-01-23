package com.project2.Leave_Approval.repository;

import com.project2.Leave_Approval.entity.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaveRepository extends JpaRepository<LeaveRequest, Long> {
    List<LeaveRequest> findByStatus(String status);
    List<LeaveRequest> findByRollNo(String rollNo);
}
