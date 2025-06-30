package com.softwares.repository;

import java.util.List;

import com.softwares.models.Notification;
import org.springframework.data.jpa.repository.JpaRepository;


public interface NotificationRepository extends JpaRepository<Notification, Long> {



}
