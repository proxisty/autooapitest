package api;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pojo.UserData;

import java.util.List;

import static io.restassured.RestAssured.given;

@Tag("API")
public class ApiTest {

    private final static String URL = "https://reqres.in/";

    @Test
    public void checkHaveMichatlAndEndMail() {
        List<UserData> userData = given()
                .when() //
                .contentType(ContentType.JSON)
                .get(URL + "api/users?page=2")
                .then().log().all()
                .statusCode(200)
                .extract().body().jsonPath().getList("data", UserData.class);

        // найти Michael
        Assertions.assertTrue(userData.stream().anyMatch(p -> "Michael".equals(p.getFirst_name())));
        // убедиться, что уmail заканчивается на @reqres.in
        Assertions.assertTrue(userData.stream().allMatch(x -> x.getEmail().endsWith("@reqres.in")));
    }
}
