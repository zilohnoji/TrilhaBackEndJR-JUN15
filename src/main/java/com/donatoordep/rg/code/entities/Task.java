package com.donatoordep.rg.code.entities;

import com.donatoordep.rg.code.enums.TaskStatus;
import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "tb_task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String title;

    private String content;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Task() {
    }

    private Task(String title, String content, TaskStatus status) {
        this.title = title;
        this.content = content;
        this.status = status;
    }

    public static Task ofCompleted(String title, String content) {
        return new Task(title, content, TaskStatus.COMPLETED);
    }

    public static Task ofProgress(String title, String content) {
        return new Task(title, content, TaskStatus.PROGRESS);
    }

    public static Task ofQuit(String title, String content) {
        return new Task(title, content, TaskStatus.QUIT);
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Task task = (Task) object;
        return Objects.equals(id, task.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}