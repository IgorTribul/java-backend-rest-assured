package ru.geekbrains.rest.assured;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import static org.hamcrest.Matchers.*;

public class BaseTest {

    protected static Properties prop = new Properties();
    protected static String userName;
    protected static String token;
    protected static Map<String,String> headers = new HashMap<>();

    ClassLoader classLoader = getClass().getClassLoader();
    protected File bigFile = new File(Objects.requireNonNull(classLoader
            .getResource((Images.BIG_FILE.fileName))).getFile());
    protected File positiveFile = new File(Objects.requireNonNull(classLoader
            .getResource((Images.POSITIVE.fileName))).getFile());
    protected File smallFile = new File(Objects.requireNonNull(classLoader
            .getResource((Images.SMALL_FILE.fileName))).getFile());
    protected File badFile = new File(Objects.requireNonNull(classLoader
            .getResource((Images.BAD_FILE.fileName))).getFile());

    static RequestSpecification reqSpecAuth = null;
    static RequestSpecification reqSpecNoauth = null;
    static ResponseSpecification respSpecPositiveTest = null;
    static ResponseSpecification respSpecNegativeTest = null;


    @BeforeAll
    protected static void beforeAll() {

        loadProperties();
        userName = prop.getProperty("userName");
        token = prop.getProperty("token");
        headers.put("Authorization","Bearer db95e5a9b463f2af989408b3e8f3c0635675cfcd");
        RestAssured.baseURI = prop.getProperty("base.url");
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        reqSpecAuth = new RequestSpecBuilder()
                .addHeader("Authorization", token)
                .build();

        reqSpecNoauth = new RequestSpecBuilder()
                .addHeader("Authorization", "")
                .build();


        respSpecPositiveTest = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectStatusLine("HTTP/1.1 200 OK")
                .expectContentType(ContentType.JSON)
                .expectResponseTime(Matchers.lessThan(5000L))
                .expectBody("success", is (true))
                .build();

        respSpecNegativeTest = new ResponseSpecBuilder()
                .expectStatusCode(greaterThanOrEqualTo(400))
                .expectResponseTime(Matchers.lessThan(5000L))
                .expectBody("success", is (false))
                .build();
    }

    protected static void loadProperties() {
        try {
            prop.load(new FileInputStream("src/test/resources/app.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
