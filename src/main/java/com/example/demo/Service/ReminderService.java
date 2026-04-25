package com.example.demo.Service;

import com.example.demo.Entity.Reminder;
import com.example.demo.Repository.ReminderRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReminderService {

    private final ReminderRepository repo;

    public ReminderService(ReminderRepository repo) {
        this.repo = repo;
    }

    public Reminder save(Reminder reminder) {
        reminder.setStatus("PENDING");
        return repo.save(reminder);
    }

    public List<Reminder> getAll() {
        return repo.findAll();
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public List<Reminder> getDueReminders() {
        return repo.findByReminderTimeBeforeAndStatus(LocalDateTime.now(), "PENDING");
    }

    public void markDone(Reminder reminder) {
        reminder.setStatus("DONE");
        repo.save(reminder);
    }
}