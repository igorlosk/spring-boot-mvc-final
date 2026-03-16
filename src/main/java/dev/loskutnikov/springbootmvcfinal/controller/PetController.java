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
            @RequestBody @Valid PetDto petDto,
            @PathVariable("id") Long userId) {
        var petDtoCreate = petService.createPet(petDto, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(petDtoCreate);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PetDto> updatePet(
            @RequestBody @Valid PetDto petDto,
            @PathVariable("id") Long petId) {
        return ResponseEntity.ok(petService.updatePet(petId, petDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PetDto> deletePet(@PathVariable("id") Long petId) {
        petService.deletePetById(petId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetDto> getPetById(@PathVariable("id") Long petId) {
        return ResponseEntity.ok(petService.getPetDto(petId));
    }

}
