package com.warehouse_project.warehouse_project.service;

import com.warehouse_project.warehouse_project.model.Role;
import com.warehouse_project.warehouse_project.model.User;
import com.warehouse_project.warehouse_project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository; // Tu repositorio de usuarios


    public List<Role> getRolesByUid(String uid) {
        User user = userRepository.findByUid(uid);
        return user.getRoles(); // Aquí se asume que 'roles' es una lista de String
    }
}
