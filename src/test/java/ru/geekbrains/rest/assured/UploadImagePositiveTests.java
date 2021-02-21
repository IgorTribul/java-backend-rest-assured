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

public class UploadImagePositiveTests extends BaseTest{
    private String deleteHash;

    @Epic(value = "Проверка API загрузки изображений")
    @Feature(value = "Загрузка нового изображения, позитивный тест")
    @Story(value = "Пользователь авторизован")
    @Test
    @Description(value = "Загрузка jpeg-файла")
    void uploadFileTypeJpgNormalSizePositive(){
        deleteHash = given()
                .headers(headers)
                .log()
                .all()
                .body(jpgFile)
                .when()
                .post("/image")
                .prettyPeek()
                .then()
                .statusCode(200)
                .extract()
                .response()
                .jsonPath()
                .getString("data.deletehash");
    }

    @Epic(value = "Проверка API загрузки изображений")
    @Feature(value = "Загрузка нового изображения, позитивный тест")
    @Story(value = "Пользователь авторизован")
    @Test
    @Description(value = "Загрузка png-файла")
    void uploadFileTypePngNormalSizePositive(){
        deleteHash = given()
                .headers(headers)
                .log()
                .all()
                .body(pngFile)
                .when()
                .post("/image")
                .prettyPeek()
                .then()
                .statusCode(200)
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
    void uploadTestFileTypeJpgSmalSizePositive(){
        deleteHash = given()
                .headers(headers)
                .log()
                .all()
                .body(smallFile)
                .when()
                .post("/image")
                .prettyPeek()
                .then()
                .statusCode(200)
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
    void uploadBinaryFilePositive(){
        deleteHash = given()
                .headers(headers)
                .log()
                .body()
                .body(getFileContent())
                .when()
                .post("/image")
                .prettyPeek()
                .then()
                .statusCode(200)
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
    void uploadBase64FilePositive(){
        String fileString = Base64.getEncoder().encodeToString(getFileContent());
        deleteHash = given()
                .headers(headers)
                .multiPart("image", fileString)
                .log()
                .all()
                .when()
                .post("/image")
                .prettyPeek()
                .then()
                .statusCode(200)
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
                .delete("/image/{deleteHash}", deleteHash)
                .prettyPeek()
                .then()
                .statusCode(200);
    }

    private byte[] getFileContent (){
        byte[] fileContent = new byte[0];
        try {
            fileContent = FileUtils.readFileToByteArray(jpgFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileContent;
    }
}
