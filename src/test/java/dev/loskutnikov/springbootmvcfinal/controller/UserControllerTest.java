package dev.loskutnikov.springbootmvcfinal.controller;

import dev.loskutnikov.springbootmvcfinal.dto.UserDto;
import dev.loskutnikov.springbootmvcfinal.service.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

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
        var user = new UserDto(
                1L,
                "some_name",
                "email@gmail.com",
                30,
                List.of()
        );

        String userJson = objectMapper.writeValueAsString(user);
        String createdUserJson = mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        UserDto userDtoResponse = objectMapper.readValue(createdUserJson, UserDto.class);

        Assertions.assertNotNull(userDtoResponse.getId());
        Assertions.assertEquals(user.getName(), userDtoResponse.getName());
        Assertions.assertEquals(user.getEmail(), userDtoResponse.getEmail());
        Assertions.assertEquals(user.getAge(), userDtoResponse.getAge());

        Assertions.assertDoesNotThrow(() -> userService.findUserById(userDtoResponse.getId()));

    }

    @Test
    void shouldSuccessSearchUserById() throws Exception {
        var user = new UserDto(
                1L,
                "some_name",
                "email@gmail.com",
                30,
                List.of()
        );

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
        var user = new UserDto(
                1L,
                "updateName",
                "update@email.com",
                30,
                List.of()
        );

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
        var user = new UserDto(
                1L,
                "some_name",
                "email@gmail.com",
                30,
                List.of()
        );

        user = userService.createUser(user);

        mockMvc.perform(delete("/api/users/{id}", user.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

    }
}