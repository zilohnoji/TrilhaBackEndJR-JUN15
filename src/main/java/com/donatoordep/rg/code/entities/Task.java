package com.donatoordep.rg.code.entities;

import com.donatoordep.rg.code.builders.entities.TaskSpecificationBuilder;
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
    private User user;

    private Task() {
    }

    private Task(String title, String content, TaskStatus status) {
        this.title = title;
        this.content = content;
        this.status = status;
    }

    public static class TaskBuilder implements TaskSpecificationBuilder {

        private Task entity;

        private TaskBuilder() {
            this.reset();
        }

        public static TaskBuilder builder() {
            return new TaskBuilder();
        }

        @Override
        public Task build() {
            return this.entity;
        }

        @Override
        public TaskSpecificationBuilder title(String title) {
            this.entity.setTitle(title);
            return this;
        }

        @Override
        public TaskSpecificationBuilder content(String content) {
            this.entity.setContent(content);
            return this;
        }

        @Override
        public TaskSpecificationBuilder status(TaskStatus status) {
            this.entity.setStatus(status);
            return this;
        }

        @Override
        public TaskSpecificationBuilder id(UUID id) {
            this.entity.setId(id);
            return this;
        }

        @Override
        public void reset() {
            this.entity = new Task();
        }
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

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
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