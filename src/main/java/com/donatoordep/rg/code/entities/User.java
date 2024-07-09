package com.donatoordep.rg.code.entities;

import com.donatoordep.rg.code.builders.entities.UserSpecificationBuilder;
import com.donatoordep.rg.code.enums.RoleName;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "tb_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;
    private String email;
    private String password;
    private boolean enabled;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private EmailCodeConfirmation code;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "tb_user_role", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private final Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private final Set<Task> tasks = new HashSet<>();

    private User() {
    }

    public static class UserBuilder implements UserSpecificationBuilder {

        private User entity;

        private UserBuilder() {
            this.reset();
        }

        @Override
        public User build() {
            return this.entity;
        }

        public static UserBuilder builder() {
            return new UserBuilder();
        }

        @Override
        public UserSpecificationBuilder id(UUID id) {
            this.entity.setId(id);
            return this;
        }

        @Override
        public UserSpecificationBuilder name(String name) {
            this.entity.setName(name);
            return this;
        }

        @Override
        public UserSpecificationBuilder email(String email) {
            this.entity.setEmail(email);
            return this;
        }

        @Override
        public UserSpecificationBuilder password(String password) {
            this.entity.setPassword(password);
            return this;
        }

        @Override
        public UserSpecificationBuilder code(String code, LocalDateTime expiredAt) {
            this.entity.setCode(EmailCodeConfirmation.EmailCodeConfirmationBuilder.builder()
                    .id(UUID.randomUUID())
                    .expiredAt(expiredAt)
                    .build());
            return this;
        }

        @Override
        public UserSpecificationBuilder role(RoleName role) {
            this.entity.addRole(new Role(role));
            return this;
        }

        @Override
        public void reset() {
            this.entity = new User();
        }
    }

    public EmailCodeConfirmation getCode() {
        return code;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setCode(EmailCodeConfirmation code) {
        this.code = code;
    }

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void activeAccount() {
        if (!this.isEnabled()) {
            this.enabled = true;
        }
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        User user = (User) object;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}