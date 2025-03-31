package com.warehouse_project.warehouse_project.dto;

import com.warehouse_project.warehouse_project.model.User;

import java.util.List;

public record UserInfoDTO(
        Long id,
        String userUid,
        List<RoleDTO> roles
) {

    public UserInfoDTO(User user) {
        this(user.getId(), user.getUid(), user.getRoles().stream().map(RoleDTO::new).toList());
    }
}
