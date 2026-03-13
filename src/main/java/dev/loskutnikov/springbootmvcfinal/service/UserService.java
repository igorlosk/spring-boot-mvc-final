package dev.loskutnikov.springbootmvcfinal.service;

import dev.loskutnikov.springbootmvcfinal.dto.UserDto;
import dev.loskutnikov.springbootmvcfinal.model.Pet;
import dev.loskutnikov.springbootmvcfinal.model.User;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    private final Map<Long, User> userList;

    private Long idCounter;

    private final UserMapper userMapper;


    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
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

    public void deletePetFromUser(Long userId, Pet pet) {
        User user = Optional.ofNullable(userList.get(userId))
                .orElseThrow(() -> new NoSuchElementException("User not found by id=%s".formatted(userId)));
        user.getPets().remove(pet);
    }

    public UserDto updateUser(UserDto userDto, Long userId) {
        UserDto updatedUser = Optional.ofNullable(userMapper.toDto(userList.get(userId)))
                .orElseThrow(() -> new NoSuchElementException("User not found by id=%s".formatted(userId)));
        updatedUser.setName(userDto.getName());
        updatedUser.setEmail(userDto.getEmail());
        updatedUser.setAge(userDto.getAge());
        userList.put(userId, userMapper.toEntity(updatedUser));
        return updatedUser;
    }

    public void deleteUser(Long userId) {
        UserDto deletedUser = findById(userId);
        userList.remove(deletedUser.getId());
    }

    public List<User> getAllUsers() {
        List<User> list = userList.values().stream().toList();
        if (list.isEmpty()) {
            throw new NoSuchElementException("Users list is empty");
        }
        return list;
    }
}
