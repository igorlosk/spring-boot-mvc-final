package dev.loskutnikov.springbootmvcfinal.service;

import dev.loskutnikov.springbootmvcfinal.dto.UserDto;
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
        var newId = ++idCounter;
        var createdUser = new UserDto(
                newId,
                userDto.getName(),
                userDto.getEmail(),
                userDto.getAge(),
                userDto.getPets()
        );
        userList.put(newId, userMapper.toEntity(createdUser));
        return createdUser;
    }

    public UserDto findUserById(Long id) {
        User user = Optional.ofNullable(userList.get(id))
                .orElseThrow(() -> new NoSuchElementException("Book not found by id=%s".formatted(id)));
        return userMapper.toDto(user);
    }

    public UserDto updateUserById(Long id, UserDto userDto) {
        if (userList.get(id) == null) {
            throw new NoSuchElementException("Book not found by id=%s".formatted(id));
        }
        var updatedUser = new UserDto(
                id,
                userDto.getName(),
                userDto.getEmail(),
                userDto.getAge(),
                userDto.getPets()
        );
        userList.put(id, userMapper.toEntity(updatedUser));
        return updatedUser;
    }

    public void deleteUserById(Long id) {
        User remove = userList.remove(id);
        if (remove == null) {
            throw new NoSuchElementException("Book not found by id=%s".formatted(id));
        }
    }
}
