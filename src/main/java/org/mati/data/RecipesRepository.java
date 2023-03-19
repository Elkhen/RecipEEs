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
    @Inject
    private EntityManager em;

    public long addRecipe(Recipe recipe) {
        em.persist(recipe);
        return recipe.getId();
    }

    public void deleteRecipe(long id) {
        Recipe recipe = findRecipeById(id);
        em.remove(recipe);
    }

    public void updateRecipe(Recipe updatedRecipe, long id) {
        updatedRecipe.setId(id);
        em.merge(updatedRecipe);
    }

    public Recipe findRecipeById(long id) {
        Recipe recipe = em.find(Recipe.class, id);
        if (recipe == null) {
            throw new IllegalArgumentException();
        }
        return recipe;
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
