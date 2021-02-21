package ru.geekbrains.rest.assured;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;

public class DeleteImageTests extends BaseTest {
    private static String deleteHash;

    @Epic(value = "Проверка API загрузки изображений")
    @Feature(value = "Удаление изображения")
    @Story(value = "Пользователь авторизован")
    @Test
    @Description(value = "Удаление в первый раз")
    void deleteImagePositiveTest() {
        setUp();
        given()
                .headers(headers)
                .log()
                .all()
                .when()
                .delete("/image/{deleteHash}", deleteHash)
                .prettyPeek()
                .then()
                .statusCode(200);
    }

    @Epic(value = "Проверка API загрузки изображений")
    @Feature(value = "Удаление изображения")
    @Story(value = "Пользователь авторизован")
    @Test
    @Description(value = "Повторное удаление")
    void deleteImageDoublePositiveTest() {
        given()
                .headers(headers)
                .log()
                .all()
                .when()
                .delete("/image/{deleteHash}", deleteHash)
                .prettyPeek()
                .then()
                .statusCode(204);
    }

    @Epic(value = "Проверка API загрузки изображений")
    @Feature(value = "Удаление изображения")
    @Story(value = "Пользователь не авторизован")
    @Test
    void deleteImageNoauthNegativeTest() {
        given()
                .log()
                .all()
                .when()
                .delete("/image/{deleteHash}", deleteHash)
                .prettyPeek()
                .then()
                .statusCode(401);
    }

    private void setUp() {
        deleteHash = given()
                .headers(headers)
                .body(jpgFile)
                .when()
                .post("/image")
                .prettyPeek()
                .then()
                .extract()
                .response()
                .jsonPath()
                .getString("data.deletehash");
    }
}
