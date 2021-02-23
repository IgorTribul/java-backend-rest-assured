package ru.geekbrains.rest.assured.steps;

import ru.geekbrains.rest.assured.BaseTest;
import ru.geekbrains.rest.assured.Endpoints;
import ru.geekbrains.rest.assured.dto.PostImageResponse;

import static io.restassured.RestAssured.given;

public class CommonRequest extends BaseTest {

    public PostImageResponse uploadCommonImage(){
        return given()
                .headers(headers)
                .body(positiveFile)
                .when()
                .post(Endpoints.getPostImageRequest())
                .prettyPeek()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(PostImageResponse.class);
    }
}
