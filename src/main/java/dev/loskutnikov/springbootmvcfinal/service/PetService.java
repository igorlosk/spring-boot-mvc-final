package dev.loskutnikov.springbootmvcfinal.service;

import dev.loskutnikov.springbootmvcfinal.dto.PetDto;

import dev.loskutnikov.springbootmvcfinal.dto.UserDto;
import dev.loskutnikov.springbootmvcfinal.model.Pet;
import dev.loskutnikov.springbootmvcfinal.model.User;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PetService {


    private final PetMapper petMapper;
    private final UserMapper userMapper;
    private final UserService userService;
    private final HashMap<Long, Pet> petList;


    private Long idCounter;

    public PetService(PetMapper petMapper, UserMapper userMapper, UserService userService) {
        this.petMapper = petMapper;
        this.userMapper = userMapper;
        this.userService = userService;
        this.petList = new HashMap<>();
        this.idCounter = 0L;

    }

    public PetDto createPet(PetDto petDto, Long userId) {
        userService.findById(userId);
        Long newId = ++idCounter;
        Pet pet = new Pet();
        pet.setId(newId);
        pet.setName(petDto.getName());
        pet.setUserId(userId);
        petList.put(newId, pet);
        userService.addPetToUser(userId, pet);
        return petMapper.toDto(pet);

    }

    public void deletePet(Long id) {
        if (!petList.containsKey(id)) {
            throw new NoSuchElementException("Pet not found by id=%s".formatted(id));
        }
        Pet pet = petList.get(id);
        Long userId = pet.getUserId();
        userService.deletePetFromUser(userId, pet);
        petList.remove(id);
    }
}
