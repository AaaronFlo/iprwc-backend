package com.example.demo.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "role")
public class Role {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private UUID role_id;

        @Column(nullable = false)
        private String name;


        public Role(String name) {
            this.name = name;
        }

        public Role() {

        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public UUID getRole_id() {
            return role_id;
        }

        public void setRole_id(UUID role_id) {
            this.role_id = role_id;
        }
}


