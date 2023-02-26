package org.mati.rest;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.mati.data.RecipeRepository;
import org.mati.model.Recipe;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Path("/recipe")
@RequestScoped
public class RecipesRESTService {

    @Inject
    private EntityManager em;
    @Inject
    private Logger logger;
    @Inject
    private RecipeRepository recipeRepository;

    @POST
    @Path("/new")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createRecipe(Recipe recipe) {
        recipeRepository.saveRecipe(recipe);
        logger.info("Persisted " + recipe.getName() + " recipe");
        return Response.ok(Map.of("id", recipe.getId())).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") int id) {
        Recipe recipe = recipeRepository.getRecipeById(id);
        return Response.ok(recipe).build();
    }

}
