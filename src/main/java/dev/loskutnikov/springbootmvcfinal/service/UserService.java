package dev.loskutnikov.springbootmvcfinal.service;

import dev.loskutnikov.springbootmvcfinal.dto.UserDto;
import dev.loskutnikov.springbootmvcfinal.model.Pet;
import dev.loskutnikov.springbootmvcfinal.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    private final Map<Long, User> userList;

    private Long idCounter;

    private final UserMapper userMapper;

    private final PetMapper petMapper;

    public UserService(UserMapper userMapper, PetMapper petMapper) {
        this.userMapper = userMapper;
        this.petMapper = petMapper;
        this.idCounter = 0L;
        this.userList = new HashMap<>();
    }

    public UserDto createUser(UserDto userDto) {
        Long newID = ++idCounter;
        User user = new User();
        user.setId(newID);
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setAge(userDto.getAge());
        userList.put(newID, user);

        return userMapper.toDto(user);
    }

    public UserDto findById(Long id) {
        User user = Optional.ofNullable(userList.get(id))
                .orElseThrow(() -> new NoSuchElementException("User not found by id=%s".formatted(id)));
        return userMapper.toDto(user);
    }


    public void addPetToUser(Long userId, Pet pet) {
        List<Pet> pets = userList.get(userId).getPets();
        pets.add(pet);
    }
}
