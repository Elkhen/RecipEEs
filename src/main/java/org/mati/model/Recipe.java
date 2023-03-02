package org.mati.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@Entity
public class Recipe {

    @Id
    @GeneratedValue(generator = "recipe_generator")
    @SequenceGenerator(name = "recipe_generator", sequenceName = "recipe_seq", allocationSize = 1)
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String category;

    @EqualsAndHashCode.Exclude
    private String date = LocalDateTime.now().toString();

    @NotBlank
    private String description;

    @NotNull
    @Size(min = 1)
    private String[] ingredients;

    @NotNull
    @Size(min = 1)
    private String[] directions;
}
