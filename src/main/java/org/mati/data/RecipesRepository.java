package org.mati.data;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import org.mati.model.Recipe;

import java.util.List;

@ApplicationScoped
@Transactional
public class RecipesRepository {
    enum Action {
        UPDATE,
        DELETE,
        GET
    }
    @Inject
    private EntityManager em;

    public long addRecipe(Recipe recipe) {
        em.persist(recipe);
        return recipe.getId();
    }

    public Response getRecipeById(long id) {
        return processRecipe(Action.GET, null, id, 404, 200);
    }

    public Response deleteRecipe(long id) {
        return processRecipe(Action.DELETE, null, id, 404, 204);
    }

    public Response updateRecipe(Recipe updatedRecipe, long id) {
        return processRecipe(Action.UPDATE, updatedRecipe, id, 404, 204);
    }

    public Recipe findRecipeById(long id) {
        Recipe recipe = em.find(Recipe.class, id);
        if (recipe == null) {
            throw new IllegalArgumentException();
        }
        return recipe;
    }

    public Response processRecipe(Action action, Recipe receivedRecipe, long id, int errorStatus, int successStatus) {
        try {
            Recipe persistedRecipe = findRecipeById(id);
            switch (action) {
                case GET:
                    return Response.ok(persistedRecipe).build();
                case DELETE:
                    em.remove(persistedRecipe);
                    break;
                case UPDATE:
                    receivedRecipe.setId(id);
                    em.merge(receivedRecipe);
            }
        } catch (IllegalArgumentException exception) {
            return Response.status(errorStatus).build();
        }
        return Response.status(successStatus).build();
    }

    public Response search(String name, String category) {
        List<Recipe> recipes;
        if (name != null) {
            recipes = em.createQuery(
                            "SELECT r from Recipe r where lower(name) like :name order by date desc", Recipe.class)
                    .setParameter("name", "%" + name.toLowerCase() + "%")
                    .getResultList();
        } else {
            recipes = em.createQuery(
                            "SELECT r from Recipe r where lower(category) = :category order by date desc", Recipe.class)
                    .setParameter("category", category.toLowerCase())
                    .getResultList();
        }
        return Response.ok(recipes).build();
    }
}
