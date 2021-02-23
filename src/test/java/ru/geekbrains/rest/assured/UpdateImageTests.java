package ru.geekbrains.rest.assured;

import com.github.javafaker.Faker;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.geekbrains.rest.assured.steps.CommonRequest;

import static io.restassured.RestAssured.given;

public class UpdateImageTests extends BaseTest {
    private String id;
    RequestSpecification reqSpecUpdate = null;
    Faker faker = new Faker();

    @BeforeEach
    void setUp() {
        reqSpecUpdate = new RequestSpecBuilder()
                .addMultiPart("title", faker.chuckNorris().fact())
                .addMultiPart("description", faker.harryPotter().quote())
                .build();
        id = new CommonRequest().uploadCommonImage().getData().getId();
    }

    @Epic(value = "Проверка API загрузки изображений")
    @Feature(value = "Редактирование загруженного изображения")
    @Story(value = "Пользователь авторизован")
    @Test
    void updateImagePositiveTest(){
        given()
                .headers(headers)
                .spec(reqSpecUpdate)
                .log()
                .all()
                .when()
                .post(Endpoints.getGetImageRequest(), id)
                .prettyPeek()
                .then()
                .spec(respSpecPositiveTest)
                .statusCode(200);
    }

    @Epic(value = "Проверка API загрузки изображений")
    @Feature(value = "Редактирование загруженного изображения")
    @Story(value = "Пользователь не авторизован")
    @Test
    void updateImageNoauthNegativeTest(){
        given()
                .spec(reqSpecUpdate)
                .log()
                .all()
                .when()
                .post(Endpoints.getGetImageRequest(), id)
                .prettyPeek()
                .then()
                .statusCode(401);
    }

    @AfterEach
    void tearDown() {
        given()
                .headers(headers)
                .log()
                .all()
                .when()
                .delete(Endpoints.getGetImageRequest(), id)
                .prettyPeek()
                .then()
                .statusCode(200);
    }
}

