package utilities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateFormatter {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static String format(LocalDate date) {
        return date.format(formatter);
    }
}
