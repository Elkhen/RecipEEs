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

import java.util.Map;


public class RecipeRestClientTest
{
    private static final Logger logger = Logger.getLogger(RecipeRestClientTest.class);
    private static final String REST_TARGET_URL = "http://localhost:8080/recipes/api/recipe";

    @Test
    public void crudTests() {
        Recipe recipe = new Recipe();
        recipe.setName("Warming Ginger Tea");
        recipe.setDescription("Ginger tea is a warming drink for cool weather, ...");
        recipe.setIngredients(new String[]{"1 inch ginger root, minced", "1/2 lemon, juiced", "1/2 teaspoon manuka honey"});
        recipe.setDirections(new String[]{"Boil water", "Pour boiling hot water into a mug", "Add fresh mint leaves",
                "Mix and let the mint leaves seep for 3-5 minutes",
                "Add honey and mix again"});

        Recipe incorrectRecipe = new Recipe();
        incorrectRecipe.setName(null);
        incorrectRecipe.setDescription("Ginger tea is a warming drink for cool weather, ...");
        incorrectRecipe.setIngredients(new String[]{"1 inch ginger root, minced", "1/2 lemon, juiced", "1/2 teaspoon manuka honey"});
        incorrectRecipe.setDirections(new String[]{"Boil water", "Pour boiling hot water into a mug", "Add fresh mint leaves",
                "Mix and let the mint leaves seep for 3-5 minutes",
                "Add honey and mix again"});


        logger.info("POSTing the correct recipe.");

        Response correctResponse = ClientBuilder.newClient()
                .target(REST_TARGET_URL).path("/new")
                .request().post(Entity.entity(recipe, MediaType.APPLICATION_JSON));

        Assert.assertEquals(200, correctResponse.getStatus());


        logger.info("POSTing the incorrect recipe.");

        Response incorrectResponse = ClientBuilder.newClient()
                .target(REST_TARGET_URL).path("/new")
                .request().post(Entity.entity(incorrectRecipe, MediaType.APPLICATION_JSON));

        Assert.assertEquals(400, incorrectResponse.getStatus());

        int idOfPostedRecipe = (int) correctResponse.readEntity(Map.class).get("id");

        logger.info("GETting the recipe");

        Recipe fetchedRecipe = ClientBuilder.newClient()
                .target(REST_TARGET_URL).path("/{id}").resolveTemplate("id", idOfPostedRecipe)
                .request().get(Recipe.class);

        assertEquals(recipe, fetchedRecipe);


        logger.info("DELETing existing recipe.");

        Response deleteResponse = ClientBuilder.newClient()
                .target(REST_TARGET_URL).path("/{id}").resolveTemplate("id", idOfPostedRecipe)
                .request().delete();

        assertEquals(204, deleteResponse.getStatus());


        logger.info("Trying to DELETE a recipe that does not exist");

        deleteResponse = ClientBuilder.newClient()
                .target(REST_TARGET_URL).path("/{id}").resolveTemplate("id", idOfPostedRecipe)
                .request().delete();

        assertEquals(404, deleteResponse.getStatus());

    }
}
