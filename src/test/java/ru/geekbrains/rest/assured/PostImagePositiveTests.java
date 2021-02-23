package ru.geekbrains.rest.assured;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Base64;
import static io.restassured.RestAssured.given;

public class PostImagePositiveTests extends BaseTest{
    private String deleteHash;
    String fileString = Base64.getEncoder().encodeToString(getFileContent());


    @Epic(value = "Проверка API загрузки изображений")
    @Feature(value = "Загрузка нового изображения, позитивный тест")
    @Story(value = "Пользователь авторизован")
    @Test
    @Description(value = "Загрузка jpeg-файла")
    void uploadImagePositiveTest(){
        deleteHash = given()
                .spec(reqSpecAuth)
                .log()
                .all()
                .body(positiveFile)
                .when()
                .post(Endpoints.getPostImageRequest())
                .prettyPeek()
                .then()
                .spec(respSpecPositiveTest)
                .extract()
                .response()
                .jsonPath()
                .getString("data.deletehash");
    }

    @Epic(value = "Проверка API загрузки изображений")
    @Feature(value = "Загрузка нового изображения, позитивный тест")
    @Story(value = "Пользователь авторизован")
    @Test
    @Description(value = "Загрузка файла 1x1 pixel")
    void uploadSmallImagePositiveTest(){
        deleteHash = given()
                .spec(reqSpecAuth)
                .log()
                .all()
                .body(smallFile)
                .when()
                .post(Endpoints.getPostImageRequest())
                .prettyPeek()
                .then()
                .spec(respSpecPositiveTest)
                .extract()
                .response()
                .jsonPath()
                .getString("data.deletehash");
    }

    @Epic(value = "Проверка API загрузки изображений")
    @Feature(value = "Загрузка нового изображения, позитивный тест")
    @Story(value = "Пользователь авторизован")
    @Test
    @Description(value = "Загрузка бинарного файла")
    void uploadBinaryFilePositiveTest(){
        deleteHash = given()
                .spec(reqSpecAuth)
                .log()
                .body()
                .body(getFileContent())
                .when()
                .post(Endpoints.getPostImageRequest())
                .prettyPeek()
                .then()
                .spec(respSpecPositiveTest)
                .extract()
                .response()
                .jsonPath()
                .getString("data.deletehash");
    }

    @Epic(value = "Проверка API загрузки изображений")
    @Feature(value = "Загрузка нового изображения, позитивный тест")
    @Story(value = "Пользователь авторизован")
    @Test
    @Description(value = "Загрузка Base64 файла")
    void uploadBase64FilePositiveTest(){
        deleteHash = given()
                .spec(reqSpecAuth)
                .multiPart("image", fileString)
                .log()
                .all()
                .when()
                .post(Endpoints.getPostImageRequest())
                .prettyPeek()
                .then()
                .spec(respSpecPositiveTest)
                .extract()
                .response()
                .jsonPath()
                .getString("data.deletehash");
    }

    @AfterEach
    void tearDown() {
        given()
                .headers(headers)
                .log()
                .all()
                .when()
                .delete(Endpoints.getDeleteImageRequest(), deleteHash)
                .prettyPeek()
                .then()
                .statusCode(200);
    }
    protected byte[] getFileContent (){
        byte[] fileContent = new byte[0];
        try {
            fileContent = FileUtils.readFileToByteArray(positiveFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileContent;
    }
}
