package com.kanban.app.models.entities;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

@Entity(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "Name is required")
    private String name;
    @Column(unique = true)
    @Email(message = "Email should be valid")
    private String email;
    @NotNull(message = "Cellphone is required")
    private String cellphone;
    @NotNull(message = "Password is required")
    private String password;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
    private String won;
    @Column(unique = true)
    private String employeeNumber;
    @OneToMany(mappedBy = "createdBy", cascade = { CascadeType.ALL }, orphanRemoval = true)
    private java.util.List<Project> projects;
    @OneToMany(mappedBy = "createdBy", cascade = { CascadeType.ALL }, orphanRemoval = true)
    private java.util.List<Board> boards;
    @OneToMany(mappedBy = "createdBy", cascade = { CascadeType.ALL }, orphanRemoval = true)
    private java.util.List<Bucket> buckets;
    @OneToMany(mappedBy = "createdBy", cascade = { CascadeType.ALL }, orphanRemoval = true)
    private java.util.List<Task> tasks;
    @OneToMany(mappedBy = "createdBy", cascade = { CascadeType.ALL }, orphanRemoval = true)
    private java.util.List<Subtask> subtasks;
    @OneToMany(mappedBy = "createdBy", cascade = { CascadeType.ALL }, orphanRemoval = true)
    private java.util.List<BucketLabel> bucketLabels;

    public User() {
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public User(String name, String email, String cellphone, String password) {
        this.name = name;
        this.email = email;
        this.cellphone = cellphone;
        this.password = password;
    }

    public User(Long id, String name, String email, String cellphone, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.cellphone = cellphone;
        this.password = password;
    }

    public User(Long id, String name, String email, String cellphone, String password, Role role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.cellphone = cellphone;
        this.password = password;
        this.role = role;
    }

    public User(Long id, String name, String email, String cellphone, String password, Role role, String won,
            String employeeNumber) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.cellphone = cellphone;
        this.password = password;
        this.role = role;
        this.won = won;
        this.employeeNumber = employeeNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getWon() {
        return won;
    }

    public void setWon(String won) {
        this.won = won;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
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
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}