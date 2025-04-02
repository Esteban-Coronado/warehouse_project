package com.warehouse_project.warehouse_project.controller;


import com.warehouse_project.warehouse_project.dto.LoginDTO;
import com.warehouse_project.warehouse_project.dto.RoleDTO;
import com.warehouse_project.warehouse_project.dto.UserInfoDTO;
import com.warehouse_project.warehouse_project.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("/getUserInfo")
    public String getPrincipalName(Principal principal) {
        return principal.getName();
    }

    @GetMapping("/getRoles")
    public List<RoleDTO> getRoles(Principal principal) {
        return authenticationService.getRolesFromPrincipal(principal);
    }
}