package org.mati.data;


import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import org.mati.model.Recipe;

@Stateless
public class RecipeRepository {

    @Inject
    private EntityManager em;

    public void saveRecipe(Recipe recipe) {
        em.persist(recipe);
    }

    public Recipe getRecipeById(long id) {
        return em.find(Recipe.class, id);
    }
}
