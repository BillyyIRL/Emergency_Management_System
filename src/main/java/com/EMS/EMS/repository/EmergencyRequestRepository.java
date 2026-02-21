package com.EMS.EMS.repository;

import com.EMS.EMS.entity.EmergencyRequest;
import com.EMS.EMS.enums.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmergencyRequestRepository extends JpaRepository<EmergencyRequest, Long> {

    // Find all emergency requests with a specific status (e.g., PENDING)
    List<EmergencyRequest> findByStatus(RequestStatus status);

    // Optional: find all requests not handled yet
    List<EmergencyRequest> findByIsHandledFalse();

    List<EmergencyRequest> findByFullyAssigned(Boolean fullyAssigned);

    List<EmergencyRequest> findByUserId(Long userId);

}
