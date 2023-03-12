package org.mati.test.endpoints;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import io.restassured.http.ContentType;
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
                    "Add honey and mix again"});
    @Test
    public void addRecipe_shouldReturn_200_andBodyWithId() {
        baseURI = "http://localhost:8080/recipes/api/recipe";
        given()
                .contentType(ContentType.JSON)
                .body(recipe)
                .post("/new")
                .then()
                .statusCode(200)
                .body(containsString("id"));

    }
}
