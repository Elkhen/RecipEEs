package org.mati;

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
    private static Logger logger = Logger.getLogger(RecipeRestClientTest.class);
    private static final String REST_TARGET_URL = "http://localhost:8080/recipes/api/recipe";

    @Test
    public void postAndGetTest() {
        logger.info("Testing the POST method at endpoint.");

        Recipe recipe = new Recipe();
        recipe.setName("Fresh Mint Tea");
        recipe.setDescription("Light, aromatic and refreshing beverage, ...");
        recipe.setIngredients("boiled water, honey, fresh mint leaves");
        recipe.setDirections("1) Boil water. " +
                "2) Pour boiling hot water into a mug. " +
                "3) Add fresh mint leaves. " +
                "4) Mix and let the mint leaves seep for 3-5 minutes. " +
                "5) Add honey and mix again.");

        Response response = ClientBuilder.newClient().target(REST_TARGET_URL).request().post(Entity.entity(recipe, MediaType.APPLICATION_JSON));
        Assert.assertEquals(response.getStatus(), 200);
        logger.info("Posting successful with status 200.");

        logger.info("Testing the GET method at endpoint.");
        Recipe fetchedRecipe = ClientBuilder.newClient().target(REST_TARGET_URL).request().get(Recipe.class);
        assertEquals(recipe.getName(), fetchedRecipe.getName());
        assertEquals(recipe.getDescription(), fetchedRecipe.getDescription());
        assertEquals(recipe.getIngredients(), fetchedRecipe.getIngredients());
        assertEquals(recipe.getDirections(), fetchedRecipe.getDirections());
        logger.info("Returned object with fields equal to the posted object.");
    }
}
