package org.mati.data;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.mati.model.Recipe;

@ApplicationScoped
@Transactional
public class RecipesRepository {

    @Inject
    private EntityManager em;

    public void addRecipe(Recipe recipe) {
        em.persist(recipe);
    }

    public Recipe getRecipeById(long id) {
        return em.find(Recipe.class, id);
    }

    public void deleteRecipe(long id) {
        Recipe recipe = em.find(Recipe.class, id);
        em.remove(recipe);
    }
}
