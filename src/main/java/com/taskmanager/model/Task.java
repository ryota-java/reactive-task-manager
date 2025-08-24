package com.taskmanager.model;


import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import jakarta.annotation.Priority;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "tasks")
public class Task extends PanacheEntity {

    @Column(nullable = false, length = 200)
    public String title;

    @Column(length = 1000)
    public String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public Priority priority;

    @Column(name = "deadline")
    public LocalDateTime deadline;

    @Column(name = "completed")
    public boolean completed = false;

    @Column(name = "created_at")
    public LocalDateTime createdAt;

    @Column(name = "updated_at")
    public LocalDateTime updatedAt;

    // Pre-persist dan pre-update untuk tracking waktu
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Override equals dan hashCode untuk comparison yang aman
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id) &&
                Objects.equals(title, task.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }

    // ToString yang informatif untuk debugging
    @Override
    public String toString() {
        return String.format("Task{id=%d, title='%s', priority=%s, completed=%b}",
                id, title, priority, completed);
    }
}