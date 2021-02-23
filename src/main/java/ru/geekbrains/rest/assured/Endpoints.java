package ru.geekbrains.rest.assured;

import lombok.Data;
import ru.geekbrains.rest.assured.dto.PostImageResponse;

public class Endpoints {

    private static final String POST_IMAGE_REQUEST = "/image";
    private static final String GET_IMAGE_REQUEST = "/image/{id}";
    private static final String GET_ACCOUNT_REQUEST = "/account/{userName}";
    private static final String DELETE_IMAGE_REQUEST = "/image/{imageHash}";

    private static final String BAD_ENDPOINT_REQUEST = "/image/123";

     private static final String DELETE_REQUEST = "/account/{userName}/image/{deleteHash}";

    public static String getPostImageRequest() {
        return POST_IMAGE_REQUEST;
    }

    public static String getGetImageRequest() {
        return GET_IMAGE_REQUEST;
    }

    public static String getGetAccountRequest() {
        return GET_ACCOUNT_REQUEST;
    }

    public static String getDeleteImageRequest() {
        return DELETE_IMAGE_REQUEST;
    }

    public static String getDeleteRequest() {
        return DELETE_REQUEST;
    }

    public static String getBadEndpointRequest() {
        return BAD_ENDPOINT_REQUEST;
    }


}

