package edu.sena.bibliotecaspring_2;

import edu.sena.bibliotecaspring_2.util.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        Thread.setDefaultUncaughtExceptionHandler((thread, ex) -> {
            Logger.logError("Excepción no capturada en hilo " + thread.getName(), ex);
        });

        try {
            SpringApplication.run(Main.class, args);
            Logger.logInfo("Aplicación Spring Boot iniciada correctamente");
        } catch (Exception e) {
            Logger.logError("Error al iniciar la aplicación", e);
            System.exit(1);
        }

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                Logger.logInfo("Aplicación cerrada correctamente");
            } catch (Exception e) {
                Logger.logError("Error al cerrar la aplicación", e);
            }
        }));
    }
}