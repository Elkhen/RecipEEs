package org.mati.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.mati.model.Recipe;

import java.util.Map;


public class RecipeRestClientTest
{
    private static final Logger logger = Logger.getLogger(RecipeRestClientTest.class);
    private static final String REST_TARGET_URL = "http://localhost:8080/recipes/api/recipe";

    final Recipe[] RECIPES = {
        new Recipe(
                    "Fresh Mint Tea",
                    "beverage",
                    "Light, aromatic and refreshing beverage, ... ",
                    new String[]{"boiled water", "honey", "fresh mint leaves"},
                    new String[]{"Boil water",
                            "Pour boiling hot water into a mug",
                            "Add fresh mint leaves",
                            "Mix and let the mint leaves seep for 3-5 minutes",
                            "Add honey and mix again"}
        ),
        new Recipe(
                    "Warming Ginger Tea",
                    "beverage",
                    "Ginger tea is a warming drink for cool weather, ...",
                    new String[]{"1 inch ginger root, minced", "1/2 lemon, juiced", "1/2 teaspoon manuka honey"},
                    new String[]{"Place all ingredients in a mug and fill with warm water " +
                            "(not too hot so you keep the beneficial honey compounds in tact)",
                            "Steep for 5-10 minutes", "Drink and enjoy"}
        )
    };

    final Recipe[] INCORRECT_RECIPES = {
            //0
            new Recipe(
                    null,
                    "beverage",
                    "Light, aromatic and refreshing beverage, ...",
                    new String[]{"boiled water", "honey", "fresh mint leaves"},
                    new String[]{"Boil water", "Pour boiling hot water into a mug",
                            "Add fresh mint leaves",
                            "Mix and let the mint leaves seep for 3-5 minutes",
                            "Add honey and mix again"}
            ),
            //1
            new Recipe(
                    "Fresh Mint Tea",
                    null,
                    "Light, aromatic and refreshing beverage, ...",
                    new String[]{"boiled water", "honey", "fresh mint leaves"},
                    new String[]{"Boil water", "Pour boiling hot water into a mug",
                            "Add fresh mint leaves",
                            "Mix and let the mint leaves seep for 3-5 minutes",
                            "Add honey and mix again"}
            )
    };

    @Test
    public void crudTests() {
        logger.info("Adding the recipe.");

        Response correctRecipeResponse = addTest(RECIPES[0]);
        assertEquals(200, correctRecipeResponse.getStatus());

        Response incorrectRecipeResponse = addTest(INCORRECT_RECIPES[0]);
        assertEquals(400, incorrectRecipeResponse.getStatus());

        int idOfPostedRecipe = (int) correctRecipeResponse.readEntity(Map.class).get("id");


        logger.info("Getting the recipe.");

        Recipe fetchedRecipe = getTest(idOfPostedRecipe);
        assertEquals(RECIPES[0], fetchedRecipe);


        logger.info("Updating the recipe.");

        RECIPES[0].setName("NotSoFresh Mint Tea");
        Response updateResponse =  updateTest(RECIPES[0], idOfPostedRecipe);
        assertEquals(204, updateResponse.getStatus());

        Recipe fetchedRecipeAfterUpdate = getTest(idOfPostedRecipe);
        assertEquals(RECIPES[0], fetchedRecipeAfterUpdate);


        logger.info("Deleting the recipe.");

        Response deleteResponse = deleteTest(idOfPostedRecipe);
        assertEquals(204, deleteResponse.getStatus());

        Response deleteNotFoundResponse = deleteTest(idOfPostedRecipe);
        assertEquals(404, deleteNotFoundResponse.getStatus());

    }

    public Response addTest(Recipe recipe) {
        return ClientBuilder.newClient()
                .target(REST_TARGET_URL).path("/new")
                .request().post(Entity.entity(recipe, MediaType.APPLICATION_JSON));

    }

    public Recipe getTest(long id) {
        return ClientBuilder.newClient()
                .target(REST_TARGET_URL).path("/{id}").resolveTemplate("id", id)
                .request().get(Recipe.class);
    }

    public Response updateTest(Recipe recipe, long id) {
        return ClientBuilder.newClient()
                .target(REST_TARGET_URL).path("/{id}").resolveTemplate("id", id)
                .request().put(Entity.entity(recipe, MediaType.APPLICATION_JSON));
    }

    public Response deleteTest(long id) {
        return ClientBuilder.newClient()
                .target(REST_TARGET_URL).path("/{id}").resolveTemplate("id", id)
                .request().delete();
    }
}
