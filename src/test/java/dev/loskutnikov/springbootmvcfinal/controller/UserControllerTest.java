package dev.loskutnikov.springbootmvcfinal.controller;

import dev.loskutnikov.springbootmvcfinal.dto.PetDto;
import dev.loskutnikov.springbootmvcfinal.dto.UserDto;
import dev.loskutnikov.springbootmvcfinal.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldSuccessCreateUser() throws Exception {
        var newUserDto = new UserDto();
        newUserDto.setId(1L);
        newUserDto.setEmail("email@email.com");
        newUserDto.setName("name");
        newUserDto.setAge(30);
        newUserDto.setPets(List.copyOf(new ArrayList<PetDto>()));

        String userJson = objectMapper.writeValueAsString(newUserDto);
        String createdUserJson = mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().is(201))
                .andReturn()
                .getResponse()
                .getContentAsString();

        UserDto userDtoResponse = objectMapper.readValue(createdUserJson, UserDto.class);
        Assertions.assertNotNull(userDtoResponse.getId());
        Assertions.assertEquals(newUserDto.getName(), userDtoResponse.getName());
    }

    @Test
    void shouldSuccessSearchUserById() throws Exception {
        var user = new UserDto();
        user.setId(1L);
        user.setEmail("email@email.com");
        user.setName("name");
        user.setAge(30);
        user.setPets(List.copyOf(new ArrayList<PetDto>()));

        user = userService.createUser(user);

        String foundUserJson = mockMvc.perform(get("/api/users/{id}", user.getId()))
                .andExpect(status().is(200))
                .andReturn()
                .getResponse()
                .getContentAsString();

        UserDto userDtoResponse = objectMapper.readValue(foundUserJson, UserDto.class);

        org.assertj.core.api.Assertions
                .assertThat(user)
                .usingRecursiveComparison().isEqualTo(userDtoResponse);

    }

    @Test
    void updateUser_shouldReturnUpdatedUser() throws Exception {
        var user = new UserDto();
        user.setId(1L);
        user.setEmail("update@email.com");
        user.setName("updateName");
        user.setAge(30);
        user.setPets(List.copyOf(new ArrayList<PetDto>()));

        user = userService.createUser(user);

        mockMvc.perform(put("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("updateName"))
                .andExpect(jsonPath("$.age").value(30))
                .andExpect(jsonPath("$.email").value("update@email.com"));


    }

    @Test
    void deleteUser_shouldReturn204() throws Exception {
        var newUserDto = new UserDto();
        newUserDto.setId(1L);
        newUserDto.setEmail("email@email.com");
        newUserDto.setName("name");
        newUserDto.setAge(30);
        newUserDto.setPets(List.copyOf(new ArrayList<PetDto>()));

        newUserDto = userService.createUser(newUserDto);

        mockMvc.perform(delete("/api/users/{id}", newUserDto.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(204));

    }
}