package com.freedomofdev.parcinformatique.dto;

import lombok.Data;

import java.util.List;

@Data
public class AuthRequest {
    private String token;
    private String email;
    private String name;
    private List<String> groups;
}
