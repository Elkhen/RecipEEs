package org.mati.test.endpoints;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.mati.model.Recipe;

import java.util.Map;

import static io.restassured.RestAssured.*;

public class GetRecipeTest {
    Recipe recipe = new Recipe(
            "Fresh Mint Tea",
            "beverage",
            "Light, aromatic and refreshing beverage, ... ",
            new String[]{"boiled water", "honey", "fresh mint leaves"},
            new String[]{"Boil water",
                    "Pour boiling hot water into a mug",
                    "Add fresh mint leaves",
                    "Mix and let the mint leaves seep for 3-5 minutes",
                    "Add honey and mix again"});

    int recipeId;

    @Before
    public void beforeClass() {
        baseURI = "http://localhost:8080/recipes/api/recipe";
        Response response = given()
                .contentType(ContentType.JSON)
                .body(recipe)
                .post("/new");
        recipeId = (int) response.getBody().as(Map.class).get("id");
    }

    @Test
    public void getRecipe_shouldReturn_200() {
        get("/" + recipeId)
                .then()
                .statusCode(200);
    }

    @Test
    public void getRecipe_shouldReturn400() {
        delete("/" + recipeId);

        get("/" + recipeId)
                .then()
                .statusCode(404);
    }

}
