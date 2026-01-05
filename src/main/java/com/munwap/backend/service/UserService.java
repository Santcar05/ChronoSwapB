package com.munwap.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.munwap.backend.dtos.UserDTO;
import com.munwap.backend.entity.UserEntity;
import com.munwap.backend.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private com.munwap.backend.repository.RoleRepository roleRepository;

    public List<UserDTO> getAllUsers() {
        List<UserEntity> users = userRepository.findAll();
        // Convertir entidades en DTOs
        List<UserDTO> userDTOs = users.stream().map(user -> UserDTO.builder()
                .id(user.getId())
                .full_name(user.getFull_name())
                .email(user.getEmail())
                .password(user.getPassword())
                .build()).toList();
        return userDTOs;
    }

    public UserDTO getUserById(Long id) {
        UserEntity user = userRepository.findById(id).orElse(null);

        // Convertir entidad en DTO
        if (user != null) {
            UserDTO userDTO = UserDTO.builder()
                    .id(user.getId())
                    .full_name(user.getFull_name())
                    .email(user.getEmail())
                    .password(user.getPassword())
                    .build();
            return userDTO;
        } else {
            return null;
        }
    }

    public void saveUser(UserDTO user) {
        //Convertir DTO en entidad
        UserEntity userEntity = UserEntity.builder()
                .full_name(user.getFull_name())
                .email(user.getEmail())
                .password(user.getPassword())
                .roles(List.of(roleRepository.findByName("USER")))
                .build();

        userRepository.save(userEntity);

    }

}
