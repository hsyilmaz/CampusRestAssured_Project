package Campus;

import Models.SbjCategory;
import Models.UtilityCookies;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class SubjCategoryTest extends UtilityCookies {
    String sbjCatId;


    @Test
    public void createCategory(){
        SbjCategory category = new SbjCategory();
        category.setName(getRandomName());
        category.setCode(getRandomCode());
        sbjCatId =
                given()
                        .cookies(cookies)
                        .contentType(ContentType.JSON)
                        .body(category)

                        .when()
                        .post("school-service/api/subject-categories")
                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().jsonPath().getString("id")
                ;
    }
    @Test(dependsOnMethods = "createCategory")
    public void updateCategory(){
        SbjCategory category = new SbjCategory();
        category.setName(getRandomName());
        category.setCode(getRandomCode());
        category.setId(sbjCatId);

                         given()
                                 .cookies(cookies)
                                 .contentType(ContentType.JSON)
                                 .body(category)

                                 .when()
                                 .put("school-service/api/subject-categories")

                                 .then()
                                 .log().body()
                                 .statusCode(200)
                         ;
    }
    @Test(dependsOnMethods = "updateCategory" )
    public void deleteCategoryById(){

                        given()
                                .cookies(cookies)
                                .contentType(ContentType.JSON)
                                .pathParam("sbjCatId",sbjCatId)

                                .when()
                                .delete("school-service/api/subject-categories/{sbjCatId}")

                                .then()
                                .log().body()
                                .statusCode(200)
                        ;
    }

}
