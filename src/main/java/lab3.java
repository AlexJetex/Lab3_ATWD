import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class LabThree {
    private static final String BASE_URL = "https://petstore.swagger.io";

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.defaultParser = Parser.JSON;
        RestAssured.requestSpecification = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
        RestAssured.responseSpecification = new ResponseSpecBuilder().build();
    }

    @Test()
    public void testGetPetById() {
        given().get("/v2/pet/1")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test(dependsOnMethods = "testGetPetById")
    public void testAddNewPet() {
        Map<String, ?> body = Map.of(
                "id", 1,
                "name", "Buddy",
                "status", "available"
        );

        given().body(body)
                .post("/v2/pet")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test(dependsOnMethods = "testAddNewPet")
    public void testUpdatePet() {
        Map<String, ?> body = Map.of(
                "id", 1,
                "name", "Buddy",
                "status", "sold"
        );

        given().body(body)
                .put("/v2/pet")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test(dependsOnMethods = "testUpdatePet")
    public void testDeletePet() {
        given().delete("/v2/pet/1")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }
}
