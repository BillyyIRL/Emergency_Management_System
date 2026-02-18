package com.EMS.EMS.repository;

import com.EMS.EMS.entity.EmergencyAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmergencyAssignmentRepository extends JpaRepository<EmergencyAssignment, Long> {

    List<EmergencyAssignment> findByEmergencyRequestId(Long emergencyRequestId);

    List<EmergencyAssignment> findByHospitalId(Long hospitalId);
}