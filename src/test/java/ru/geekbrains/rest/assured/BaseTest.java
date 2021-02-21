package ru.geekbrains.rest.assured;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

public class BaseTest {
    protected static final String BIG_FILE_PATH = "canada-moraine-lake-ozero-gory.jpg";
    protected static final String JPG_FILE_PATH = "flower.jpg";
    protected static final String SMALL_FILE_PATH = "flower2.jpg";
    protected static final String PNG_FILE_PATH = "png_format.png";

    protected static Properties prop = new Properties();
    protected static String userName;
    protected static String token;
    protected static Map<String,String> headers = new HashMap<>();

    ClassLoader classLoader = getClass().getClassLoader();
    protected File bigFile = new File(Objects.requireNonNull(classLoader.getResource(BIG_FILE_PATH)).getFile());
    protected File jpgFile = new File(Objects.requireNonNull(classLoader.getResource(JPG_FILE_PATH)).getFile());
    protected File smallFile = new File(Objects.requireNonNull(classLoader.getResource(SMALL_FILE_PATH)).getFile());
    protected File pngFile = new File(Objects.requireNonNull(classLoader.getResource(PNG_FILE_PATH)).getFile());

    @BeforeAll
    protected static void beforeAll() {
        loadProperties();
        userName = prop.getProperty("userName");
        token = prop.getProperty("token");
        headers.put("Authorization", token);
        RestAssured.baseURI = prop.getProperty("base.url");
    }

    protected static void loadProperties() {
        try {
            prop.load(new FileInputStream("src/test/resources/app.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
