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
import org.mati.data.RecipesDAO;
import org.mati.model.Recipe;

@Path("/recipe")
@RequestScoped
public class RecipesResource {


    @Context
    private UriInfo uriInfo;
    @Inject
    private EntityManager em;
    @Inject
    private Logger logger;
    @Inject
    private RecipesDAO recipesDAO;

    @POST
    @Path("/new")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addRecipe(@Valid Recipe recipe) {
        long idOfSavedRecipe = recipesDAO.addRecipe(recipe);
        return Response.status(201).header("Location", uriInfo.getBaseUri() + "recipe/" + idOfSavedRecipe).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRecipeById(@PathParam("id") int id) {
        return Response.ok(recipesDAO.findRecipeById(id)).build();
    }

    @DELETE
    @Path("/{id}")
    public void deleteRecipe(@PathParam("id") int id) {
        recipesDAO.deleteRecipe(id);
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateRecipe(@Valid Recipe recipe, @PathParam("id") int id) {
        recipesDAO.updateRecipe(recipe, id);
    }

    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchRecipes(@QueryParam("name") String name, @QueryParam("category") String category) {
        if ((name == null) == (category == null)) {
            return Response.status(400).build();
        } else {
            return recipesDAO.search(name, category);
        }
    }

    @Path("/{id}")
    public RecipeInfoResource getRecipeInfo(@PathParam("id") int id) {
        return new RecipeInfoResource(recipesDAO.findRecipeById(id));
    }
}
