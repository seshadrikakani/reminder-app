package com.example.demo;

import com.example.demo.Entity.Reminder;
import com.example.demo.Service.EmailService;
import com.example.demo.Service.ReminderService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.swing.*;
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

    // 🔥 CRON: runs every minute (second 0)
    @Scheduled(cron = "0 * * * * *")
    public void checkReminders() {

        System.out.println("⏰ Checking reminders at: " + LocalDateTime.now());

        List<Reminder> reminders = service.getDueReminders();

        for (Reminder r : reminders) {

            // 🔔 Console log
            System.out.println("🔔 REMINDER: " + r.getTitle() + " - " + r.getDescription());

            // 🪟 Desktop Popup
            showPopup(r.getTitle(), r.getDescription());

            // 📧 Email Notification
            emailService.sendReminder(
                    "kakaniseshadri367@gmail.com", // 👉 replace if needed
                    r.getTitle(),
                    r.getDescription()
            );

            // 🔁 Recurring Logic
            if ("DAILY".equalsIgnoreCase(r.getRecurrence())) {

                r.setReminderTime(r.getReminderTime().plusDays(1));
                service.save(r);

            } else if ("WEEKLY".equalsIgnoreCase(r.getRecurrence())) {

                r.setReminderTime(r.getReminderTime().plusWeeks(1));
                service.save(r);

            } else {
                // ONE-TIME reminder
                service.markDone(r);
            }
        }
    }

    // 🪟 Popup method
    private void showPopup(String title, String message) {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(
                    null,
                    message,
                    title,
                    JOptionPane.INFORMATION_MESSAGE
            );
        });
    }
}