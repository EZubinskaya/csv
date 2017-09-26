package comparus.de.util;

import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;

/**
 * Created by ekaterina on 9/26/17.
 */
public class Util {

    public static BigDecimal createNumValue(String value) {
        BigDecimal result = BigDecimal.ZERO;
        String sanitized = value.replace(",", ".");
        if (NumberUtils.isCreatable(sanitized))     {
            result = NumberUtils.createBigDecimal(sanitized);
        }
        return result;
    }

    public static String decimalToString(BigDecimal decimal) {
        if (decimal.signum() == 0) {
            return "0,00";
        }
        return String.valueOf(decimal).replace(".", ",");
    }
}
