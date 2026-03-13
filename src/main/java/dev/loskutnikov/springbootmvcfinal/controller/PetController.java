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

    @PostMapping("/{id}")
    public ResponseEntity<PetDto> createPet(
            @RequestBody @Valid PetDto petDto,
            @PathVariable("id") Long id) {
        var createdPet = petService.createPet(petDto, id);
        return ResponseEntity.ok(createdPet);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePet(@PathVariable("id") Long id) {
        petService.deletePet(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
