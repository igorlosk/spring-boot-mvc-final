package dev.loskutnikov.springbootmvcfinal.controller;


import dev.loskutnikov.springbootmvcfinal.dto.PetDto;
import dev.loskutnikov.springbootmvcfinal.dto.UserDto;
import dev.loskutnikov.springbootmvcfinal.model.Pet;
import dev.loskutnikov.springbootmvcfinal.model.User;
import dev.loskutnikov.springbootmvcfinal.service.PetService;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class PetControllerTest {
    @Autowired
    private UserService userService;
    @Autowired
    private PetService petsService;
    @Autowired
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void shouldCreateNewPet() throws Exception {
        var user = userService.createUser(new UserDto(
                null,
                "email@email.com",
                "name",
                30,
                List.of()
        ));

        var petDto = new PetDto(null, "Vasya", user.getId());

        String newPetJson = objectMapper.writeValueAsString(petDto);

        var jsonResponse = mockMvc.perform(post("/api/users/pets/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newPetJson))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        var petDtoResponse = objectMapper.readValue(jsonResponse, PetDto.class);
        Assertions.assertEquals(petDto.getName(), petDtoResponse.getName());
        Assertions.assertEquals(petDto.getUserId(), petDtoResponse.getUserId());
        Assertions.assertNotNull(petDtoResponse.getId());

        Assertions.assertDoesNotThrow(() -> petsService.getPet(petDtoResponse.getId()));

        var userWithPetDto = userService.findUserById(user.getId());
        Assertions.assertEquals(1, userWithPetDto.getPets().size());
        Assertions.assertEquals(petDtoResponse.getId(), userWithPetDto.getPets().get(0).getId());


    }
}


