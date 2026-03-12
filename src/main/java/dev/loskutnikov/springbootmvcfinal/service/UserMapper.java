package dev.loskutnikov.springbootmvcfinal.service;

import dev.loskutnikov.springbootmvcfinal.dto.PetDto;
import dev.loskutnikov.springbootmvcfinal.dto.UserDto;
import dev.loskutnikov.springbootmvcfinal.model.Pet;
import dev.loskutnikov.springbootmvcfinal.model.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    private final PetMapper petMapper = new PetMapper();

    public UserDto toDto(User user) {
        if (user == null) {
            return null;
        }
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setAge(user.getAge());
        userDto.setPets(mapPetsToDtos(user.getPets()));
        return userDto;
    }

    public User toEntity(UserDto userDto) {
        if (userDto == null) {
            return null;
        }
        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setAge(userDto.getAge());
        user.setPets(mapDtosToPets(userDto.getPets()));
        return user;
    }

    private List<PetDto> mapPetsToDtos(List<Pet> pets) {
        if (pets == null) {
            return null;
        }
        return pets.stream()
                .map(petMapper::toDto)
                .collect(Collectors.toList());
    }


    private List<Pet> mapDtosToPets(List<PetDto> petDtos) {
        if (petDtos == null) {
            return null;
        }
        return petDtos.stream()
                .map(petMapper::toEntity)
                .collect(Collectors.toList());
    }
}
