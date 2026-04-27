package com.example.demo;

import com.example.demo.Entity.Reminder;
import com.example.demo.Service.EmailService;
import com.example.demo.Service.ReminderService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class ReminderScheduler {

    private final ReminderService service;
    private final EmailService emailService;

    public ReminderScheduler(ReminderService service, EmailService emailService) {
        this.service = service;
        this.emailService = emailService;
    }

    // ⏰ Runs every minute
    @Scheduled(cron = "0 * * * * *")
    public void checkReminders() {

        System.out.println("⏰ Checking reminders at: " + LocalDateTime.now());

        List<Reminder> reminders = service.getDueReminders();

        for (Reminder r : reminders) {

            // 🔔 Console log
            System.out.println("🔔 REMINDER: " + r.getTitle() + " - " + r.getDescription());

            // 📧 Send email
            emailService.sendReminder(
                    "kakaniseshadri367@gmail.com",
                    r.getTitle(),
                    r.getDescription()
            );

            // 🔥 CUSTOM INTERVAL LOGIC
            if (r.getIntervalDays() != null && r.getIntervalDays() > 0) {

                // Move reminder forward by X days
                r.setReminderTime(r.getReminderTime().plusDays(r.getIntervalDays()));

                // Keep it active
                r.setStatus("PENDING");

                service.save(r);

            } else {
                // One-time reminder
                r.setStatus("DONE");
                service.markDone(r);
            }
        }
    }
}