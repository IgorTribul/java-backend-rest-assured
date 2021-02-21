package ru.geekbrains.rest.assured;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;

public class UploadImageNegativeTests extends BaseTest{

    @Epic(value = "Проверка API загрузки изображений")
    @Feature(value = "Загрузка нового изображения, негативный тест")
    @Story(value = "Пользователь авторизован")
    @Test
    @Description(value = "Пользователь пытается загрузить файл с резмером, " +
            "превышающим максимально допустимый")
    void uploadBigFileNegativeNegativeTest() {
        given()
                .headers(headers)
                .body(bigFile)
                .log()
                .all()
                .when()
                .post("/image")
                .prettyPeek()
                .then()
                .statusCode(400);
    }

    @Epic(value = "Проверка API загрузки изображений")
    @Feature(value = "Загрузка нового изображения, негативный тест")
    @Story(value = "Пользователь авторизован")
    @Test
    @Description(value = "Неверно указан эндпойнт")
    void uploadBadEndpointNegativeTest() {
        given()
                .headers(headers)
                .body(jpgFile)
                .log()
                .all()
                .when()
                .post("/image777")
                .prettyPeek()
                .then()
                .statusCode(404);
    }

    @Epic(value = "Проверка API загрузки изображений")
    @Feature(value = "Загрузка нового изображения, негативный тест")
    @Story(value = "Пользователь авторизован")
    @Test
    @Description(value = "Пользователь пытается загрузить недопустимый тип файла ")
    void uploadNotImageFileNegativeTest() {
        given()
                .headers(headers)
                .body("src/test/resources/error.log")
                .log()
                .all()
                .when()
                .post("/image")
                .prettyPeek()
                .then()
                .statusCode(400);
    }

    @Epic(value = "Проверка API загрузки изображений")
    @Feature(value = "Загрузка нового изображения, негативный тест")
    @Story(value = "Пользователь не авторизован")
    @Test
    void uploadImageNoauthNegativeTest() {
        given()
                .body(jpgFile)
                .log()
                .all()
                .when()
                .post("/image")
                .prettyPeek()
                .then()
                .statusCode(401);
    }
}
