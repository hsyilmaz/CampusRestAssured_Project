package Campus;

import Models.UtilityCookies;
import Models.gradeLevels;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class gradeLevelsTest extends UtilityCookies {
    String gradeId;
    String gname;
    String shortName;

    @Test
    public void createGradeLevels() {
        gname = getRandomName();
        shortName = getRandomName();
        gradeLevels gradeLevels = new gradeLevels();
        gradeLevels.setName(gname);
        gradeLevels.setShortName(shortName);
        gradeLevels.setOrder("1");
        gradeId =
                given()
                        .cookies(cookies)
                        .contentType(ContentType.JSON)
                        .body(gradeLevels)

                        .when()
                        .post("school-service/api/grade-levels")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().jsonPath().getString("id");
    }

    @Test(dependsOnMethods = "createGradeLevels")
    public void editGradeLevels() {
        gname = getRandomName();
        shortName = getRandomName();
        gradeLevels gradeLevels = new gradeLevels();
        gradeLevels.setId(gradeId);
        gradeLevels.setName(gname);
        gradeLevels.setShortName(shortName);
        gradeLevels.setOrder("1");

        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(gradeLevels)

                .when()
                .put("school-service/api/grade-levels")

                .then()
                .log().body()
                .statusCode(200);
    }

    @Test(dependsOnMethods = "editGradeLevels")
    public void deleteGradeLevels() {

        given()
                .cookies(cookies)
                .pathParam("id",gradeId)


                .when()
                .delete("school-service/api/grade-levels/{id}")

                .then()
                .log().body()
                .statusCode(200);
    }
}
