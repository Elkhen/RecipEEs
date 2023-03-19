package org.mati.test.endpoints;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.mati.model.Recipe;

import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.assertEquals;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

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

    String recipeURI;

    @Before
    public void beforeClass() {
        Response response =
                given().
                        contentType(ContentType.JSON).
                        body(recipe).
                post("http://localhost:8080/recipes/api/recipe/new");
        recipeURI = response.getHeader("Location");
    }

    @Test
    public void getRecipe_shouldReturn_200() {
        Recipe extractedRecipe =
        when().
                get(recipeURI).
        then().
                statusCode(200).
        extract().
                response().
                as(Recipe.class);

        assertEquals(recipe, extractedRecipe);
    }

    @Test
    public void getRecipe_shouldReturn404() {
        delete(recipeURI);

        when().
                get(recipeURI).
        then().
                statusCode(404);
    }

}
