package Campus;

import Models.Attestations;
import Models.UtilityCookies;
import Models.positionCategories;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class AttestationsTest extends UtilityCookies {
    String id;

    @Test
    public void createAttestations() {
        Attestations attestations = new Attestations();
        attestations.setName(getRandomName());
        id =
                given()
                        .cookies(cookies)
                        .contentType(ContentType.JSON)
                        .body(attestations)

                        .when()
                        .post("school-service/api/attestation")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().jsonPath().getString("id");
    }

    @Test(dependsOnMethods = "createAttestations")
    public void editAttestations() {
        Attestations attestations = new Attestations();
        attestations.setId(id);
        attestations.setName(getRandomName());

        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(attestations)

                .when()
                .put("school-service/api/attestation")

                .then()
                .log().body()
                .statusCode(200);
    }

    @Test(dependsOnMethods = "editAttestations")
    public void deleteAttestations() {

        given()
                .cookies(cookies)
                .pathParam("id", id)


                .when()
                .delete("school-service/api/attestation/{id}")

                .then()
                .log().body()
                .statusCode(204);
    }
}
