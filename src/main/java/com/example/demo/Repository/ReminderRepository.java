package com.example.demo.Repository;

import com.example.demo.Entity.Reminder;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface ReminderRepository extends JpaRepository<Reminder, Long> {

    List<Reminder> findByReminderTimeBeforeAndStatus(LocalDateTime time, String status);
}