package org.mati.test.endpoints;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.mati.model.Recipe;


public class AddRecipeTest {
    Recipe recipe = new Recipe(
            "Fresh Mint Tea",
            "beverage",
            "Light, aromatic and refreshing beverage, ... ",
            new String[]{"boiled water", "honey", "fresh mint leaves"},
            new String[]{"Boil water",
                    "Pour boiling hot water into a mug",
                    "Add fresh mint leaves",
                    "Mix and let the mint leaves seep for 3-5 minutes",
                    "Add honey and mix again"}
    );

    Recipe incorrectRecipe = new Recipe(
            "Fresh Mint Tea",
            null,
            "Light, aromatic and refreshing beverage, ...",
            new String[]{"boiled water", "honey", "fresh mint leaves"},
            new String[]{"Boil water", "Pour boiling hot water into a mug",
                    "Add fresh mint leaves",
                    "Mix and let the mint leaves seep for 3-5 minutes",
                    "Add honey and mix again"}
    );

    @Before
    public void beforeClass() {
        baseURI = "http://localhost:8080/recipes/api/recipe";
    }

    @Test
    public void addRecipe_shouldReturn_200_andBodyWithId() {
        given().
                contentType(ContentType.JSON).
                body(recipe).
        when().
                post("/new").
        then()
                .statusCode(200)
                .body(containsString("id"));
    }

    @Test
    public void addRecipe_shouldReturn_400() {
        given().
                contentType(ContentType.JSON).
                body(incorrectRecipe).
        when().
                post("/new").
        then()
                .statusCode(400);
    }
}
