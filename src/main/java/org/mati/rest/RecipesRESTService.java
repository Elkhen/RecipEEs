package org.mati.rest;

import jakarta.persistence.EntityManager;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.mati.model.Recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Path("/recipe")
public class RecipesRESTService {
    private static List<Recipe> recipes = new ArrayList<>();
    @Inject
    private EntityManager em;

    @POST
    @Path("/new")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createRecipe(Recipe recipe) {
        recipes.add(recipe);
        return Response.ok(Map.of("id", recipes.size())).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") int id) {
        Recipe recipe = recipes.get(id);
        return Response.ok(recipe).build();
    }

}
