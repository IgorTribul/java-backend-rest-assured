package ru.geekbrains.rest.assured;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;

public class UpdateImageTests extends BaseTest {

    private String id;

    @BeforeEach
    void setUp() {
        id = given()
                .headers(headers)
                .body(jpgFile)
                .when()
                .post("/image")
                .prettyPeek()
                .then()
                .statusCode(200)
                .extract()
                .response()
                .jsonPath()
                .getString("data.id");
    }

    @Epic(value = "Проверка API загрузки изображений")
    @Feature(value = "Редактирование загруженного изображения")
    @Story(value = "Пользователь авторизован")
    @Test
    void updateImagePositiveTest(){
        given()
                .headers(headers)
                .params("title", "beautiful flower")
                .params("description", "attempt to change the description")
                .log()
                .all()
                .when()
                .post("/image/{id}", id)
                .prettyPeek()
                .then()
                .statusCode(200);
    }

    @Epic(value = "Проверка API загрузки изображений")
    @Feature(value = "Редактирование загруженного изображения")
    @Story(value = "Пользователь не авторизован")
    @Test
    void updateImageNoauthNegativeTest(){
        given()
                .params("title", "beautiful flower")
                .params("description", "attempt to change the description")
                .log()
                .all()
                .when()
                .post("/image/{id}", id)
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
                .delete("/image/{id}", id)
                .prettyPeek()
                .then()
                .statusCode(200);
    }
}

