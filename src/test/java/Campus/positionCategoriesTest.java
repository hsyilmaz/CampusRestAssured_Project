package Campus;

import Models.UtilityCookies;
import Models.gradeLevels;
import Models.positionCategories;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class positionCategoriesTest extends UtilityCookies {

    String id;

    @Test
    public void createpositionCategories() {
        positionCategories positionCategories = new positionCategories();
        positionCategories.setName(getRandomName());
        id =
                given()
                        .cookies(cookies)
                        .contentType(ContentType.JSON)
                        .body(positionCategories)

                        .when()
                        .post("school-service/api/position-category")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().jsonPath().getString("id");
    }

    @Test(dependsOnMethods = "createpositionCategories")
    public void editpositionCategories() {
        positionCategories positionCategories = new positionCategories();
        positionCategories.setId(id);
        positionCategories.setName(getRandomName());

        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(positionCategories)

                .when()
                .put("school-service/api/position-category")

                .then()
                .log().body()
                .statusCode(200);
    }

    @Test(dependsOnMethods = "editpositionCategories")
    public void deletepositionCategories() {

        given()
                .cookies(cookies)
                .pathParam("id", id)


                .when()
                .delete("school-service/api/position-category/{id}")

                .then()
                .log().body()
                .statusCode(204);
    }
}
