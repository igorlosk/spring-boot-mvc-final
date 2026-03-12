package dev.loskutnikov.springbootmvcfinal.service;

import dev.loskutnikov.springbootmvcfinal.dto.PetDto;

import dev.loskutnikov.springbootmvcfinal.dto.UserDto;
import dev.loskutnikov.springbootmvcfinal.model.Pet;
import dev.loskutnikov.springbootmvcfinal.model.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PetService {


    private final PetMapper petMapper;
    private final UserMapper userMapper;
    private final UserService userService;


    private Long idCounter;

    public PetService(PetMapper petMapper, UserMapper userMapper, UserService userService) {
        this.petMapper = petMapper;
        this.userMapper = userMapper;
        this.userService = userService;
        this.idCounter = 0L;

    }

    public PetDto createPet(PetDto petDto, Long userId) {
        UserDto userDto = userService.findById(userId);
        Long newId = ++idCounter;
        Pet pet = petMapper.toEntity(petDto);
        pet.setId(newId);
        pet.setName(petDto.getName());
        pet.setUserId(userDto.getId());
        System.out.println(pet);
        userService.addPetToUser(userId, pet);
        return petMapper.toDto(pet);

    }

}
