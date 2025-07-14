package com.kanban.app.models.dto;

public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String cellphone;
    private EntityIdentifier role;

    public UserDTO() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getCellphone() { return cellphone; }
    public void setCellphone(String cellphone) { this.cellphone = cellphone; }
    public EntityIdentifier getRole() { return role; }
    public void setRole(EntityIdentifier role) { this.role = role; }
}
