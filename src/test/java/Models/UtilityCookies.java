package Models;

import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.BeforeClass;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class UtilityCookies {
    protected Cookies cookies;

    @BeforeClass
    public void loginCampus()
    {
        baseURI = "https://test.mersys.io/";
        Map<String,String> credentials = new HashMap<>();


        cookies=
                given()
                        .contentType(ContentType.JSON)
                        .body(credentials)

                        .when()
                        .post("auth/login")

                        .then()
                        .statusCode(200)
                        .extract().response().getDetailedCookies()
        ;
    }
    public String getRandomName() {
        return RandomStringUtils.randomAlphabetic(6);
    }
    public String getRandomCode() {
        return RandomStringUtils.randomAlphabetic(3);
    }
    public String getRandomIban() {
        return RandomStringUtils.randomAlphabetic(20);
    }
}