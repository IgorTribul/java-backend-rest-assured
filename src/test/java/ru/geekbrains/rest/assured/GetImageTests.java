package ru.geekbrains.rest.assured;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.geekbrains.rest.assured.dto.GetImageResponse;
import ru.geekbrains.rest.assured.steps.CommonRequest;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GetImageTests extends BaseTest {
    private String id;

    @BeforeEach
    void setUp() {
        id = new CommonRequest().uploadCommonImage().getData().getId();
    }

    @Epic(value = "Проверка API загрузки изображений")
    @Feature(value = "Получение загруженного изображения")
    @Story(value = "Пользователь авторизован")
    @Test
    void getImagePositiveTest(){
        GetImageResponse response = given()
                .spec(reqSpecAuth)
//                .log()
//                .all()
                .when()
                .get(Endpoints.getGetImageRequest(), id)
                .prettyPeek()
                .then()
                .extract()
                .body()
                .as(GetImageResponse.class);
        assertNotNull(response.getData().getId());
        assertNotNull(response.getData().getDatetime());
        assertNotNull(response.getData().getDeletehash());
    }

    @Epic(value = "Проверка API загрузки изображений")
    @Feature(value = "Получение загруженного изображения")
    @Story(value = "Пользователь не авторизован")
    @Test
    void getImageNegativeTest(){
        given()
                .spec(reqSpecNoauth)
                .log()
                .all()
                .when()
                .get(Endpoints.getGetImageRequest(), id)
                .prettyPeek()
                .then()
                .statusCode(403)
                .spec(respSpecNegativeTest);

    }

    @AfterEach
    void tearDown() {
        given()
                .headers(headers)
                .when()
                .delete(Endpoints.getGetImageRequest(), id)
                .prettyPeek()
                .then()
                .statusCode(200);
    }
}
