package com.freedomofdev.parcinformatique.service;

import com.freedomofdev.parcinformatique.dto.UserDto;
import com.freedomofdev.parcinformatique.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> findByEmail(String email);
    User save(User user);
    User getUserById(Long id);
    void assignActifToUser(Long userId, Long actifId);
    void removeActifFromUser(Long userId, Long actifId);
    List<UserDto> getAllUsers();
    void deleteUserAndUnassignActifs(Long userId);
}