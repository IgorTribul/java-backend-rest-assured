package ru.geekbrains.rest.assured;

public enum Images {
    BIG_FILE("12Mb.jpg"),
    POSITIVE("normal.jpg"),
    SMALL_FILE("small.jpg"),
    BAD_FILE("text.txt");

    public final String fileName;

    Images(String fileName) {
        this.fileName=fileName;
    }
}
