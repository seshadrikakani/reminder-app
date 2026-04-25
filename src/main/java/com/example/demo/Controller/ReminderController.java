package com.example.demo.Controller;

import com.example.demo.Entity.Reminder;
import com.example.demo.Service.ReminderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reminders")
@CrossOrigin(origins = "http://localhost:3000")
public class ReminderController {

    private final ReminderService service;

    public ReminderController(ReminderService service) {
        this.service = service;
    }

    @PostMapping
    public Reminder create(@RequestBody Reminder reminder) {
        return service.save(reminder);
    }

    @GetMapping
    public List<Reminder> getAll() {
        return service.getAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}