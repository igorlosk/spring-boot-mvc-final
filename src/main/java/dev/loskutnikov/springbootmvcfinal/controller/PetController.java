package dev.loskutnikov.springbootmvcfinal.controller;

import dev.loskutnikov.springbootmvcfinal.dto.PetDto;
import dev.loskutnikov.springbootmvcfinal.service.PetService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/pets")
public class PetController {


    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping("{id}")
    public ResponseEntity<PetDto> createPet(
            @Valid @RequestBody PetDto petDto,
            @PathVariable("id") Long userId) {
        var petDtoCreate = petService.createPet(petDto, userId);
        return ResponseEntity.ok(petDtoCreate);
    }

}
