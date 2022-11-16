package Campus;

import Models.BankAccount;
import Models.UtilityCookies;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class BankAccountTest extends UtilityCookies {
    String bankId;
    String name;
    String iban;
    String code;

    @Test
    public void createBankAccount() {
        name = getRandomName();
        code = getRandomCode();
        iban = "TR" + getRandomIban();
        BankAccount bankAccount = new BankAccount();
        bankAccount.setName(name);
        bankAccount.setIban(iban);
        bankAccount.setIntegrationCode(code);
        bankAccount.setCurrency("TRY");
        bankAccount.setSchoolId("5fe07e4fb064ca29931236a5");

        bankId =
                given()
                        .cookies(cookies)
                        .contentType(ContentType.JSON)
                        .body(bankAccount)

                        .when()
                        .post("school-service/api/bank-accounts")

                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().jsonPath().getString("id");
    }

    @Test(dependsOnMethods = "createBankAccount")
    public void editBankAccount() {
        name = getRandomName();
        code = getRandomCode();
        iban = "TR" + getRandomIban();
        BankAccount bankAccount = new BankAccount();
        bankAccount.setId(bankId);
        bankAccount.setName(name);
        bankAccount.setIban(iban);
        bankAccount.setIntegrationCode(code);
        bankAccount.setCurrency("TRY");
        bankAccount.setSchoolId("5fe07e4fb064ca29931236a5");

        given()
                .cookies(cookies)
                .contentType(ContentType.JSON)
                .body(bankAccount)

                .when()
                .put("school-service/api/bank-accounts")

                .then()
                .log().body()
                .statusCode(200)
        ;
    }

    @Test(dependsOnMethods = "editBankAccount")
    public void deleteBankAccount() {
        given()
                .cookies(cookies)
                .pathParam("id", bankId)

                .when()
                .delete("school-service/api/bank-accounts/{id}")

                .then()
                .log().body()
                .statusCode(200);
    }



}
