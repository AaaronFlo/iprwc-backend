package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Role;
import com.example.demo.repo.RoleRepo;

@RestController
@RequestMapping(path = "api/role")
public class RoleController {

    private final RoleRepo roleRepo;

    @Autowired
    public RoleController(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }

    @GetMapping("{name}")
    public ResponseEntity<Role> getRole(@PathVariable String name) {
        Role role = roleRepo.findByName(name).orElse(null);
        if (role == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(role, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Object> addRole(@RequestBody Role role) {
        if (role.getName() == null || role.getName().trim().isEmpty()) {
            return new ResponseEntity<>("Role name is required", HttpStatus.BAD_REQUEST);
        }
        try {
            Role savedRole = roleRepo.save(role);
            return new ResponseEntity<>(savedRole, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error creating role: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}