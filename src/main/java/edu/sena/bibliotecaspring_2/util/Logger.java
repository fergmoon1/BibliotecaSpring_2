package edu.sena.bibliotecaspring_2.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void logInfo(String message) {
        String timestamp = LocalDateTime.now().format(formatter);
        System.out.println("[INFO] [" + timestamp + "] " + message);
    }

    public static void logError(String message, Throwable throwable) {
        String timestamp = LocalDateTime.now().format(formatter);
        System.out.println("[ERROR] [" + timestamp + "] " + message);
        if (throwable != null) {
            throwable.printStackTrace();
        }
    }
}