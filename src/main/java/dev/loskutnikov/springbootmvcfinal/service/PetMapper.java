package dev.loskutnikov.springbootmvcfinal.service;

import dev.loskutnikov.springbootmvcfinal.dto.PetDto;
import dev.loskutnikov.springbootmvcfinal.model.Pet;
import org.springframework.stereotype.Component;

@Component
public class PetMapper {

    public PetDto toDto(Pet pet) {
        if (pet == null) {
            return null;
        }
        PetDto petDto = new PetDto();
        petDto.setId(pet.getId());
        petDto.setName(pet.getName());
        petDto.setUserId(pet.getUserId());
        return petDto;
    }

    public Pet toEntity(PetDto petDto) {
        if (petDto == null) {
            return null;
        }
        Pet pet = new Pet();
        pet.setId(petDto.getId());
        pet.setName(petDto.getName());
        pet.setUserId(petDto.getUserId());
        return pet;
    }
}
