package com.example.demo.Service;

import com.example.demo.Entity.Reminder;
import com.example.demo.Repository.ReminderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReminderService {

    private final ReminderRepository repository;

    public ReminderService(ReminderRepository repository) {
        this.repository = repository;
    }

    // GET ALL
    public List<Reminder> getAll() {
        return repository.findAll();
    }

    // SAVE
    public Reminder save(Reminder reminder) {
        return repository.save(reminder);
    }

    // DELETE
    public void delete(Long id) {
        repository.deleteById(id);
    }

    // GET BY ID
    public Reminder getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reminder not found"));
    }

    // 🔥 GET DUE REMINDERS (scheduler uses this)
    public List<Reminder> getDueReminders() {
        return repository.findByReminderTimeBeforeAndStatus(
                LocalDateTime.now(),
                "PENDING"
        );
    }

    // MARK DONE
    public void markDone(Reminder reminder) {
        reminder.setStatus("DONE");
        repository.save(reminder);
    }
}