package com.warehouse_project.warehouse_project.service;

import com.warehouse_project.warehouse_project.dto.RoleDTO;
import com.warehouse_project.warehouse_project.dto.UserInfoDTO;
import com.warehouse_project.warehouse_project.model.User;
import com.warehouse_project.warehouse_project.repository.RoleRepository;
import com.warehouse_project.warehouse_project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    public UserInfoDTO getUserInfoFromPrincipal(Principal principal) {
        String uid = principal.getName();

        System.out.println("Este es el maltito nombre: " + uid);
        User user = userRepository.findByUid(uid);
        return new UserInfoDTO(user);

    }
}