package org.mati.test;

import jakarta.inject.Inject;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.apache.log4j.Logger;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mati.data.RecipesRepository;
import org.mati.model.Recipe;
import org.mati.util.Utils;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class ArquillianDatabaseTest {

    @Deployment
    public static Archive<?> createTestArchive() {
        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addClasses(Recipe.class, RecipesRepository.class, Utils.class)
                .addPackages(true, "org.apache.log4j")
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsResource("log4j.properties")
                .addAsWebInfResource("test-beans.xml", "beans.xml")
                .addAsWebInfResource("test-ds.xml");
    }

    @Inject
    RecipesRepository recipesRepository;

    @Inject
    Logger logger;

    @PersistenceContext
    EntityManager em;

    @Test
    public void testSaveAndFind() {
        Recipe recipe = new Recipe();
        recipe.setName("Warming Ginger Tea");
        recipe.setCategory("beverage");
        recipe.setDescription("Ginger tea is a warming drink for cool weather, ...");
        recipe.setIngredients(new String[]{"1 inch ginger root, minced", "1/2 lemon, juiced", "1/2 teaspoon manuka honey"});
        recipe.setDirections(new String[]{"Boil water", "Pour boiling hot water into a mug", "Add fresh mint leaves",
                "Mix and let the mint leaves seep for 3-5 minutes",
                "Add honey and mix again"});



        recipesRepository.addRecipe(recipe);

        assertNotNull(recipe.getId());

        logger.info("Recipe for " + recipe.getName() + " persisted with id " + recipe.getId());


        Recipe fetchedRecipe = recipesRepository.findRecipeById(recipe.getId());

        assertEquals(recipe, fetchedRecipe);

        logger.info("Retrieved recipe equal to persisted one.");
    }

}
