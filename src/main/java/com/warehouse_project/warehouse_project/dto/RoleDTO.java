package com.warehouse_project.warehouse_project.dto;

import com.warehouse_project.warehouse_project.model.Role;

public record RoleDTO(String name) {

    public RoleDTO(Role role) {
        this(role.getName());

    }
}
