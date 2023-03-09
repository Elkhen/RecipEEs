package org.mati.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
public class Recipe {

    public Recipe(String name, String category, String description, @NotNull @Size(min = 1) String[] ingredients, @NotNull @Size(min = 1) String[] directions) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.ingredients = ingredients;
        this.directions = directions;
    }

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
