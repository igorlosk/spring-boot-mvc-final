package dev.loskutnikov.springbootmvcfinal.controller;


import dev.loskutnikov.springbootmvcfinal.dto.*;
import dev.loskutnikov.springbootmvcfinal.service.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
                .andExpect(status().isCreated())
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

    @Test
    void shouldDeletePetById() throws Exception {

        var user = userService.createUser(new UserDto(
                null,
                "email@email.com",
                "name",
                30,
                List.of()
        ));

        var petDto = new PetDto(null, "Vasya", user.getId());
        String petJson = objectMapper.writeValueAsString(petDto);

        var result = mockMvc.perform(post("/api/users/pets/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(petJson))
                .andExpect(status().isCreated())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        PetDto createdPet = objectMapper.readValue(responseBody, PetDto.class);
        Long petId = createdPet.getId();

        mockMvc.perform(delete("/api/users/pets/{id}", petId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/users/pets/{id}", petId))
                .andExpect(status().isNotFound());
    }
}


