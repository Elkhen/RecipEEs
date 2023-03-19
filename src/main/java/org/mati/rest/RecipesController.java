package org.mati.rest;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.apache.log4j.Logger;
import org.mati.data.RecipesRepository;
import org.mati.model.Recipe;

import java.net.URI;

@Path("/recipe")
@RequestScoped
public class RecipesController {


    @Context
    private UriInfo uriInfo;
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
        long idOfSavedRecipe = recipesRepository.addRecipe(recipe);
        return Response.status(201).header("Location", uriInfo.getBaseUri() + "recipe/" + idOfSavedRecipe).build();
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
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateRecipe(@Valid Recipe recipe, @PathParam("id") int id) {
        return recipesRepository.updateRecipe(recipe, id);
    }

    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchRecipes(@QueryParam("name") String name, @QueryParam("category") String category) {
        if ((name == null) == (category == null)) {
            return Response.status(400).build();
        } else {
            return recipesRepository.search(name, category);
        }
    }
}
