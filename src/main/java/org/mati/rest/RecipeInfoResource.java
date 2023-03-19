package org.mati.rest;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.mati.model.Recipe;

public class RecipeInfoResource {
    private Recipe recipe;

    public RecipeInfoResource(Recipe recipe) {
        this.recipe = recipe;
    }

    @GET
    @Path("/name")
    public String getRecipeName() {
        return recipe.getName();
    }
}
