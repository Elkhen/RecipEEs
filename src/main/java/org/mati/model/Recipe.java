package org.mati.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

@Entity
public class Recipe {
    @Id
    @GeneratedValue(generator = "recipe_generator")
    @SequenceGenerator(name = "recipe_generator", sequenceName = "recipe_seq", allocationSize = 1)
    @JsonIgnore
    private long id;

    @NotBlank
    private String name;

    @NotBlank
    private String category;

    private String date = LocalDateTime.now().toString();

    @NotBlank
    private String description;

    @NotNull
    @Size(min = 1)
    private String[] ingredients;

    @NotNull
    @Size(min = 1)
    private String[] directions;

    public Recipe() {
    }

    public Recipe(String name, String category, String description, @NotNull @Size(min = 1) String[] ingredients, @NotNull @Size(min = 1) String[] directions) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.ingredients = ingredients;
        this.directions = directions;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String[] getIngredients() {
        return ingredients;
    }

    public String[] getDirections() {
        return directions;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIngredients(String[] ingredients) {
        this.ingredients = ingredients;
    }

    public void setDirections(String[] directions) {
        this.directions = directions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return name.equals(recipe.name) && category.equals(recipe.category) && description.equals(recipe.description) && Arrays.equals(ingredients, recipe.ingredients) && Arrays.equals(directions, recipe.directions);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name, category, description);
        result = 31 * result + Arrays.hashCode(ingredients);
        result = 31 * result + Arrays.hashCode(directions);
        return result;
    }
}
