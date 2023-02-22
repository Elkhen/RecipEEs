package org.mati.rest;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.mati.model.Recipe;

@Path("/recipe")
public class RecipesRESTService {
    private static Recipe recipe;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createRecipe(Recipe recipeData) {
        recipe = recipeData;
        return Response.ok().build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRecipe() {
        return Response.ok(recipe).build();
    }

}
