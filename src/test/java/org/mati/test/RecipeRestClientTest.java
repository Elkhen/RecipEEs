package org.mati.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.mati.model.Recipe;



public class RecipeRestClientTest
{
    private static final Logger logger = Logger.getLogger(RecipeRestClientTest.class);
    private static final String REST_TARGET_URL = "http://localhost:8080/recipes/api/recipe";

    @Test
    public void postAndGetTest() {
        logger.info("Testing the POST method at endpoint.");

        Recipe recipe = new Recipe();
        recipe.setName("Warming Ginger Tea");
        recipe.setDescription("Ginger tea is a warming drink for cool weather, ...");
        recipe.setIngredients(new String[]{"1 inch ginger root, minced", "1/2 lemon, juiced", "1/2 teaspoon manuka honey"});
        recipe.setDirections(new String[]{"Boil water", "Pour boiling hot water into a mug", "Add fresh mint leaves",
                "Mix and let the mint leaves seep for 3-5 minutes",
                "Add honey and mix again"});

        Response response = ClientBuilder.newClient()
                .target(REST_TARGET_URL).path("/new")
                .request().post(Entity.entity(recipe, MediaType.APPLICATION_JSON));
        Assert.assertEquals(response.getStatus(), 200);

        logger.info("Posting successful with status 200.");
        logger.info("Testing the GET method at endpoint.");

        Recipe fetchedRecipe = ClientBuilder.newClient()
                .target(REST_TARGET_URL).path("/{id}").resolveTemplate("id", 1)
                .request().get(Recipe.class);

        assertEquals(recipe.getName(), fetchedRecipe.getName());
        assertEquals(recipe.getDescription(), fetchedRecipe.getDescription());
        assertArrayEquals(recipe.getIngredients(), fetchedRecipe.getIngredients());
        assertArrayEquals(recipe.getDirections(), fetchedRecipe.getDirections());

        logger.info("Returned object with fields equal to the posted object.");
    }
}
