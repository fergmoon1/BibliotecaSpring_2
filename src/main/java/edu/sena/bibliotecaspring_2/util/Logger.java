package edu.sena.bibliotecaspring_2.util;

public class Logger {
    public static void logInfo(String message) {
        System.out.println("[INFO] " + message);
    }

    public static void logError(String message, Throwable throwable) {
        System.err.println("[ERROR] " + message);
        if (throwable != null) {
            throwable.printStackTrace();
        }
    }
}