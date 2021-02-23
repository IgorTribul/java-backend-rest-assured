package ru.geekbrains.rest.assured;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.geekbrains.rest.assured.steps.CommonRequest;

import static io.restassured.RestAssured.given;

public class DeleteImageTests extends BaseTest {
    private static String deleteHash;

    @BeforeAll
    static void beforeAllDeleteTests() {
        deleteHash = new CommonRequest().uploadCommonImage().getData().getDeletehash();
    }

    @Epic(value = "Проверка API загрузки изображений")
    @Feature(value = "Удаление изображения")
    @Story(value = "Пользователь авторизован")
    @Test
    @Description(value = "Удаление в первый раз")
    void deleteImagePositiveTest() {
        given()
                .spec(reqSpecAuth)
                .when()
                .delete(Endpoints.getDeleteImageRequest(), deleteHash)
                .prettyPeek()
                .then()
                .spec(respSpecPositiveTest);
    }

    @Epic(value = "Проверка API загрузки изображений")
    @Feature(value = "Удаление изображения")
    @Story(value = "Пользователь авторизован")
    @Test
    @Description(value = "Повторное удаление")
    void deleteImageDoublePositiveTest() {
        given()
                .spec(reqSpecAuth)
                .when()
                .delete(Endpoints.getDeleteImageRequest(), deleteHash)
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
                .spec(reqSpecNoauth)
                .when()
                .delete(Endpoints.getDeleteImageRequest(), deleteHash)
                .prettyPeek()
                .then()
                .spec(respSpecNegativeTest)
                .statusCode(403);
    }
}
