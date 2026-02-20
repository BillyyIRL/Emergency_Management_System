package com.EMS.EMS.repository;

import com.EMS.EMS.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByHospitalId(Long hospitalId);

    List<Notification> findByHospitalIdAndIsRead(Long hospitalId, boolean isRead);
}