package com.example.demo.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data                // getters + setters + toString
@NoArgsConstructor   // default constructor
@AllArgsConstructor  // full constructor
public class Reminder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private LocalDateTime reminderTime;

    // 🔥 Custom interval (core feature)
    private Integer intervalDays;

    private String status; // PENDING / DONE
}