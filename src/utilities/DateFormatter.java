package utilities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateFormatter {
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm");

    public static String formatLocalDate(LocalDate date) {
        return date.format(dateFormatter);
    }

    public static String formatLocalDateTime(LocalDateTime dateTime) {
        return dateTime.format(dateTimeFormatter);
    }
}
