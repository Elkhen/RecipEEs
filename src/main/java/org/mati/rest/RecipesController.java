package org.mati.rest;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.log4j.Logger;
import org.mati.data.RecipesRepository;
import org.mati.model.Recipe;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Path("/recipe")
@RequestScoped
public class RecipesController {

    @Inject
    private EntityManager em;
    @Inject
    private Logger logger;
    @Inject
    private RecipesRepository recipesRepository;
    @Inject
    private Validator validator;

    @POST
    @Path("/new")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addRecipe(Recipe recipe) {
        try {
            validateRecipe(recipe);
            recipesRepository.addRecipe(recipe);
        } catch (ConstraintViolationException cve) {
            return Response.status(400).build();
        }
        logger.info("Persisted " + recipe.getName() + " recipe");
        return Response.ok(Map.of("id", recipe.getId())).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRecipeById(@PathParam("id") int id) {
        Recipe recipe = recipesRepository.getRecipeById(id);
        return Response.ok(recipe).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteRecipe(@PathParam("id") int id) {
        try {
            recipesRepository.deleteRecipe(id);
        } catch (IllegalArgumentException e) {
            return Response.status(404).build();
        }
        return Response.status(204).build();
    }

    public void validateRecipe(Recipe recipe) {

        Set<ConstraintViolation<Recipe>> violations = validator.validate(recipe);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(new HashSet<>(violations));
        }
    }
}
