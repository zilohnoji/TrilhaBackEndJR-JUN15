package com.donatoordep.rg.code.entities;

import com.donatoordep.rg.code.builders.entities.UserSpecificationBuilder;
import com.donatoordep.rg.code.enums.RoleName;
import com.donatoordep.rg.code.exceptions.ONBEntityNotFoundException;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Table(name = "tb_user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;
    private String email;
    private String password;
    private boolean enabled;

    @OneToOne(cascade = CascadeType.ALL)
    private EmailCodeConfirmation code;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "tb_user_role", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private final Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private final List<Task> tasks = new ArrayList<>();

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
        public UserSpecificationBuilder code(EmailCodeConfirmation code) {
            this.entity.setCode(code);
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

    public Task findTaskById(UUID id) {
        return tasks.stream().filter(task -> task.getId().equals(id)).findFirst().orElseThrow(ONBEntityNotFoundException::new);
    }

    public void deleteTaskById(UUID id) {
        tasks.remove(this.findTaskById(id));
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public EmailCodeConfirmation getCode() {
        return code;
    }

    public void setCode(EmailCodeConfirmation code) {
        this.code = code;
    }

    public void addTask(Task task) {
        task.setUser(this);
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

    public void setPassword(String password) {
        this.password = password;
    }

    public User activeAccount() {
        if (!this.isEnabled()) {
            this.enabled = true;
        }
        return this;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return enabled;
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