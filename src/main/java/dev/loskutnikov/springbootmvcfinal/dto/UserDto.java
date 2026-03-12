package dev.loskutnikov.springbootmvcfinal.dto;

import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.List;

public class UserDto {
    private Long id;

    @NotBlank
    private String name;

    @Email
    private String email;

    @NotNull
    @Positive
    @Max(120)
    private Integer age;

    private List<PetDto> pets;

    public UserDto() {
        this.pets = new ArrayList<>();
    }

//    public UserDto(Long id, String name, String email, Integer age, List<PetDto> pets) {
//        this.id = id;
//        this.name = name;
//        this.email = email;
//        this.age = age;
//        this.pets = pets != null ? new ArrayList<>(pets) : new ArrayList<>();
//    }

//    public void addPet(PetDto petDto){
//        if (petDto == null) {
//            throw new IllegalArgumentException("Нельзя добавить null-питомца");
//        }
//        pets.add(petDto);
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<PetDto> getPets() {
        return pets;
    }

    public void setPets(List<PetDto> pets) {
        this.pets = pets;
    }
}
