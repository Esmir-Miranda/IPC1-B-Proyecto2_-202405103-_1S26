package util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FechaUtil {
    public static String getFechaHoraActual() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }

    public static String getFechaArchivo() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm_ss"));
    }
}