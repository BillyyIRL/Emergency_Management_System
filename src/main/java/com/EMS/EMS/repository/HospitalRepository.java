package com.EMS.EMS.repository;

import com.EMS.EMS.entity.Hospital;
import com.EMS.EMS.enums.HospitalStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital, Long> {

    List<Hospital> findByHospitalStatus(HospitalStatus hospitalStatus);

    List<Hospital> findByHospitalAvailabilityStatus(HospitalStatus hospitalStatus);

    List<Hospital> findByIsActiveTrue(); // fetch all active hospitals

}
