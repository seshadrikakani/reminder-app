package com.example.demo.Controller;

import com.example.demo.Entity.Reminder;
import com.example.demo.Service.ReminderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*") // allow frontend calls
@RestController
@RequestMapping("/reminders")
public class ReminderController {

    private final ReminderService service;

    public ReminderController(ReminderService service) {
        this.service = service;
    }

    // ✅ GET ALL
    @GetMapping
    public List<Reminder> getAll() {
        return service.getAll();
    }

    // ✅ CREATE
    @PostMapping
    public Reminder create(@RequestBody Reminder reminder) {
        reminder.setStatus("PENDING");
        return service.save(reminder);
    }

    // ✅ DELETE
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    // 🔥 UPDATE (EDIT REMINDER)
    @PutMapping("/{id}")
    public Reminder update(@PathVariable Long id, @RequestBody Reminder updated) {

        Reminder r = service.getById(id);

        r.setTitle(updated.getTitle());
        r.setDescription(updated.getDescription());
        r.setReminderTime(updated.getReminderTime());
        r.setIntervalDays(updated.getIntervalDays());

        return service.save(r);
    }

    // 🔥 MARK AS DONE
    @PutMapping("/done/{id}")
    public Reminder markDone(@PathVariable Long id) {

        Reminder r = service.getById(id);
        r.setStatus("DONE");

        return service.save(r);
    }
}