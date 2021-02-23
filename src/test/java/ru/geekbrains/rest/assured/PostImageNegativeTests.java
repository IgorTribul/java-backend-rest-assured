package ru.geekbrains.rest.assured;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;

public class PostImageNegativeTests extends BaseTest{

    @Epic(value = "Проверка API загрузки изображений")
    @Feature(value = "Загрузка нового изображения, негативный тест")
    @Story(value = "Пользователь авторизован")
    @Test
    @Description(value = "Пользователь пытается загрузить файл с резмером, " +
            "превышающим максимально допустимый")
    void postBigFileNegativeTest() {
        given()
                .spec(reqSpecAuth)
                .body(bigFile)
                .log()
                .all()
                .when()
                .post(Endpoints.getPostImageRequest())
                .prettyPeek()
                .then()
                .spec(respSpecNegativeTest)
                .statusCode(400);
    }

    @Epic(value = "Проверка API загрузки изображений")
    @Feature(value = "Загрузка нового изображения, негативный тест")
    @Story(value = "Пользователь авторизован")
    @Test
    @Description(value = "Неверно указан эндпойнт")
    void postWithBadEndpointNegativeTest() {
        given()
                .spec(reqSpecAuth)
                .body(positiveFile)
                .log()
                .all()
                .when()
                .post(Endpoints.getBadEndpointRequest())
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
                .spec(reqSpecAuth)
                .body(badFile)
                .log()
                .all()
                .when()
                .post(Endpoints.getPostImageRequest())
                .prettyPeek()
                .then()
                .spec(respSpecNegativeTest)
                .statusCode(500);
    }

    @Epic(value = "Проверка API загрузки изображений")
    @Feature(value = "Загрузка нового изображения, негативный тест")
    @Story(value = "Пользователь не авторизован")
    @Test
    void uploadImageNoauthNegativeTest() {
        given()
                .spec(reqSpecNoauth)
                .body(positiveFile)
                .log()
                .all()
                .when()
                .post(Endpoints.getPostImageRequest())
                .prettyPeek()
                .then()
                .spec(respSpecNegativeTest)
                .statusCode(403);
    }
}
