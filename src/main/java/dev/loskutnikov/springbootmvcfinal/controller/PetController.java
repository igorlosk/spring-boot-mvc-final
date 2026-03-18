package dev.loskutnikov.springbootmvcfinal.controller;

import dev.loskutnikov.springbootmvcfinal.dto.PetDto;
import dev.loskutnikov.springbootmvcfinal.service.PetService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pets")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping()
    public ResponseEntity<PetDto> createPet(@RequestBody @Valid PetDto petDto) {
        var petDtoCreate = petService.createPet(petDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("Location", String.format("/api/pets/%d", petDtoCreate.getId()))
                .body(petDtoCreate);
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
