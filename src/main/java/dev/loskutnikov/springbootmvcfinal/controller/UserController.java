package dev.loskutnikov.springbootmvcfinal.controller;

import dev.loskutnikov.springbootmvcfinal.dto.UserDto;
import dev.loskutnikov.springbootmvcfinal.model.User;
import dev.loskutnikov.springbootmvcfinal.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid UserDto userDto) {
        var createdUser = userService.createUser(userDto);
        return ResponseEntity.ok(createdUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findUserById(@PathVariable("id") Long userId) {
        return ResponseEntity.ok(userService.findById(userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable("id") Long userId,
            @RequestBody @Valid UserDto userDto) {
        var updateUser = userService.updateUser(userDto, userId);
        return ResponseEntity.ok(updateUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping
    public ResponseEntity<List<User>> findAllUsers() {
        List<User> userDtoList = userService.getAllUsers();
        return ResponseEntity.ok(userDtoList);
    }

}
