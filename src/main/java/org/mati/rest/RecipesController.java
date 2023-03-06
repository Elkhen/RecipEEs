package org.mati.rest;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.log4j.Logger;
import org.mati.data.RecipesRepository;
import org.mati.model.Recipe;

@Path("/recipe")
@RequestScoped
public class RecipesController {

    @Inject
    private EntityManager em;
    @Inject
    private Logger logger;
    @Inject
    private RecipesRepository recipesRepository;

    @POST
    @Path("/new")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addRecipe(@Valid Recipe recipe) {
        return recipesRepository.addRecipe(recipe);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRecipeById(@PathParam("id") int id) {
        return recipesRepository.getRecipeById(id);
    }

    @DELETE
    @Path("/{id}")
    public Response deleteRecipe(@PathParam("id") int id) {
        return recipesRepository.deleteRecipe(id);
    }

    @PUT
    @Path("/{id}")
    public Response updateRecipe(@Valid Recipe recipe, @PathParam("id") int id) {
        return recipesRepository.updateRecipe(recipe, id);
    }
}
