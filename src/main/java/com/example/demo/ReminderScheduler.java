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

    // Runs every minute
    @Scheduled(cron = "0 * * * * *")
    public void checkReminders() {

        System.out.println("⏰ Checking reminders at: " + LocalDateTime.now());

        List<Reminder> reminders = service.getDueReminders();

        for (Reminder r : reminders) {

            // 🔔 Console
            System.out.println("🔔 REMINDER: " + r.getTitle() + " - " + r.getDescription());

            // 📧 Email
            emailService.sendReminder(
                    "kakaniseshadri367@gmail.com",
                    r.getTitle(),
                    r.getDescription()
            );

            // 🔁 Recurrence Logic
            if ("DAILY".equalsIgnoreCase(r.getRecurrence())) {

                r.setReminderTime(r.getReminderTime().plusDays(1));
                service.save(r);

            } else if ("EVERY_2_DAYS".equalsIgnoreCase(r.getRecurrence())) {

                r.setReminderTime(r.getReminderTime().plusDays(2));
                service.save(r);

            } else if ("WEEKLY".equalsIgnoreCase(r.getRecurrence())) {

                r.setReminderTime(r.getReminderTime().plusWeeks(1));
                service.save(r);

            } else {
                // ONE-TIME
                service.markDone(r);
            }
        }
    }
}