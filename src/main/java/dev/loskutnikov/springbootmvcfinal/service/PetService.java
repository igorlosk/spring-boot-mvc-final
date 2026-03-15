package dev.loskutnikov.springbootmvcfinal.service;

import dev.loskutnikov.springbootmvcfinal.dto.PetDto;

import dev.loskutnikov.springbootmvcfinal.model.Pet;
import dev.loskutnikov.springbootmvcfinal.model.User;
import jakarta.validation.Valid;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PetService {

    private final Map<Long, Pet> petList;

    private Long idCounter;

    private final PetMapper petMapper;

    private final UserService userService;

    public PetService(PetMapper petMapper, UserService userService) {
        this.userService = userService;
        this.petList = new HashMap<>();
        this.petMapper = petMapper;
        this.idCounter = 0L;
    }

    public PetDto createPet(PetDto petDto, Long userId) {
        Long petId = ++idCounter;
        PetDto createdPet = new PetDto(
                petId,
                petDto.getName(),
                userId
        );
        userService.addPetToUser(userId, petMapper.toEntity(createdPet));
        petList.put(petId, petMapper.toEntity(createdPet));
        return createdPet;

    }

    public Pet getPet(Long id) {
        return Optional.ofNullable(petList.get(id))
                .orElseThrow(() -> new NoSuchElementException("Pet not found by id=%s".formatted(id)));

    }
}
