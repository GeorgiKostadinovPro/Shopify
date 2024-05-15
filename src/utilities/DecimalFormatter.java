package utilities;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class DecimalFormatter {
    private static final DecimalFormat df = new DecimalFormat("#,##0.00");

    public static String format(BigDecimal value) {
        return df.format(value);
    }
}
