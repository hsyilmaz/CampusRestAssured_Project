package Campus;

import Models.Position;
import Models.UtilityCookies;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class PositionTest extends UtilityCookies {
    String positionID;

    @Test
    public void createPosition() {
        Position position = new Position();
        position.setName(getRandomName());
        position.setShortName(getRandomCode());

        positionID =
                given()
                        .cookies(cookies)
                        .body(position)
                        .contentType(ContentType.JSON)
                        .when()
                        .post("school-service/api/employee-position")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().jsonPath().getString("id")
        ;
    }

    @Test(dependsOnMethods = "createPosition")
    public void updatePosition() {
        Position position = new Position();
        position.setId(positionID);
        position.setName(getRandomName());
        position.setShortName(getRandomCode());

        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(position)

                .when()
                .put("school-service/api/employee-position")

                .then()
                .log().body()
                .statusCode(200)
        ;
    }

    @Test(dependsOnMethods = "updatePosition")
    public void deletePositionById() {

        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .pathParam("positionID", positionID)

                .when()
                .delete("school-service/api/employee-position/{positionID}")

                .then()
                .statusCode(204)
        ;

    }

}


